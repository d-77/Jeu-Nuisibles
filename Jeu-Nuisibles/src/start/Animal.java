package start;

public class Animal {
	
	private static int counter = 0;
	private static int id;
	private String type;
	private int speed;
	private int life;
	private int x;
	private int y;
	
	public Animal(String type, int speed, int life, int x, int y){
		
		id = counter;
		counter++;
		this.type = type;
		this.speed = speed;
		this.life = life;
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return this.type;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void movement() {
	System.out.println("Movement");
	}
	
	public void death() {
	System.out.println("Death");
	}
	
	public void transform() {
	System.out.println("Transform");
	}
	
}
