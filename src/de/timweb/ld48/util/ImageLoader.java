package de.timweb.ld48.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import de.timweb.ld48.TinyWorldMain;


public class ImageLoader {
	public static BufferedImage tex_grass = null;
	public static BufferedImage tex_sand;
	public static BufferedImage bug_blue_32;
	public static BufferedImage bug_blue_16;
	public static BufferedImage bug_red_32;
	public static BufferedImage bug_red_16;
	public static BufferedImage bug_orange_32;
	public static BufferedImage bug_orange_16;
	public static BufferedImage bug_yellow_32;
	public static BufferedImage bug_yellow_16;
	
	public static BufferedImage flower;
	
	public static BufferedImage tex_water;
	public static BufferedImage world;
	
	public static BufferedImage spawner_blue;
	public static BufferedImage spawner_red;
	public static BufferedImage spawner_orange;
	public static BufferedImage spawner_yellow;
	
	public static BufferedImage spawner_blue_16;
	public static BufferedImage spawner_red_16;
	public static BufferedImage spawner_orange_16;
	public static BufferedImage spawner_yellow_16;
	public static BufferedImage ani_puff;
	


	public static BufferedImage getSubImage(BufferedImage img, int x, int y, int width) {
		return img.getSubimage(x * width, y * width, width, width);
	}
	

	public static void init() {
		try {
			bug_blue_32 = ImageIO.read(TinyWorldMain.class.getResource("/bug_blue.png"));
			bug_red_32 = ImageIO.read(TinyWorldMain.class.getResource("/bug_red.png"));
			bug_orange_32 = ImageIO.read(TinyWorldMain.class.getResource("/bug_orange.png"));
			bug_yellow_32 = ImageIO.read(TinyWorldMain.class.getResource("/bug_yellow.png"));
			bug_blue_16 = ImageIO.read(TinyWorldMain.class.getResource("/bug_blue_16.png"));
			bug_red_16 = ImageIO.read(TinyWorldMain.class.getResource("/bug_red_16.png"));
			bug_orange_16 = ImageIO.read(TinyWorldMain.class.getResource("/bug_orange_16.png"));
			bug_yellow_16 = ImageIO.read(TinyWorldMain.class.getResource("/bug_yellow_16.png"));
			
			flower = ImageIO.read(TinyWorldMain.class.getResource("/flower.png"));
			
			spawner_blue = ImageIO.read(TinyWorldMain.class.getResource("/spawner_blue.png"));
			spawner_red = ImageIO.read(TinyWorldMain.class.getResource("/spawner_red.png"));
			spawner_orange = ImageIO.read(TinyWorldMain.class.getResource("/spawner_orange.png"));
			spawner_yellow = ImageIO.read(TinyWorldMain.class.getResource("/spawner_yellow.png"));
			
			spawner_blue_16 = ImageIO.read(TinyWorldMain.class.getResource("/spawner_blue_16.png"));
			spawner_red_16 = ImageIO.read(TinyWorldMain.class.getResource("/spawner_red_16.png"));
			spawner_orange_16 = ImageIO.read(TinyWorldMain.class.getResource("/spawner_orange_16.png"));
			spawner_yellow_16 = ImageIO.read(TinyWorldMain.class.getResource("/spawner_yellow_16.png"));
			
			tex_grass = ImageIO.read(TinyWorldMain.class.getResource("/tex_grass.png"));
			tex_water = ImageIO.read(TinyWorldMain.class.getResource("/tex_water.png"));
			tex_sand = ImageIO.read(TinyWorldMain.class.getResource("/tex_sand.png"));
			
			ani_puff = ImageIO.read(TinyWorldMain.class.getResource("/anim_puff.png"));

			world = ImageIO.read(TinyWorldMain.class.getResource("/world.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
