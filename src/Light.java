public class Light {
	private int size;
	private int x, y;
	
	public Light(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getSize() {
		return size;
	}
}
