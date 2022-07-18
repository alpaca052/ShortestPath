import java.awt.Color;
import java.awt.Graphics;

public class Dot {
	private final int r = 10;
	private int x = 0, y = 0;
	private Color color = Color.RED;

	public Dot(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = Color.RED;
	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval(this.x - r / 2, this.y - r / 2, this.r, this.r);
	}

	/* getter */
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getR() {
		return r;
	}

	/* setter */
	public void setColor(Color color) {
		this.color = color;
	}
}
