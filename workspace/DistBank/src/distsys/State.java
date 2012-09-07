package distsys;

public class State {

	private int x;

	public State() {
		x = 0;
		System.out.println("State initialized to 0");
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		x = newX;
		System.out.println("State set to " + newX);
	}

}
