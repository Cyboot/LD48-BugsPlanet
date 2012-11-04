package de.timweb.ld48.util;

import java.awt.Image;
import java.awt.image.BufferedImage;



public class Sprite {
	private BufferedImage[][] imgs;
	
	public Sprite(BufferedImage img, int directions, int size) {
		int width = img.getWidth(null)/size;
		
		imgs = new BufferedImage[directions][width];
		
		for (int y = 0; y < directions; y++) {
			for (int x = 0; x < width; x++) {
				imgs[y][x] = ImageLoader.getSubImage(img, x, y, size);
			}
		}
	}
	
	public BufferedImage getSprite(int direction, float step){
		return imgs[direction][(int)step];
	}

	public float getX() {
		return imgs[0].length;
	}
}
