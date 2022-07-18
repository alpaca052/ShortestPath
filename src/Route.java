public class Route {
	private Dot current_point;
	private Route beforeR = null;
	private Route beforeRTmp = null;
	private Edge beforeE = null;
	private int distance = Integer.MAX_VALUE;
	private boolean undecided = true;
	
	public Route(Dot c) {
		this.current_point = c;
	}

	public Dot getCurrent_point() {
		return current_point;
	}

	public void setCurrent_point(Dot current_point) {
		this.current_point = current_point;
	}

	public Route getBeforeR() {
		return beforeR;
	}

	public void setBeforeR(Route beforeR) {
		this.beforeR = beforeR;
	}

	public Route getBeforeRTmp() {
		return beforeRTmp;
	}

	public void setBeforeRTmp(Route beforeRTmp) {
		this.beforeRTmp = beforeRTmp;
	}

	public Edge getBeforeE() {
		return beforeE;
	}

	public void setBeforeE(Edge beforeE) {
		this.beforeE = beforeE;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean isUndecided() {
		return undecided;
	}

	public void setUndecided(boolean undecided) {
		this.undecided = undecided;
	}
	
	
}
