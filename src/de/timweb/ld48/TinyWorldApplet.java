package de.timweb.ld48;

import java.applet.Applet;

import de.timweb.ld48.game.TinyWorldCanvas;


public class TinyWorldApplet extends Applet{
	
	@Override
	public void init() {
		super.init();
		TinyWorldCanvas game = new TinyWorldCanvas(getWidth(),getHeight(),0);
		add(game);
		game.start();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		System.exit(0);
	}
}
