package de.timweb.ld48.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.entity.PlayerBug;
import de.timweb.ld48.game.Game;
import de.timweb.ld48.game.TinyWorldCanvas;
import de.timweb.ld48.util.ImageLoader;

public class Gui {
	public static final int WIDTH = 192;
	public static final int POS_X = TinyWorldCanvas.WIDTH - WIDTH;
	private static final int NOTIFICATION_LENGTH = 3000;
	private static final int MAX_LASTHELP = 25 * 1000;

	private int lastUpdate = 0;
	private int lastHelp = 0;

	private BufferedImage icon_blue;
	private BufferedImage icon_blue_big;
	private BufferedImage icon_red;
	private BufferedImage icon_orange;
	private BufferedImage icon_yellow;

	private MiniMap minimap;
	private String message = "";
	private Color color = new Color(0, 0, 0, 200);
	private Font font_big;
	private Font font;

	public Gui() {
		minimap = new MiniMap();

		font_big = new Font("Arial", Font.BOLD, 26);
		font = new Font("Arial", 0, 12);
		icon_blue = ImageLoader.getSubImage(ImageLoader.bug_blue_16, 0, 0, 16);
		icon_blue_big = ImageLoader.getSubImage(ImageLoader.bug_blue_32, 0, 0, 32);
		icon_red = ImageLoader.getSubImage(ImageLoader.bug_red_16, 0, 0, 16);
		icon_orange = ImageLoader.getSubImage(ImageLoader.bug_orange_16, 0, 0, 16);
		icon_yellow = ImageLoader.getSubImage(ImageLoader.bug_yellow_16, 0, 0, 16);
	}

	public void render(Graphics g) {
		g.setFont(font);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(POS_X, 0, WIDTH, TinyWorldCanvas.HEIGHT);

		renderBugInfos(g);
		renderBug(g);

		minimap.render(g);

		if (lastHelp < MAX_LASTHELP)
			renderHelp(g);

		if (Game.isGameEnd()) {
			if (Game.isWon()) {
				renderWin(g);
			} else
				renderLoose(g);

		}

		if (lastUpdate > 0) {
			renderHint(g);
		}
	}

	private void renderWin(Graphics g) {
		g.setColor(color);
		g.fillRoundRect(50, 50, TinyWorldCanvas.WIDTH_GAME - 100, TinyWorldCanvas.HEIGHT - 100, 40, 40);

		g.setColor(Color.white);
		g.setFont(font_big);
		g.drawString("Congratulations, you won!", TinyWorldCanvas.WIDTH_GAME / 2 - 150, 90);
		g.setFont(font);
		
	}

	private void renderLoose(Graphics g) {
		g.setColor(color);
		g.fillRoundRect(50, 50, TinyWorldCanvas.WIDTH_GAME - 100, TinyWorldCanvas.HEIGHT - 100, 40, 40);

		g.setColor(Color.white);
		g.setFont(font_big);
		g.drawString("Game over, you lose!", TinyWorldCanvas.WIDTH_GAME / 2 - 150, 90);
		g.setFont(font);
	}

	private void renderHelp(Graphics g) {
		g.setColor(color);
		g.fillRoundRect(50, 50, TinyWorldCanvas.WIDTH_GAME - 100, TinyWorldCanvas.HEIGHT - 100, 40, 40);

		g.setColor(Color.white);
		g.setFont(font_big);
		g.drawString("Bugs Planet", TinyWorldCanvas.WIDTH_GAME / 2 - 50, 90);

		int x = 70;
		int y = 120;
		g.setFont(font);
		g.drawString("Mission:", x, y);
		y += 20;
		g.drawString("Its a very tiny Planet and only one Sort of Bugs can survive!", x + 30, y);
		y += 20;
		g.drawString("Control one of the blue Bug and help your race.", x + 30, y);
		y += 20;
		g.drawString("Destroy all Enemys and their nests!", x + 30, y);

		y += 60;
		g.drawString("Controls:", x, y);
		y += 20;
		g.drawString("WASD (or Arrowkeys)        to move the Bug", x + 30, y);
		y += 20;
		g.drawString("Space                                   shoot / repair your nest", x + 30, y);
		y += 20;
		g.drawString("Shift (hold)                            extra speed", x + 30, y);
		y += 20;
		g.drawString("ESC                                       close this Window", x + 30, y);

		y += 60;
		g.drawString("Gameplay:", x, y);
		y += 20;
		g.drawString("Collect the flowers to get Ammo", x + 30, y);
		y += 20;
		g.drawString("Destroy the other nests to stop the enemys supplies", x + 30, y);
		y += 20;
		g.drawString("Shoot at your own nest improve your spawnrate", x + 30, y);

		y += 80;
		g.drawString("This windows is closing in: " + (MAX_LASTHELP - lastHelp) / 1000 + " sec", x + 30, y);
	}

	private void renderHint(Graphics g) {
		int width = message.length() * 4;

		g.setColor(color);
		g.fillRoundRect(20, 20, width * 2, 40, 20, 20);

		g.setColor(Color.white);
		g.drawString(message, 30, 43);

	}

	public void update(int delta) {
		if (lastUpdate > 0) {
			lastUpdate -= delta;
		}
		if (lastHelp < MAX_LASTHELP)
			lastHelp += delta;
	}

	private void renderBugInfos(Graphics g) {
		g.drawImage(icon_blue, POS_X + 10, 270, null);
		g.drawImage(icon_red, POS_X + 10, 320, null);
		g.drawImage(icon_orange, POS_X + 10, 370, null);
		g.drawImage(icon_yellow, POS_X + 10, 420, null);

		g.setColor(Color.white);
		g.drawString("" + Game.getCount(Game.PLAYER), POS_X + 40, 280);
		g.drawString("" + Game.getCount(Game.ENEMY_RED), POS_X + 40, 330);
		g.drawString("" + Game.getCount(Game.ENEMY_ORANGE), POS_X + 40, 380);
		g.drawString("" + Game.getCount(Game.ENEMY_YELLOW), POS_X + 40, 430);

		g.drawImage(ImageLoader.spawner_blue_16, POS_X + 90, 270, null);
		g.drawString("Level : " + Game.getSpawner()[Game.PLAYER].getLevel(), POS_X + 110, 280);
		g.drawString("Health: " + Game.getSpawner()[Game.PLAYER].getHealth() + "%", POS_X + 110, 295);

		int dy = 50;
		g.drawImage(ImageLoader.spawner_red_16, POS_X + 90, 270 + dy, null);
		g.drawString("Level : " + Game.getSpawner()[Game.ENEMY_RED].getLevel(), POS_X + 110, 280 + dy);
		g.drawString("Health: " + Game.getSpawner()[Game.ENEMY_RED].getHealth() + "%", POS_X + 110, 295 + dy);

		dy += 50;
		g.drawImage(ImageLoader.spawner_orange_16, POS_X + 90, 270 + dy, null);
		g.drawString("Level : " + Game.getSpawner()[Game.ENEMY_ORANGE].getLevel(), POS_X + 110, 280 + dy);
		g.drawString("Health: " + Game.getSpawner()[Game.ENEMY_ORANGE].getHealth() + "%", POS_X + 110, 295 + dy);

		dy += 50;
		g.drawImage(ImageLoader.spawner_yellow_16, POS_X + 90, 270 + dy, null);
		g.drawString("Level : " + Game.getSpawner()[Game.ENEMY_YELLOW].getLevel(), POS_X + 110, 280 + dy);
		g.drawString("Health: " + Game.getSpawner()[Game.ENEMY_YELLOW].getHealth() + "%", POS_X + 110, 295 + dy);
	}

	private void renderBug(Graphics g) {
		PlayerBug bug = Game.getPlayerControlledBug();
		String level = "-";
		String flowers = "-";
		String health = "-";

		if (bug != null) {
			level = "" + bug.getLevel();
			flowers = "" + bug.getFlower();
			health = "" + (int) bug.getHealth();
		}

		g.setColor(Color.black);
		g.drawRect(POS_X + 5, 500, WIDTH - 10, 100);

		g.setColor(Color.white);
		g.drawImage(icon_blue, POS_X + 7, 502, null);
		g.drawString("Level: " + level, POS_X + 30, 530);
		g.drawString("Flowers: " + flowers, POS_X + 30, 550);
		g.drawString("Health: " + health, POS_X + 30, 570);
	}

	public void notificate(String string) {
		message = string;
		lastUpdate = NOTIFICATION_LENGTH;
	}

	public boolean showingHelp() {
		return lastHelp < MAX_LASTHELP;
	}

	public void stopHelp() {
		lastHelp = MAX_LASTHELP;
	}
}
