package de.timweb.ld48.gui;

import java.awt.Color;
import java.awt.Graphics;

import de.timweb.ld48.entity.BugSpawner;
import de.timweb.ld48.game.Game;
import de.timweb.ld48.game.Player;
import de.timweb.ld48.util.Converter;
import de.timweb.ld48.util.ImageLoader;
import de.timweb.ld48.util.Vector2f;

public class MiniMap {
	public static final int RADIUS = 64;
	private static final int BORDER = 32;
	public static final int POS_X = Gui.POS_X+BORDER;
	public static final double POS_Y = BORDER;
	private static final int ICON_PLAYER = 4;
	
	private Color color;

	public MiniMap() {
		color = new Color(0,0,0,200);
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRoundRect(POS_X-BORDER, 0, RADIUS*2+BORDER*2, RADIUS*2+BORDER*2,30,30);
		g.drawImage(ImageLoader.world, Gui.POS_X+BORDER, BORDER, null);
		
		renderSpawner(g);
		renderPlayer(g);
	}

	private void renderSpawner(Graphics g) {
		for(BugSpawner b : Game.getSpawner()){
			if(!b.isAlive())
				continue;
			int team = b.getTeam();
				
			switch (team) {
			case Game.PLAYER:
				g.setColor(Color.blue);
				break;
			case Game.ENEMY_RED:
				g.setColor(Color.red);
				break;
			case Game.ENEMY_ORANGE:
				g.setColor(Color.ORANGE.darker());
				break;
			case Game.ENEMY_YELLOW:
				g.setColor(Color.yellow);
				break;
			}
			
			Vector2f pos = Converter.PostoMinimap(b.getPosition());
			g.fillRect(pos.x(), pos.y(), 8, 8);
		}
	}

	private void renderPlayer(Graphics g) {
		Player player = Game.getPlayer();
		if(player.controlsBug()){
			Vector2f pos = Converter.PostoMinimap(player.getPosition());
			
			g.setColor(Color.cyan);
			g.fillOval(pos.x()-ICON_PLAYER, pos.y()-ICON_PLAYER, 8, 8);
		}
	}
}
