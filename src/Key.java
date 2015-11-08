import java.awt.Color;
import java.awt.Graphics;

public class Key {
	private int size;
	private int x, y;
	
	public Key(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(120, 120, 0, 100));
		g.fillRect(x, y, size, size);
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
