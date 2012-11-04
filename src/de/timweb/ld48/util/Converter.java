package de.timweb.ld48.util;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.game.TinyWorldCanvas;
import de.timweb.ld48.gui.MiniMap;
import de.timweb.ld48.world.World;

public class Converter {

	public static int toScreenX(float x) {
		float screenX = (x - Game.getOffset().x) % World.SIZE_X;

		if (screenX < -(World.SIZE_X - TinyWorldCanvas.WIDTH))
			screenX += World.SIZE_X;
		return (int) screenX;
	}

	public static int toScreenY(float y) {
		return (int) (y - Game.getOffset().y);
	}

	public static int getScreenY(Vector2f pos) {
		return 0;
	}

	public static Vector2f PostoMinimap(Vector2f position) {
		double angle = Math.toRadians(position.x / World.SIZE_X * 360 +270);

		double dx = Math.cos(angle) + 1;
		double dy = Math.sin(angle) + 1;

		return new Vector2f((float) (dx * MiniMap.RADIUS + MiniMap.POS_X), (float) (dy * MiniMap.RADIUS + MiniMap.POS_Y));
	}
}
