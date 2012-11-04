package de.timweb.ld48.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import de.timweb.ld48.entity.Bug;
import de.timweb.ld48.entity.BugSpawner;
import de.timweb.ld48.entity.Entity;
import de.timweb.ld48.entity.Flower;
import de.timweb.ld48.entity.PlayerBug;
import de.timweb.ld48.gui.Gui;
import de.timweb.ld48.util.Vector2f;
import de.timweb.ld48.world.World;

public class Game {
	public static final int PLAYER = 0;
	public static final int ENEMY_RED = 1;
	public static final int ENEMY_ORANGE = 2;
	public static final int ENEMY_YELLOW = 3;
	public final static int DISTANCE_FROM_BORDER = TinyWorldCanvas.WIDTH / 4;
	public static final int ENTITIY_LIMIT = 500;

	private static Vector2f offset = new Vector2f();
	private static boolean gameEnd = false;

	private static World world;
	private static ArrayList<Bug> enemyBugs = new ArrayList<Bug>();
	private static ArrayList<Bug> playerBugs = new ArrayList<Bug>();
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private static HashMap<Integer, Bool> keymap = new HashMap<Integer, Bool>();

	private static int[] countBugs = new int[4];
	private static BugSpawner[] bugspawner = new BugSpawner[4];

	private static Gui gui;
	private static Player player;
	private static boolean isWon;

	public static void init() {
		world = new World();
		gui = new Gui();

		// entities.add(new Flower(new Vector2f(100, 100)));
		// entities.add(new Flower(new Vector2f(500, 500)));
		// entities.add(new Flower(new Vector2f(1500, 600)));

		player = new Player();
		addFriendBug(new PlayerBug(new Vector2f(200, 200), Bug.MIDDLE, null));
		registerKeys();
	}

	private static void registerKeys() {
		keymap.put(KeyEvent.VK_W, new Bool(false));
		keymap.put(KeyEvent.VK_A, new Bool(false));
		keymap.put(KeyEvent.VK_S, new Bool(false));
		keymap.put(KeyEvent.VK_D, new Bool(false));
		keymap.put(KeyEvent.VK_UP, new Bool(false));
		keymap.put(KeyEvent.VK_LEFT, new Bool(false));
		keymap.put(KeyEvent.VK_DOWN, new Bool(false));
		keymap.put(KeyEvent.VK_RIGHT, new Bool(false));
		keymap.put(KeyEvent.VK_SHIFT, new Bool(false));
		keymap.put(KeyEvent.VK_SPACE, new Bool(false));
		keymap.put(KeyEvent.VK_ESCAPE, new Bool(false));
	}

	public static void update(int delta) {
		gui.update(delta);
		if (isKeyDown(KeyEvent.VK_ESCAPE))
			gui.stopHelp();

		if (gui.showingHelp())
			return;

		if (!bugspawner[1].isAlive() && !bugspawner[2].isAlive() && !bugspawner[3].isAlive() && countBugs[1] <= 0 && countBugs[2] <= 0
				&& countBugs[3] <= 0)
			gameWin();
		if (!bugspawner[PLAYER].isAlive())
			gameOver();

		if (!player.isControlingBug()) {
			try {
				getNextPlayerBug().setPlayer(player);
			} catch (NullPointerException e) {
			}
		}

		player.update(delta);
		float x = player.getPosition().x;
		float y = player.getPosition().y;

		if (x - offset.x > TinyWorldCanvas.WIDTH_GAME - DISTANCE_FROM_BORDER)
			offset.x = x + DISTANCE_FROM_BORDER - TinyWorldCanvas.WIDTH_GAME;
		if (x - offset.x < DISTANCE_FROM_BORDER)
			offset.x = x - DISTANCE_FROM_BORDER + 50;

		if (y - offset.y < DISTANCE_FROM_BORDER)
			offset.y = y - DISTANCE_FROM_BORDER;
		if (y - offset.y > TinyWorldCanvas.HEIGHT - DISTANCE_FROM_BORDER)
			offset.y = y + DISTANCE_FROM_BORDER - TinyWorldCanvas.HEIGHT;

		if (offset.x < 0)
			offset.x += World.SIZE_X;
		if (offset.x > World.SIZE_X)
			offset.x -= World.SIZE_X;

		if (offset.y < 0)
			offset.y = 0;
		if (offset.y > World.SIZE_Y - TinyWorldCanvas.HEIGHT)
			offset.y = World.SIZE_Y - TinyWorldCanvas.HEIGHT;

		for (BugSpawner b : bugspawner) {
			if (b.isAlive())
				b.update(delta);
		}

		for (int i = 0; i < enemyBugs.size(); i++) {
			Bug e = enemyBugs.get(i);
			if (!e.isAlive()) {
				countBugs[e.getTeam()]--;
				enemyBugs.remove(i);
			} else
				e.update(delta);
		}
		for (int i = 0; i < playerBugs.size(); i++) {
			Bug e = playerBugs.get(i);
			if (!e.isAlive()) {
				countBugs[e.getTeam()]--;
				playerBugs.remove(i);
			} else
				e.update(delta);
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (!e.isAlive()) {
				entities.remove(i);
			} else
				e.update(delta);
		}
	}

	private static void gameOver() {
		isWon = false;
		gameEnd = true;
	}

	private static void gameWin() {
		gameEnd = true;
		isWon = true;
	}

	public static void render(Graphics g) {
		world.render(g);
		for (Entity e : entities)
			e.render(g);

		for (Entity e : bugspawner) {
			if (e.isAlive())
				e.render(g);
		}
		for (Entity e : enemyBugs)
			e.render(g);
		for (Entity e : playerBugs)
			e.render(g);

		gui.render(g);
	}

	public static Vector2f getOffset() {
		return offset;
	}

	public static boolean isKeyDown(int keyCode) {
		if (!keymap.containsKey(new Integer(keyCode)))
			return false;
		return keymap.get(new Integer(keyCode)).getBool();
	}

	public static boolean isKeyPressed(int keyCode) {
		if (!keymap.containsKey(new Integer(keyCode)))
			return false;
		boolean b = keymap.get(new Integer(keyCode)).getBool();
		keymap.get(new Integer(keyCode)).setBool(false);
		return b;
	}

	public static synchronized void keyPressed(int keyCode) {
		if (keymap.containsKey(new Integer(keyCode))) {
			Bool bool = keymap.get(new Integer(keyCode));
			bool.setBool(true);
		}
	}

	public static synchronized void keyReleased(int keyCode) {
		if (keymap.containsKey(new Integer(keyCode))) {
			Bool bool = keymap.get(new Integer(keyCode));
			bool.setBool(false);
		}
	}

	private static class Bool {
		private boolean bool;

		public Bool(boolean bool) {
			this.bool = bool;
		}

		public void setBool(boolean bool) {
			this.bool = bool;
		}

		public boolean getBool() {
			return bool;
		}
	}

	public static void addEnemyBug(Bug e) {
		countBugs[e.getTeam()]++;
		enemyBugs.add(e);
	}

	public static void addFriendBug(PlayerBug e) {
		countBugs[e.getTeam()]++;
		playerBugs.add(e);
	}

	public static Player getPlayer() {
		return player;
	}

	public static BugSpawner[] getSpawner() {
		return bugspawner;
	}

	public static ArrayList<Bug> getPlayersBug() {
		return playerBugs;
	}

	public static ArrayList<Bug> getEnemyBugs() {
		return enemyBugs;
	}

	public static int getCount(int team) {
		return countBugs[team];
	}

	public static int getEntityCount() {
		return countBugs[0] + countBugs[1] + countBugs[2] + countBugs[3];
	}

	public static PlayerBug getPlayerControlledBug() {
		for (Bug p : playerBugs)
			if ((p instanceof PlayerBug) && ((PlayerBug) p).isPlayerControlled())
				return (PlayerBug) p;

		return null;
	}

	public static PlayerBug getNextPlayerBug() {
		for (Bug b : playerBugs) {
			if (b.isAlive() && b instanceof PlayerBug)
				return (PlayerBug) b;
		}
		return null;
	}

	public static Gui getGui() {
		return gui;
	}

	public static void addEntity(Entity e) {
		entities.add(e);
	}

	public static boolean isGameEnd() {
		return gameEnd;
	}

	public static boolean isWon() {
		return isWon;
	}
}
