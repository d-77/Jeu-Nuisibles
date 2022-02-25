package start;

public class Animal implements GlobalInterface {
	
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();	

	private static int counter = 0;
	private static int id;
	private actorType type;
	private int speed;
	private int life;
	private int x;
	private int y;
	
	// Constructors
	
	public Animal(actorType type, int speed, int life, int x, int y){
		
		id = counter;
		counter++;
		this.type = type;
		this.speed = speed;
		this.life = life;
		this.x = x;
		this.y = y;
	}
	
	// setters and getters
	
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
	
	/**
	 * @return the id
	 */
	public static int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public static void setId(int id) {
		Animal.id = id;
	}
	
	/**
	 * @return the type
	 */
	public actorType getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(actorType type) {
		this.type = type;
	}
	
	
	/* 
	 * 		Methods
	 */
	
	
	/**
	 * 
	 * @return
	 * returns the graphic representation of the animal
	 */
	public String display() {
		
		String theResult;

		switch (this.type) {
		case zomby:
			theResult = "Z";
			break;
		case rat:
			theResult = "R";
			break;
		case pigeon:
			theResult = "P";
			break;
		default:
			theResult = "Error-type";
			break;
		}
		return theResult;
	}

	// displays the attributes 
	public String displayAttributes() {
		return this.type + " x: " + this.x + " y: " + this.y + " life : " + this.life +
				" speed:" + this.speed ;
	}
	
	// perform a random movement
	public void movement() {
		TheGblVars.echoDebug(3, "starting");
	}
	
	//commit death of the animal
	public void death() {
	System.out.println("Death");
	}
	
	// transform the animal to a new type
	public void transform() {
	System.out.println("Transform");
	}
	
}
