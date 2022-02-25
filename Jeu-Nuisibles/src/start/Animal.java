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
	
	// compute a random movement based on the characteristics of the actor
	public int[] movement() {
		
		int[] theResult = new int[2];
		int shift_x = 0;
		int shift_y = 0;
		int direction_y = 0; // -1 or 1 to go up or down
		
		if ((Math.random() - 0.5) >= 0) {
			direction_y = 1;
		} else {
			direction_y = -1;
		}
			
		shift_x =  (int) (Math.random() * (2* this.speed) - this.speed );
		shift_y = direction_y * (this.speed - Math.abs(shift_x));
		theResult[0] = shift_x;
		theResult[1] = shift_y;
		TheGblVars.echoDebug(4, "speed s_x s_y dir_y: " +
				this.speed +", "+ shift_x +", "+ shift_y +", "+ direction_y);

		return theResult;
		
		/*
		 * //TODO déplacer ce code dans Ecosystem class
		
		// compute the new coordinates of the actor
		int new_x;
		int new_y;
		new_x = this.x + shift_x;
		new_y = this.y + shift_y;
		switch (this.type) {
		case zomby:
			new_x = Math.min(new_x, new_y)
			break;

		default:
			break;
		}
		 */
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
