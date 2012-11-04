package de.timweb.ld48.entity;

import de.timweb.ld48.util.ImageLoader;
import de.timweb.ld48.util.Sprite;
import de.timweb.ld48.util.Vector2f;

public class PuffAnim extends Entity {
	private int lifetime = 1000;

	public PuffAnim(Vector2f pos) {
		super(pos);
		
		setSprite(new Sprite(ImageLoader.ani_puff, 1, 64));
		setSize(64);
	}


	@Override
	public void update(int delta) {
		super.update(delta);
		
		lifetime -= delta;
		if(lifetime < 0)
			kill();
	}
}
