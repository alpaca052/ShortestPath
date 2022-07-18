import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MainPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	private final int NO_EVENT = 0;
	private final int ADD_DOT = 1;
	private final int ADD_EDGE = 2;
	private final int GET_SHORTEST_PATH = 3;

	private int nowX = 0, nowY = 0, flag = 0;
	private Dot edgeS = null, edgeG = null;

	public MainPanel() {
	}

	// 描画処理
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (this.flag == this.ADD_EDGE) {
			if (this.edgeS != null) {
				g.setColor(Color.RED);
				if (this.edgeG == null) {
					g.drawLine(this.edgeS.getX(), this.edgeS.getY(), this.nowX, this.nowY);
				} else {
					g.drawLine(this.edgeS.getX(), this.edgeS.getY(), this.edgeG.getX(), this.edgeG.getY());
				}
			}
		} else if (this.flag == this.GET_SHORTEST_PATH) {
			if (this.edgeS != null && this.edgeG != null) {
				ElementManager.getShortestPath(this.edgeS, this.edgeG);
				this.edgeS = this.edgeG = null;
				this.flag = this.NO_EVENT;
			}
		}
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		ElementManager.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (flag == this.ADD_DOT) {
			// 地点を追加
			Dot dot = new Dot(e.getX(), e.getY());
			ElementManager.add(dot);
		} else if (flag == this.GET_SHORTEST_PATH) {
			// 最短経路の開始地点または終了地点を設定
			Dot dot = ElementManager.getNearDot(e.getX(), e.getY());
			if (this.edgeS == null) {
				if (dot != null) {
					dot.setColor(Color.GREEN);
					this.edgeS = dot;
				}
			} else {
				if (dot != null && dot != this.edgeS) {
					dot.setColor(Color.GREEN);
					this.edgeG = dot;
				}
			}
		}
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.flag == this.ADD_EDGE) {
			// 新規経路のスタート地点を設定
			edgeS = ElementManager.getNearDot(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.flag == this.ADD_EDGE) {
			// 経路のスタート・ゴール地点が存在すれば経路を追加
			if (this.edgeS != null && this.edgeG != null && this.edgeS != this.edgeG) {
				Edge edge = new Edge(this.edgeS, this.edgeG, ElementManager.getDistance(edgeS, edgeG));
				ElementManager.add(edge);
			}
			this.edgeS = this.edgeG = null;
			this.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 初期化処理
		this.edgeS = this.edgeG = null;
		ElementManager.resetColor();

		if (e.getSource() instanceof JRadioButton) {
			if (((JRadioButton) e.getSource()).getText() == "地点を追加") {
				// 地点を追加
				this.flag = this.ADD_DOT;
			} else if (((JRadioButton) e.getSource()).getText() == "経路を追加") {
				// 経路を追加
				this.flag = this.ADD_EDGE;
			} else if (((JRadioButton) e.getSource()).getText() == "最短経路を表示") {
				// 最短経路を表示
				this.flag = this.GET_SHORTEST_PATH;
			}
		} else if (e.getSource() instanceof JButton) {
			if (((JButton) e.getSource()).getText() == "全て削除") {
				ElementManager.clearAll();
			} else if (((JButton) e.getSource()).getText() == "経路のみ削除") {
				ElementManager.clearEdge();
			} else if (((JButton) e.getSource()).getText() == "エクスポート") {
				ElementManager.save();
			} else if (((JButton) e.getSource()).getText() == "インポート") {
				ElementManager.readFile();
				;
			}
		}

		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (flag == this.ADD_EDGE) {
			this.edgeG = ElementManager.getNearDot(e.getX(), e.getY());
			this.nowX = e.getX();
			this.nowY = e.getY();
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
