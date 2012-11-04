package de.timweb.ld48.util;


public class Vector2f {
	public float x,y;
	
	public Vector2f() {
		this(0, 0);
	}
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public int x(){
		return (int)x;
	}
	public int y(){
		return (int)y;
	}
	
	public Vector2f copy(){
		return new Vector2f(x, y);
	}
	public void set(Vector2f position) {
		x = position.x;
		y = position.y;
	}
	public double distance(Vector2f other) {
		double dx = Math.abs(x-other.x);
		double dy = Math.abs(y-other.y);
		
		return Math.sqrt(dx*dx + dy*dy);
	}
}
