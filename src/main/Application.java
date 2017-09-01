package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import io.Keys;
import io.Mouse;

public class Application implements Runnable{

	private boolean running;
	private Thread thread;
	private JFrame frame;
	private Canvas canvas;
	private int width, height;
	private BufferStrategy bs;
	private Graphics g;
	private Map map;
	private Keys keys;
	private Mouse mouse;
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void create() {
		frame = new JFrame("Maze and Pathfinding Test");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(600, 600));
		frame.setMaximumSize(new Dimension(1200, 800));
		canvas = new Canvas();
		width = 900;
		height = 600;
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		frame.add(canvas);
		frame.pack();
		keys = new Keys();
		mouse = new Mouse();
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
		frame.addMouseWheelListener(mouse);
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addMouseWheelListener(mouse);
		frame.addKeyListener(keys);
		map = new Map(this);
	}
	
	public void run() {
		create();
		while(running) {
			width = frame.getWidth();
			height = frame.getHeight();
			keys.tick();
			render();
		}
		stop();
	}
	
	public void render() {
		bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		map.render(g);
		bs.show();
		g.dispose();
	}
	
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public JFrame getFrame() {return frame;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public Keys keys() {return keys;}
	public Mouse mouse() {return mouse;}
	
	public static void main(String[] args) {
		new Application().start();
	}
	
}
