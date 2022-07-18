import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class ElementManager {
	private static ArrayList<Dot> dotList = new ArrayList<Dot>();
	private static ArrayList<Edge> edgeList = new ArrayList<Edge>();
	public static int R = 20;

	private ElementManager() {
	};

	public static void draw(Graphics g) {
		for (Dot d : ElementManager.dotList) {
			d.draw(g);
		}
		for (Edge e : ElementManager.edgeList) {
			e.draw(g);
		}
	}

	// Dotを追加可能か確認
	public static boolean checkNewDot(Dot dot) {
		if (ElementManager.getNearDot(dot.getX(), dot.getY()) == null) {
			return true;
		} else {
			return false;
		}
	}

	// Edgeを追加可能か確認
	public static boolean checkNewEdge(Edge edge) {
		for (Edge e : edgeList) {
			if (e.getDot1() == edge.getDot1() && e.getDot2() == edge.getDot2()) {
				return false;
			}
			if (e.getDot1() == edge.getDot2() && e.getDot2() == edge.getDot1()) {
				return false;
			}
		}

		return true;
	}

	// Dotを追加
	public static void add(Dot d) {
		if (ElementManager.checkNewDot(d)) {
			ElementManager.dotList.add(d);
		}
	}

	// Edgeを追加
	public static void add(Edge e) {
		if (ElementManager.checkNewEdge(e)) {
			ElementManager.edgeList.add(e);
		}
	}

	// Dot,Edgeを全て削除
	public static void clearAll() {
		ElementManager.dotList.clear();
		ElementManager.edgeList.clear();
	}

	// Edgeを全て削除
	public static void clearEdge() {
		ElementManager.edgeList.clear();
	}

	// Dot,Edgeの色を初期化
	public static void resetColor() {
		for (Dot d : ElementManager.dotList) {
			d.setColor(Color.RED);
		}
		for (Edge e : ElementManager.edgeList) {
			e.setColor(Color.BLUE);
			;
		}
	}

	// 2地点間の距離を取得
	public static int getDistance(Dot dot1, Dot dot2) {
		return (int) Math.sqrt((dot1.getX() - dot2.getX()) * (dot1.getX() - dot2.getX())
				+ (dot1.getY() - dot2.getY()) * (dot1.getY() - dot2.getY()));
	}

	// x,yから半径r以内のDotがあれば一番近いものを返す
	public static Dot getNearDot(int x, int y) {
		Dot nearDot = null;
		int tmpR;
		int min_r = R;

		for (Dot d : dotList) {
			tmpR = ElementManager.getDistance(new Dot(x, y), d);
			if (min_r > tmpR) {
				nearDot = d;
				min_r = tmpR;
			}
		}

		return nearDot;
	}

	// 2地点間の最短経路を求める
	public static void getShortestPath(Dot dotA, Dot dotB) {
		int new_dist, min;
		ArrayList<Route> routes = new ArrayList<Route>();
		Route min_r, dotb_r = null, tmp_r = new Route(dotA);
		tmp_r.setDistance(0);
		tmp_r.setUndecided(false);
		routes.add(tmp_r);
		for (Dot d : ElementManager.dotList) {
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
			for (Edge e : ElementManager.edgeList) {
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

		for (Route bef = dotb_r; bef.getBeforeR() != null; bef = bef.getBeforeR()) {
			if (bef.getBeforeR() == null)
				break;
			bef.getBeforeE().setColor(Color.GREEN);
			bef.getCurrent_point().setColor(Color.GREEN);
		}
	}

	// ファイルに保存
	public static void save() {
		JFileChooser filechooser = new JFileChooser();

		int selected = filechooser.showSaveDialog(filechooser);
		if (selected == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			System.out.println(file.getAbsolutePath());
			// 書き込み
			PrintWriter pw = null;
			try {
				FileWriter wfile = new FileWriter(file);
				pw = new PrintWriter(new BufferedWriter(wfile));

				pw.println(ElementManager.dotList.size());
				for (Dot d : ElementManager.dotList) {
					pw.println(d.getX());
					pw.println(d.getY());
				}
				pw.println(ElementManager.edgeList.size());
				for (Edge e : ElementManager.edgeList) {
					for (int i = 0; i < ElementManager.dotList.size(); i++) {
						if (e.getDot1() == ElementManager.dotList.get(i)) {
							pw.println(i);
							break;
						}
					}
					for (int i = 0; i < ElementManager.dotList.size(); i++) {
						if (e.getDot2() == ElementManager.dotList.get(i)) {
							pw.println(i);
							pw.println(e.getWeight());
							break;
						}
					}
				}

				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (selected == JFileChooser.CANCEL_OPTION) {
			System.out.println("キャンセルされました");
		} else if (selected == JFileChooser.ERROR_OPTION) {
			System.out.println("エラー又は取消しがありました");
		}
	}

	// ファイルの読み込み
	public static void readFile() {
		ElementManager.clearAll();
		JFileChooser filechooser = new JFileChooser();
		int x, y, n, d1, d2, weight;

		int selected = filechooser.showSaveDialog(filechooser);
		if (selected == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			System.out.println(file.getAbsolutePath());
			// 読み込み
			try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);

				n = Integer.parseInt(br.readLine());
				for (int i = 0; i < n; i++) {
					x = Integer.parseInt(br.readLine());
					y = Integer.parseInt(br.readLine());
					ElementManager.add(new Dot(x, y));
				}
				n = Integer.parseInt(br.readLine());
				for (int i = 0; i < n; i++) {
					d1 = Integer.parseInt(br.readLine());
					d2 = Integer.parseInt(br.readLine());
					weight = Integer.parseInt(br.readLine());
					ElementManager
							.add(new Edge(ElementManager.dotList.get(d1), ElementManager.dotList.get(d2), weight));
				}

				br.close();
			} catch (IOException | NumberFormatException e) {
				e.printStackTrace();
			}
		} else if (selected == JFileChooser.CANCEL_OPTION) {
			System.out.println("キャンセルされました");
		} else if (selected == JFileChooser.ERROR_OPTION) {
			System.out.println("エラー又は取消しがありました");
		}
	}
}
