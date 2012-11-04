package de.timweb.ld48.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import de.timweb.ld48.TinyWorldMain;
import de.timweb.ld48.entity.BugSpawner;
import de.timweb.ld48.entity.Flower;
import de.timweb.ld48.game.Game;
import de.timweb.ld48.util.Vector2f;

public class WorldLoader {

	public static Tile[][] loadTiles() {
		String lines[] = {
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
				"...~~~~.........................",
				"   ..~~..            2           ",
				" 0  ....           ....          ",
				"  *        *      .~~.          ",
				"....              .~~.          ",
				".~~.              ....          ",
				".~~.               *            ",
				"....      1 ....          3     ",
				"           ..~~..               ",
				"............~~~~................",
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
		};
		
//		BufferedReader bufReader = null;
//		try {
//			
//			bufReader = new BufferedReader(new FileReader(new File(TinyWorldMain.class.getResource("/world.txt").toURI())));
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}

		Tile[][] tiles = new Tile[World.TILES_X][World.TILES_Y];
		for (int y = 0; y < World.TILES_Y; y++) {
			
				String line = lines[y];
				System.out.println(line);
				for (int x = 0; x < World.TILES_X; x++) {
					switch (line.charAt(x)) {
					case '#':
						tiles[x][y] = new Tile(x, y, Tile.SAND);
						break;
					case ' ':
						tiles[x][y] = new Tile(x, y, Tile.NORMAL);
						break;
					case '~':
						tiles[x][y] = new Tile(x, y, Tile.WATER);
						break;
					case '.':
						tiles[x][y] = new Tile(x, y, Tile.SAND);
						break;
					case '0':
						tiles[x][y] = new Tile(x, y, Tile.NORMAL);
						Game.getSpawner()[0] = new BugSpawner(new Vector2f(x * Tile.SIZE, y * Tile.SIZE), Game.PLAYER);
						break;
					case '1':
						tiles[x][y] = new Tile(x, y, Tile.NORMAL);
						Game.getSpawner()[1] = new BugSpawner(new Vector2f(x * Tile.SIZE, y * Tile.SIZE), Game.ENEMY_RED);
						break;
					case '2':
						tiles[x][y] = new Tile(x, y, Tile.NORMAL);
						Game.getSpawner()[2] = new BugSpawner(new Vector2f(x * Tile.SIZE, y * Tile.SIZE), Game.ENEMY_ORANGE);
						break;
					case '3':
						tiles[x][y] = new Tile(x, y, Tile.NORMAL);
						Game.getSpawner()[3] = new BugSpawner(new Vector2f(x * Tile.SIZE, y * Tile.SIZE), Game.ENEMY_YELLOW);
						break;

					case '*':
						tiles[x][y] = new Tile(x, y, Tile.NORMAL);
						Game.addEntity(new Flower(new Vector2f(x * Tile.SIZE, y * Tile.SIZE)));
						break;
					}
				}

			

		}
		return tiles;
	}
}
