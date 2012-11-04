package de.timweb.ld48.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.util.Converter;
import de.timweb.ld48.util.Vector2f;

public class Bullet extends Entity {
	private static final double HURT_RADIUS = 24;
	private static final int MAX_LIFETIME = 500;

	private float speed = 0.3f;
	private int lifetime = MAX_LIFETIME;

	private Vector2f dir;
	private Color color;

	private float damage = 0.3f;

	public Bullet(Vector2f pos, Vector2f dir) {
		super(pos);

		this.dir = dir;
		color = new Color(255, 255, 0);
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		lifetime -= delta;
		if (lifetime < 0)
			kill();

		getPosition().x += dir.x * speed * delta;
		getPosition().y += dir.y * speed * delta;

		ArrayList<Bug> enemys = Game.getEnemyBugs();
		for (Bug b : enemys) {
			if (getPosition().distance(b.getPosition()) < HURT_RADIUS) {
				b.hurt(damage * delta);
			}
		}

		BugSpawner[] spawner = Game.getSpawner();
		for (BugSpawner b : spawner) {
			if (getPosition().distance(b.getPosition()) < HURT_RADIUS) {
				if (b.getTeam() != Game.PLAYER)
					b.hurt(damage * delta);
				else
					b.help(damage * delta);

			}
		}

	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);

		g.fillRect(Converter.toScreenX(getPosition().x), Converter.toScreenY(getPosition().y), 3, 3);
	}
}
