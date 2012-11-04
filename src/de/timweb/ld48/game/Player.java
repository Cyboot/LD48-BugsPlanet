package de.timweb.ld48.game;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.timweb.ld48.entity.Entity;
import de.timweb.ld48.util.Vector2f;

public class Player{
	private Vector2f pos = new Vector2f();
	private int dir;
	private boolean keypressed;
	private boolean sprint;
	private boolean controlsBug;
	private int reversePulse = 1;
	private float alpha = 50;
	private boolean isShooting;

	public Player() {
	}
	

	public void update(int delta) {
		keypressed = true;
		if(Game.isKeyDown(KeyEvent.VK_W) || Game.isKeyDown(KeyEvent.VK_UP))
			dir = Entity.UP;
		else if(Game.isKeyDown(KeyEvent.VK_A)|| Game.isKeyDown(KeyEvent.VK_LEFT))
			dir = Entity.LEFT;
		else if(Game.isKeyDown(KeyEvent.VK_S)|| Game.isKeyDown(KeyEvent.VK_DOWN))
			dir = Entity.DOWN;
		else if(Game.isKeyDown(KeyEvent.VK_D)|| Game.isKeyDown(KeyEvent.VK_RIGHT))
			dir = Entity.RIGHT;
		else
			keypressed = false;
		
		if(Game.isKeyDown(KeyEvent.VK_SHIFT))
			sprint = true;
		else
			sprint = false;
		
		if(Game.isKeyDown(KeyEvent.VK_SPACE))
			isShooting = true;
		else
			isShooting = false;

		alpha += 0.1 * reversePulse * delta;
		if(alpha > 180){
			reversePulse = -1;
			alpha = 180;
		}
		if(alpha < 30){
			reversePulse = 1;
			alpha = 30;
		}
		
	}


	public int getDirection() {
		return dir;
	}


	public boolean keyPressed() {
		return keypressed;
	}

	public boolean isShooting(){
		return isShooting;
	}
	
	public void setPosition(Vector2f pos) {
		this.pos.set(pos);
	}
	public Vector2f getPosition() {
		return pos;
	}
	
	public boolean isSprinting() {
		return sprint;
	}


	public boolean controlsBug() {
		return controlsBug;
	}


	public void releaseBug() {
		controlsBug = false;
	}
	public void startControllBug(){
		controlsBug = true;
	}
	public boolean isControlingBug() {
		return controlsBug;
	}


	public Color getColor() {
		return new Color(0,100,255,(int)alpha);
	}


}
