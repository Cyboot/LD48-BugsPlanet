package de.timweb.ld48.entity;

import java.awt.Graphics;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.game.TinyWorldCanvas;
import de.timweb.ld48.util.Converter;
import de.timweb.ld48.util.Sprite;
import de.timweb.ld48.util.Vector2f;
import de.timweb.ld48.world.World;

public abstract class Entity {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;

	private Vector2f position;
	private boolean isAlive = true;
	private boolean noClip = false;
	private int size;
	private Sprite sprite;
	private float spritespeed = 0.01f;
	private int direction;
	private float step;

	public Entity(Vector2f pos) {
		this.position = pos;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void update(int delta) {
		if (sprite == null)
			return;

		step = (step + spritespeed * delta) % sprite.getX();
	}

	public void render(Graphics g) {
		if (sprite == null)
			return;

		float x = (position.x - Game.getOffset().x) % World.SIZE_X;
		if(x < -(World.SIZE_X - TinyWorldCanvas.WIDTH))
			x += World.SIZE_X;
		
		
		g.drawImage(sprite.getSprite(direction, step), Converter.toScreenX(getPosition().x-size/2),
				Converter.toScreenY(getPosition().y-size/2), null);

	}

	protected void kill() {
		isAlive = false;
		onKilled();
	}

	protected void onKilled() {

	}

	public final Vector2f getPosition() {
		return position;
	}

	protected void move(float x, float y) {
		Vector2f nextPos = new Vector2f(position.x + x, position.y + y);
		setPosition(nextPos);
	}

	public void setPosition(Vector2f position) {
		if (World.isValidPosition(position)){
			this.position.set(position);
		}

	}

	protected void setSize(int size) {
		this.size = size;
	}
	protected int getSize() {
		return size;
	}

	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	protected void setSpriteSpeed(float factor) {
		this.spritespeed *= factor;
	}

	protected void setDirection(int dir) {
		this.direction = dir;
	}

	protected int getDirection() {
		return direction;
	}

	protected Vector2f getDirectionVector() {
		switch (direction) {
		case UP:
			return new Vector2f(0, -1);
		case DOWN:
			return new Vector2f(0, 1);
		case RIGHT:
			return new Vector2f(1, 0);
		case LEFT:
			return new Vector2f(-1, 0);

		default:
			return null;
		}

	}
}
