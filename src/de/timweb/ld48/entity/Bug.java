package de.timweb.ld48.entity;

import java.awt.Graphics;
import java.util.ArrayList;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.util.ImageLoader;
import de.timweb.ld48.util.Sprite;
import de.timweb.ld48.util.Vector2f;

public class Bug extends Entity {
	private static final int MAX_DIRCHANGE = 1000;
	private static final int HURT_RADIUS = 32;

	public static final int WEAK = 1;
	public static final int MIDDLE = 5;
	public static final int STRONG = 10;

	private int lastHurt;
	private int lastDirChange = 0;
	protected float speed = 0.1f;
	private float health = 100;

	private Particle particles[];
	
	private int team;
	private int level;

	public Bug(Vector2f pos, int level, int team) {
		super(pos);

		this.team = team;
		this.level = level;

		setDirection((int) (Math.random() * 4));
		if(level == STRONG){
			
			switch (team) {
			case Game.PLAYER:
				setSprite(new Sprite(ImageLoader.bug_blue_32, 4, 32));
				break;
			case Game.ENEMY_RED:
				setSprite(new Sprite(ImageLoader.bug_red_32, 4, 32));
				break;
			case Game.ENEMY_ORANGE:
				setSprite(new Sprite(ImageLoader.bug_orange_32, 4, 32));
				break;
			case Game.ENEMY_YELLOW:
				setSprite(new Sprite(ImageLoader.bug_yellow_32, 4, 32));
				break;
			}
			health *= 5;
			setSize(32);
		}else{
			
			switch (team) {
			case Game.PLAYER:
				setSprite(new Sprite(ImageLoader.bug_blue_16, 4, 16));
				break;
			case Game.ENEMY_RED:
				setSprite(new Sprite(ImageLoader.bug_red_16, 4, 16));
				break;
			case Game.ENEMY_ORANGE:
				setSprite(new Sprite(ImageLoader.bug_orange_16, 4, 16));
				break;
			case Game.ENEMY_YELLOW:
				setSprite(new Sprite(ImageLoader.bug_yellow_16, 4, 16));
				break;
			}
			setSize(16);
		}
		
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if(lastHurt > 0){
			for(Particle p:particles)
				p.update(delta);
			
			lastHurt -= delta;
		}
		updateAttack(delta);
		
		if(health < 0)
			kill();

		if (this instanceof PlayerBug && ((PlayerBug) this).isPlayerControlled())
			return;

		lastDirChange += delta;
		if (lastDirChange > MAX_DIRCHANGE) {
			lastDirChange = (int) (Math.random()*500)-250;
			setDirection((int) (Math.random() * 4));
		}
		move(getDirectionVector().x * speed * delta, getDirectionVector().y * speed * delta);
		
		
		
	}
	
	private void updateAttack(int delta) {
		ArrayList<Bug> enemys = null;
		if(team == Game.PLAYER){
			enemys = Game.getEnemyBugs();
		}
		else{
			enemys = Game.getPlayersBug();
		}
		
		float damage = 0.05f * level *delta;
		
		for(Bug b:enemys){
			if(b == this)
				continue;
			if(getPosition().distance(b.getPosition()) < HURT_RADIUS){
				b.hurt(damage);
				this.hurt(damage);
			}
		}
		if(this instanceof PlayerBug)
			return;
		
		BugSpawner[] spawner = Game.getSpawner();
		for(BugSpawner b:spawner ){
			if(getPosition().distance(b.getPosition()) < HURT_RADIUS){
				if(b.getTeam() == Game.PLAYER)
					b.hurt(0.01f *damage *delta);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		
		if(lastHurt > 0){
			for(Particle p:particles)
				p.render(g);
		}
	}
	
	public void hurt(float damage){
		health -= damage;
		lastHurt = 500;
		
		particles = new Particle[6];
		for(int i=0;i<particles.length;i++)
			particles[i] = new Particle(getPosition().copy());
	}

	public int getTeam() {
		return team;
	}
	
	public float getHealth() {
		return health;
	}
	public int getLevel() {
		return level;
	}
}
