package de.timweb.ld48.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.game.TinyWorldCanvas;
import de.timweb.ld48.util.ImageLoader;

public class Tile {
	public static final int SIZE = 64;
	public static final int NORMAL = 0;
	public static final int WATER = 1;
	public static final int SAND = 2;

	private int x, y;
	private int type;
	private boolean blocked = false;
	private BufferedImage img;

	public Tile(int x, int y, int type) {
		this.type = type;
		this.x = x;
		this.y = y;
		
		switch (type) {
		case NORMAL:
			img = ImageLoader.tex_grass;
			break;
		case WATER:
			img = ImageLoader.tex_water;
			break;
		case SAND:
			img = ImageLoader.tex_sand;
			break;

		default:
			img = ImageLoader.tex_grass;
			break;
		}
		if(type == WATER)
			blocked = true;
		
	}

	public void render(Graphics g) {
		float x = (this.x * SIZE - Game.getOffset().x) % World.SIZE_X;
		float y = this.y * SIZE - Game.getOffset().y;
		
		if(x < -(World.SIZE_X - TinyWorldCanvas.WIDTH))
			x += World.SIZE_X;
		
		g.drawImage(img, (int)x,(int) y, null);
//		if(x < TinyWorldCanvas.WIDTH+SIZE && y< TinyWorldCanvas.HEIGHT){
//			g.drawImage(img, (int)x,(int) y, null);
//		}else{
//			 x = ((this.x+World.SIZE_X) * SIZE - Game.getOffset().x) % World.SIZE_X;
//			 if(x < TinyWorldCanvas.WIDTH+SIZE && y< TinyWorldCanvas.HEIGHT){
//					g.drawImage(img, (int)x,(int) y, null);
//				System.out.println("draw else");
//				}
//		}
	}

	public boolean isValid() {
		return !blocked;
	}

	public boolean isWater() {
		return type == WATER;
	}
}
