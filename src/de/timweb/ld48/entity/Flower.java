package de.timweb.ld48.entity;

import java.util.ArrayList;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.util.ImageLoader;
import de.timweb.ld48.util.Sprite;
import de.timweb.ld48.util.Vector2f;
import de.timweb.ld48.world.World;

public class Flower extends Pickup{

	private static final double PICKUP_RADIUS = 20;


	public Flower(Vector2f pos) {
		super(pos);
	
		setSprite(new Sprite(ImageLoader.flower, 1, 24));
		setSpriteSpeed(0.5f);
		setSize(24);
	}

	
	@Override
	public void update(int delta) {
		super.update(delta);
		
		for(Bug b: Game.getPlayersBug()){
			if(b instanceof PlayerBug && ((PlayerBug) b).isPlayerControlled()){
				if(getPosition().distance(b.getPosition()) < PICKUP_RADIUS){
					((PlayerBug) b).onPickup(this);
					kill();
				}
				
			}
		}
	}
	@Override
	protected void onKilled() {
		
		Vector2f vector = new Vector2f((float)Math.random()*World.SIZE_X,(float) Math.random()*World.SIZE_Y);
		while(!World.isValidPosition(vector)){
			vector = new Vector2f((float)Math.random()*World.SIZE_X,(float) Math.random()*World.SIZE_Y);
		}
		Game.addEntity(new Flower(vector));
	}
}
