package de.timweb.ld48.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import de.timweb.ld48.gui.Gui;
import de.timweb.ld48.util.ImageLoader;


public class TinyWorldCanvas extends Canvas implements Runnable, KeyListener{
	public static int WIDTH;
	public static int WIDTH_GAME;
	public static int HEIGHT;
	
	public static final int FPS_TARGET = 120;
	public static final int DELTA_TARGET = 1000/FPS_TARGET;

	private boolean isRunning = false;
	private int delta;
	
	
	public TinyWorldCanvas(int width, int height, int border) {
		WIDTH = width;
		WIDTH_GAME = width-Gui.WIDTH;
		HEIGHT = height;
		
		Dimension dim = new Dimension(width-border, height-border);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.addKeyListener(this);
	}

	public void start() {
		Thread gamethread = new Thread(this);
		ImageLoader.init();
		Game.init(); 
		gamethread.start();
		isRunning = true;
	}

	@Override
	public void run() {
		long timeOld = System.currentTimeMillis();
		delta = 0;
		
		while(true){
			timeOld = System.currentTimeMillis();
			
			
			update(delta);
			
			BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                continue;
            }
			render(bs.getDrawGraphics());
			
			if (bs != null)
				bs.show();
			
			long timepassed = System.currentTimeMillis() - timeOld;
			if(timepassed < DELTA_TARGET){
				try {
					Thread.sleep(DELTA_TARGET-timepassed);
				} catch (InterruptedException e) {
				}
			}
			delta = (int) (System.currentTimeMillis() - timeOld);
		}
	}

	private synchronized void render(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		
		Game.render(g);
		
		g.setColor(Color.red);
		g.setFont(getFont());
		g.drawString("FPS: "+getFPS(), 20, 20);
		
		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
	
	@Override
	public void paint(Graphics arg0) {
	}

	public int getFPS(){
		return delta > 0 ? 1000/ delta : Integer.MAX_VALUE;
	}
	
	private void update(int delta){
		Game.update(delta);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Game.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Game.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	
}
