package de.timweb.ld48.world;

import java.awt.Graphics;

import de.timweb.ld48.game.TinyWorldCanvas;
import de.timweb.ld48.util.Vector2f;

public class World {
	public static final int TILES_X = 32;
	public static final int TILES_Y = 12;

	public static final int SIZE_X = TILES_X * Tile.SIZE;
	public static final int SIZE_Y = TILES_Y * Tile.SIZE;

	private static Tile[][] tiles = new Tile[TILES_X][TILES_Y];

	public World() {
//		for (int x = 0; x < TILES_X; x++) {
//			for (int y = 0; y < TILES_Y; y++) {
//				if (x == 0)
//					tiles[x][y] = new Tile(x, y, Tile.WATER);
//				else
//					tiles[x][y] = new Tile(x, y, Tile.NORMAL);
//			}
//		}
		tiles = WorldLoader.loadTiles();

	}

	public void render(Graphics g) {

		for (int x = 0; x < TILES_X; x++) {
			for (int y = 0; y < TILES_Y; y++) {
				tiles[x][y].render(g);
			}
		}
	}

	public static boolean isValidPosition(Vector2f position) {
		boolean isValid = false;

		if (position.x < 0)
			position.x += World.SIZE_X;
		if (position.x > World.SIZE_X)
			position.x -= World.SIZE_X;

		if (position.y < 0 || position.y > World.SIZE_Y)
			return false;

		try {
			isValid = tiles[position.x() / Tile.SIZE][position.y() / Tile.SIZE]
					.isValid();
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return isValid;
	}

	public static int distanceToWater(Vector2f pos) {
		int X = pos.x() / Tile.SIZE;
		int Y = pos.y() / Tile.SIZE;


		int min = 10;

		for (int x = -10; x < 10; x++) {
			int x2 = x + X;
			if (x2 < 0)
				x2 += TILES_X;
			if (x2 > TILES_X)
				x2 -= TILES_X;

			if (tiles[x2][Y].isWater()) {
				if (min > x)
					min = Math.abs(x);
			}
		}
		for (int y = -10; y < 10; y++) {
			int y2 = y + Y;
			if (y2 < 0)
				y2 += TILES_Y;
			if (y2 > TILES_Y)
				y2 -= TILES_Y;

			try{
				if (tiles[X][y2].isWater()) {
					if (min > y)
						min = Math.abs(y);
				}
			}catch (ArrayIndexOutOfBoundsException e) {
			}
		}

		return min;
	}
}
