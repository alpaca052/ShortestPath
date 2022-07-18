import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				// フレームの設定関連
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(1200, 1200);
				frame.getContentPane().setBackground(Color.lightGray);
				
				MainPanel mpanel = new MainPanel();
				
				JPanel header = new Header(mpanel);
				
				mpanel.setBounds(50, header.getHeight() + 100, frame.getWidth() - 130, frame.getHeight() - header.getHeight() - 200);
				
				frame.setLayout(null);
				frame.add(mpanel);
				frame.setLayout(new BorderLayout());
				frame.add(BorderLayout.NORTH, header);
				
				frame.setVisible(true);
			}
		});
	}

}
