import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				// フレームの設定関連
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(500, 500);
				MainPanel panel = new MainPanel();
				frame.getContentPane().add(panel);
				frame.setVisible(true);
			}
		});
	}

}