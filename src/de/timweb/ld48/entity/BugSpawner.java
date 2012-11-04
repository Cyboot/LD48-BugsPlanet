package de.timweb.ld48.entity;

import java.awt.Color;
import java.awt.Graphics;

import de.timweb.ld48.game.Game;
import de.timweb.ld48.util.ImageLoader;
import de.timweb.ld48.util.Sprite;
import de.timweb.ld48.util.Vector2f;
import de.timweb.ld48.world.World;

public class BugSpawner extends Entity {
	public static final int MAX_LASTSPAWN = 30 * 1000;
	private static final int STARTLEVEL	 = 10;
	private static final int MAX_LEVELUP = 4000;
	private static final int MAX_AUTOLEVELUP = 20*1000;

	private int team;
	private int lastSpawn;
	private int lastAutoLevel = MAX_AUTOLEVELUP/2;
	private int lastAttacked = 2000;

	private int level_spawn = MAX_LASTSPAWN;
	private int level_strength = 1;

	private float damage;
	private float health = 100;
	private int lastLevelUp;
	private boolean notified = false;

	public BugSpawner(Vector2f pos, int team) {
		super(pos);
		this.team = team;
		switch (team) {
		case Game.PLAYER:
			setSprite(new Sprite(ImageLoader.spawner_blue, 1, 48));
			break;
		case Game.ENEMY_RED:
			setSprite(new Sprite(ImageLoader.spawner_red, 1, 48));
			break;
		case Game.ENEMY_ORANGE:
			setSprite(new Sprite(ImageLoader.spawner_orange, 1, 48));
			break;
		case Game.ENEMY_YELLOW:
			setSprite(new Sprite(ImageLoader.spawner_yellow, 1, 48));
			break;
		}

		level_spawn = STARTLEVEL;
		setSize(48);
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if(lastLevelUp <= MAX_LASTSPAWN)
			lastLevelUp += delta;
		
		lastAttacked += delta;
		if(lastAttacked < 1000){
			if(Math.random() > 0.98){
				if (team == Game.PLAYER){
					if(Math.random() > 0.7)
						Game.addFriendBug(new PlayerBug(getPosition().copy(), 10, null));
				}
				else
					Game.addEnemyBug(new Bug(getPosition().copy(),10,team));
			}
		}
		
		lastSpawn += level_spawn * delta;
		lastAutoLevel += delta;
		
		if(lastAutoLevel > MAX_AUTOLEVELUP){
			lastAutoLevel = 0;
			level_spawn++;
		}
		
		if (lastSpawn > MAX_LASTSPAWN && Game.getEntityCount() < Game.ENTITIY_LIMIT) {
			lastSpawn = 0;
			if (team == Game.PLAYER)
				Game.addFriendBug(new PlayerBug(getPosition().copy(), level_strength, null));
			else
				Game.addEnemyBug(new Bug(getPosition().copy(),level_strength,team));
		}
	}

	public void hurt(float damage){
		lastAttacked = 0;
		this.damage -= damage*0.3f;
		if(this.damage < 0){
			this.damage = 100;
			if(level_spawn > STARTLEVEL)
				level_spawn--;
		}
		if(level_spawn <= STARTLEVEL){
			this.health -= damage * 0.005f;
		}
		if(this.health < 0)
			kill();
	}
	@Override
	protected void onKilled() {
		if(notified)
			return;
		
		String str = "You destroyed the ";
		
		switch (team) {
		case Game.ENEMY_RED:
			str += "RED";
			break;
		case Game.ENEMY_ORANGE:
			str += "ORANGE";
			break;
		case Game.ENEMY_YELLOW:
			str += "YELLOW";
			break;
		}
		
		str += " nest!";
		level_spawn = STARTLEVEL-1;
		
		Game.getGui().notificate(str);
		notified = true;
		
		Game.addEntity(new PuffAnim(getPosition()));
	}
	
	
	public int getTeam() {
		return team;
	}
	
	public int getHealth(){
		return health > 0 ?(int)health : 0;
	}
	
	public int getLevel(){
		return level_spawn-STARTLEVEL+1;
	}

	public void help(float f) {
		if(lastLevelUp < MAX_LEVELUP)
			return;
		
		this.damage += f*0.5f;
		if(damage > 100){
			damage = 0;
			level_spawn++;
			
			Game.getGui().notificate("Level Up! (earliest next level: 4 sec)");
			lastLevelUp = 0;
		}
	}
}
