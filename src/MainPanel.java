import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MainPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	private final int r = 20;
	private Graphics g = this.getGraphics();
	private int nowX = 0, nowY = 0, flag = 0;
	private JRadioButton radio1, radio2;
	private JButton button;
	private ArrayList<Dot> dotList = new ArrayList<Dot>();
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();
	private Dot edgeS = null, edgeG = null;

	public MainPanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	// 描画処理
	@Override
	public void paintComponent(Graphics g) {
		this.g = g;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (this.flag == 2) {
			if (this.edgeS != null) {
				g.setColor(Color.RED);
				if (this.edgeG == null) {
					this.g.drawLine(this.edgeS.getX(), this.edgeS.getY(), this.nowX, this.nowY);
				} else {
					this.g.drawLine(this.edgeS.getX(), this.edgeS.getY(), this.edgeG.getX(), this.edgeG.getY());
				}
			}
		} else if (this.flag == 3) {
			if (this.edgeS != null && this.edgeG != null) {
				this.getShortestPath(edgeS, edgeG);
			}
		}

		this.setRadioButtons();
		this.drawDotEdge();
	}

	public void setRadioButtons() {
		ButtonGroup bg = new ButtonGroup();

		// メニューの作成
		JPanel header = new JPanel();
		header.setBackground(Color.ORANGE);

		// ラジオボタンの追加
		this.radio1 = new JRadioButton("地点を追加");
		this.radio2 = new JRadioButton("経路を追加");
		bg.add(this.radio1);
		bg.add(this.radio2);
		header.add(this.radio1);
		header.add(this.radio2);
		this.radio1.setBackground(Color.ORANGE);
		this.radio2.setBackground(Color.ORANGE);
		this.radio1.addActionListener(this);
		this.radio2.addActionListener(this);

		// ボタンの追加
		this.button = new JButton("最短経路を表示");
		header.add(this.button);
		this.button.addActionListener(this);

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH, header);
	}

	// 全ての地点と経路を表示
	public void drawDotEdge() {
		for (Dot d : this.dotList) {
			d.draw(this.g);
		}
		for (Edge e : this.edgeList) {
			e.draw(this.g);
		}
	}

	// 2地点間の距離を取得
	public int getDistance(Dot dot1, Dot dot2) {
		return (int) Math.sqrt((dot1.getX() - dot2.getX()) * (dot1.getX() - dot2.getX())
				+ (dot1.getY() - dot2.getY()) * (dot1.getY() - dot2.getY()));
	}

	// Dotを追加できるかどうか確認
	public boolean checkNewDot(Dot dot) {
		boolean check = true;

		for (Dot d : dotList) {
			if (dot.getR() > this.getDistance(dot, d)) {
				check = false;
			}
		}
		return check;
	}

	// x,yから半径r以内のDotがあれば一番近いものを返す
	public Dot getNearDot(int x, int y, int r) {
		Dot nearDot = null;
		int tmpR;

		for (Dot d : dotList) {
			tmpR = this.getDistance(new Dot(x, y), d);
			if (r > tmpR) {
				nearDot = d;
				r = tmpR;
			}
		}

		return nearDot;
	}

	// 2地点間の最短経路を求める
	public void getShortestPath(Dot dotA, Dot dotB) {
		int new_dist, min;
		ArrayList<Route> routes = new ArrayList<Route>();
		Route min_r, dotb_r = null, tmp_r = new Route(dotA);
		tmp_r.setDistance(0);
		tmp_r.setUndecided(false);
		routes.add(tmp_r);
		for (Dot d : this.dotList) {
			if (d == dotB) {
				dotb_r = new Route(d);
				routes.add(dotb_r);
			} else if (d != dotA) {
				d.setColor(Color.RED);
				routes.add(new Route(d));
			}
		}

		for (int i = 0; i < routes.size(); i++) {
			// tmp_rから経路が繋がっている場所のdistanceを更新
			for (Edge e : this.edgeList) {
				e.setColor(Color.BLUE);
				if (tmp_r.getCurrent_point() == e.getDot1()) {
					for (Route r : routes) {
						if (e.getDot2() == r.getCurrent_point()) {
							new_dist = tmp_r.getDistance() + e.getWeight();
							if (new_dist < r.getDistance()) {
								r.setDistance(new_dist);
								r.setBeforeRTmp(tmp_r);
								r.setBeforeE(e);
							}
						}
					}
				} else if (tmp_r.getCurrent_point() == e.getDot2()) {
					for (Route r : routes) {
						if (e.getDot1() == r.getCurrent_point()) {
							new_dist = tmp_r.getDistance() + e.getWeight();
							if (new_dist < r.getDistance()) {
								r.setDistance(new_dist);
								r.setBeforeRTmp(tmp_r);
								r.setBeforeE(e);
							}
						}
					}
				}
			}

			// test
			for (Route r : routes) {
				System.out.println("test : " + r.getDistance() + "," + r.isUndecided());
			}

			// distanceが最小の地点は経路決定
			min = Integer.MAX_VALUE;
			min_r = null;
			for (Route r : routes) {
				if (r.isUndecided() && min > r.getDistance()) {
					min = r.getDistance();
					min_r = r;
				}
			}
			if (min_r == null) {
				break;
			}
			min_r.setBeforeR(min_r.getBeforeRTmp());
			min_r.setUndecided(false);
			tmp_r = min_r;
		}

		System.out.println(routes.get(0).getBeforeR() + "," + routes.get(0));
		for (Route bef = dotb_r; bef.getBeforeR() != null; bef = bef.getBeforeR()) {
			System.out.println(bef.getBeforeR() + "," + bef.getDistance());
			if (bef.getBeforeR() == null)
				break;
			bef.getBeforeE().setColor(Color.GREEN);
			bef.getCurrent_point().setColor(Color.GREEN);
		}

		this.flag = 0;
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (flag == 1) {
			// 地点を追加
			Dot dot = new Dot(e.getX(), e.getY());
			if (this.checkNewDot(dot)) {
				dotList.add(dot);
				this.repaint();
			}
		} else if (flag == 3) {
			Dot dot = this.getNearDot(e.getX(), e.getY(), this.r);
			if (this.edgeS == null) {
				dot.setColor(Color.GREEN);
				this.edgeS = dot;
			} else {
				dot.setColor(Color.GREEN);
				this.edgeG = dot;
			}
			this.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.flag == 2) {
			// 経路のスタート地点を設定
			edgeS = this.getNearDot(e.getX(), e.getY(), this.r);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.flag == 2) {
			// 経路のスタート・ゴール地点が存在すれば経路を追加
			if (this.edgeS != null && this.edgeG != null && this.edgeS != this.edgeG) {
				Edge edge = new Edge(this.edgeS, this.edgeG, this.getDistance(edgeS, edgeG));
				this.edgeList.add(edge);
			}
			this.edgeS = this.edgeG = null;
			this.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.edgeS = this.edgeG = null;

		//Managerに以降予定
		for (Dot d : this.dotList) {
			d.setColor(Color.RED);
		}
		for (Edge edge : this.edgeList) {
			edge.setColor(Color.BLUE);
		}

		if (e.getSource() instanceof JRadioButton) {
			if (((JRadioButton) e.getSource()).getText() == radio1.getText()) {
				// 地点を追加
				this.flag = 1;
			} else if (((JRadioButton) e.getSource()).getText() == radio2.getText()) {
				// 経路を追加
				this.flag = 2;
			}
		} else if (e.getSource() instanceof JButton) {
			if (((JButton) e.getSource()).getText() == this.button.getText()) {
				this.flag = 3;
			}
		}

		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (flag == 2) {
			this.edgeG = this.getNearDot(e.getX(), e.getY(), this.r);
			this.nowX = e.getX();
			this.nowY = e.getY();
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}