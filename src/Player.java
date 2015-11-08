import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
	private int size;
	private int x, y;
	private int dir;
	
	private boolean[] keys = new boolean[120];
	private boolean up, down, left, right;
	
	private Main m;
	private Light l;
	private int t = 64;
	
	public Player(int x, int y, int size, Main m) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.dir = 0;
		this.m = m;
		l = new Light(x - t/2, y, t);
	}
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		if (up && y > 2) {
			y -= 3;
		}
		if (down && y < m.getHeight() - 20) {
			y += 3;
		}
		if (left && x > 2) {
			x -= 3;
		}
		if (right && x < m.getWidth() - 18) {
			x += 3;
		}
		
		if (up) {
			if (right) {
				dir = 1;
			}
			else {
				dir = 0;
			}
		}
		if (right && !up) {
			if (down) {
				dir = 3;
			}
			else {
				dir = 2;
			}
		}
		if (down && !right) {
			if (left) {
				dir = 5;
			}
			else {
				dir = 4;
			}
		}
		if (left && !down) {
			if (up) {
				dir = 7;
			}
			else {
				dir = 6;
			}
		}
		
		int tx = x + size / 2;
		int ty = y + size / 2;
		switch(dir) {
			case 0:
				l.update(tx - t/2, y - t);
				break;
			case 1:
				l.update(x + size, y - t);
				break;
			case 2:
				l.update(x + size, ty - t/2);
				break;
			case 3:
				l.update(x + size, y + size);
				break;
			case 4:
				l.update(tx - t/2, y + size);
				break;
			case 5:
				l.update(x - t, y + size);
				break;
			case 6:
				l.update(x - t, ty - t/2);
				break;
			case 7:
				l.update(x - t, y - t);
				break;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(175, 50, 50));
		g.fillRect(x, y, size, size);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public Light getLight() {
		return l;
	}

	public void setLight(Light l) {
		this.l = l;
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
