package de.timweb.ld48.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.game.Player;
import de.timweb.ld48.util.Converter;
import de.timweb.ld48.util.Vector2f;

public class PlayerBug extends Bug {
	private static final int BULLET_COUNT = 10;
	private Player player = null;
	private int flower = 0;

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private int lastShot;

	public PlayerBug(Vector2f pos, int strength, Player player) {
		super(pos, strength, Game.PLAYER);
		setPlayer(player);

	}

	public void setPlayer(Player player) {
		if (player == null)
			return;

		this.player = player;
		player.startControllBug();

		speed *= 2;
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if (lastShot > 0) {
			lastShot -= delta;
			for (int i = 0; i < bullets.size(); i++) {
				Bullet b = bullets.get(i);
				if(!b.isAlive())
					bullets.remove(i);
				else
					b.update(delta);
			}
		}

		if (player == null)
			return;

		setDirection(player.getDirection());

		if (player.isShooting())
			shoot(delta/10+1);

		float sprint = player.isSprinting() ? 2 : 1;
		if (player.keyPressed())
			move(getDirectionVector().x * sprint * speed * delta, getDirectionVector().y * sprint * speed * delta);

		player.setPosition(getPosition().copy());
	}

	@Override
	public void render(Graphics g) {
		if (player != null) {
			g.setColor(player.getColor());
			g.fillOval(Converter.toScreenX(getPosition().x - getSize()), Converter.toScreenY(getPosition().y - getSize()), getSize() * 2,
					getSize() * 2);
		}

		if (lastShot > 0) {
			for (Bullet b : bullets)
				b.render(g);
		}

		super.render(g);

	}

	@Override
	protected void onKilled() {
		if (player != null) {
			player.releaseBug();
			Game.getGui().notificate("Your bug died!");
		}
	}

	public boolean isPlayerControlled() {
		return player != null;
	}

	public void onPickup(Pickup pickup) {
		this.flower += 500;
	}

	public int getFlower() {
		return flower;
	}

	public void shoot(int bullets) {
		if (flower < bullets)
			return;

		flower -= bullets;

		lastShot = 500;
		Vector2f dir = getDirectionVector().copy();
		for (int i = 0; i < bullets; i++) {
			this.bullets.add( new Bullet(getPosition().copy(), new Vector2f(dir.x + ((float) Math.random() * 0.7f) - .35f, dir.y
					+ ((float) Math.random() * 0.7f) - .35f)));
		}
		
	}
}
