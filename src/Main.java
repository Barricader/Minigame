import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final Color DARK = new Color(0, 0, 0, 245);
	private static final int TILE_SIZE = 4;
	public static int width = 1200;
	public static int height = 675;

	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private boolean won = false;

	ArrayList<ArrayList<Integer>> test;
	
	Random r;
	
	Player p;
	Key k;
	
	/**
	 ** Instantiate the variables
	 ** Also create the cats
	 ** Init the nodes?
	 **/
	public Main() {
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		frame = new JFrame();

		r = new Random();
		p = new Player((width / 2) - 8, (height / 2) - 8, 16, this);
		k = new Key(r.nextInt(width - 80) + 40, r.nextInt(height - 80) + 40, 32);
		
		test = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < height/TILE_SIZE + 1; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int j = 0; j < width/TILE_SIZE + 1; j++) {
				temp.add(0);
			}
			test.add(temp);
		}
		
		addKeyListener(p);
	}

	/**
	 ** Start the thread
	 **/
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	/**
	 ** Stop the thread
	 **/
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("Minigame | " + frames + " fps");
				frames = 0;
			}
		}
		stop();
	}

	/**
	 ** The game loop
	 **/
	public void update() {
		p.update();
		for (int i = 0; i < test.size(); i++) {
			for (int j = 0; j < test.get(i).size(); j++) {
				Rectangle r1 = new Rectangle(p.getX(), p.getY(), p.getSize(), p.getSize());
				Rectangle r2 = new Rectangle(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				Rectangle r3 = new Rectangle(p.getLight().getX() , p.getLight().getY(), p.getLight().getSize(), p.getLight().getSize());
				Rectangle r4 = new Rectangle(k.getX(), k.getY(), k.getSize(), k.getSize());
				Rectangle r5 = new Rectangle(p.getLight().getX() + p.getLight().getSize()/4,
						p.getLight().getY() + p.getLight().getSize()/4,
						p.getLight().getSize()/2, p.getLight().getSize()/2);
				if (r1.intersects(r2)) {
					test.get(i).set(j, 1);
				}
				else if (r3.intersects(r2)) {
					test.get(i).set(j, 2);
				}
				else {
					test.get(i).set(j, 0);
				}
				
				if (r5.intersects(r4)) {
					won = true;
				}
			}	
		}
	}

	/**
	 ** Renders the screen
	 **/
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (!won) {
			k.render(g);
			p.render(g);
			
			g.setColor(DARK);
			for (int i = 0; i < test.size(); i++) {
				for (int j = 0; j < test.get(i).size(); j++) {
					if (test.get(i).get(j) == 0) {
						g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
					}
				}
			}
		}
		else {
			g.setColor(Color.BLACK);
			g.drawString("YOU WIN", width / 2 - 10, height / 2 - 5);
		}

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Main game = new Main();
		game.frame.setResizable(false);
		game.frame.setTitle("Minigame");
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
