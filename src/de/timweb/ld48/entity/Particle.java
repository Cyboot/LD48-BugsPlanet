package de.timweb.ld48.entity;

import java.awt.Color;
import java.awt.Graphics;

import de.timweb.ld48.util.Converter;
import de.timweb.ld48.util.Vector2f;

public class Particle extends Entity {
	private static int PARTICLE_SIZE = 2;
	private static float PARTICLE_SPEED = 0.07f;
	
	private Vector2f dir;

	public Particle(Vector2f pos) {
		super(pos);
		
		int n = Math.random() > 0.5 ? 1 : -1;
		int m = Math.random() > 0.5 ? 1 : -1;
		dir = new Vector2f((float)Math.random()*PARTICLE_SPEED*n,(float)Math.random()*PARTICLE_SPEED*m);
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		
		getPosition().x += dir.x *delta;
		getPosition().y += dir.y *delta;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		
		g.fillRect(Converter.toScreenX(getPosition().x), Converter.toScreenY(getPosition().y), PARTICLE_SIZE, PARTICLE_SIZE);
		
		
	}
}
