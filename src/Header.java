import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Header extends JPanel {
	private MainPanel mpanel;
	
	public Header(MainPanel mpanel) {
		this.mpanel = mpanel;
	}

	// 描画処理
	@Override
	public void paintComponent(Graphics g) {
		int fontSize = 20;
		//this.setSize(this.mpanel.getWidth() + 200, 90);
		this.setLayout(new FlowLayout());
		ButtonGroup bg = new ButtonGroup();
		g.setColor(Color.BLACK);
		g.fillRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());
		
		// ラジオボタンの追加
		JRadioButton radio1 = new JRadioButton("地点を追加");
		JRadioButton radio2 = new JRadioButton("経路を追加");
		JRadioButton radio3 = new JRadioButton("最短経路を表示");
		radio1.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		radio2.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		radio3.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		bg.add(radio1);
		bg.add(radio2);
		bg.add(radio3);
		this.add(radio1);
		this.add(radio2);
		this.add(radio3);
		radio1.setBackground(Color.ORANGE);
		radio2.setBackground(Color.ORANGE);
		radio3.setBackground(Color.ORANGE);
		radio1.addActionListener(this.mpanel);
		radio2.addActionListener(this.mpanel);
		radio3.addActionListener(this.mpanel);
		
		// ボタンの追加
		JButton button2 = new JButton("全て削除");
		JButton button3 = new JButton("経路のみ削除");
		JButton button4 = new JButton("エクスポート");
		JButton button5 = new JButton("インポート");
		button2.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		button3.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		button4.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		button5.setFont(new Font("ＭＳ 明朝", Font.BOLD, fontSize));
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(button5);
		button2.addActionListener(this.mpanel);
		button3.addActionListener(this.mpanel);
		button4.addActionListener(this.mpanel);
		button5.addActionListener(this.mpanel);
		
		this.repaint();
	}
}
