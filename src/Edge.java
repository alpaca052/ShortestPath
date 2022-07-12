import java.awt.Color;
import java.awt.Graphics;

public class Edge {
	private Dot dot1, dot2;
	private int weight = 0;
	private Color color = Color.BLUE;

	public Dot getDot1() {
		return dot1;
	}

	public void setDot1(Dot dot1) {
		this.dot1 = dot1;
	}

	public Dot getDot2() {
		return dot2;
	}

	public void setDot2(Dot dot2) {
		this.dot2 = dot2;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Edge() {
		this(new Dot(0, 0), new Dot(0, 0), 0);
	}

	public Edge(Dot dot1, Dot dot2, int weight) {
		this.dot1 = dot1;
		this.dot2 = dot2;
		this.weight = weight;
		this.color = Color.BLUE;
	}

	public void draw(Graphics g) {
		int x_ave = (dot1.getX() + dot2.getX()) / 2;
		int y_ave = (dot1.getY() + dot2.getY()) / 2;
		g.setColor(this.color);
		g.drawLine(dot1.getX(), dot1.getY(), dot2.getX(), dot2.getY());
		g.drawString(weight + "", x_ave, y_ave);
	}
}