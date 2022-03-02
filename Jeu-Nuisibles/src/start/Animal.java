package start;

import start.GlobalInterface.actorType;

public class Animal implements GlobalInterface {
	
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();	

	private static int counter = 0;
	private static int [] countersByType;
	private int id; 
	/* The id is unique for each type of actor:
	 * a rat and a pigeon may have the same id '1' for example
	 */
	private actorType type;
	private int speed;
	private int life;
	private int x;
	private int y;
	private int previous_x;
	private int previous_y;
	
	/*
	 ******	Constructors
	*/
	
	public Animal(actorType type, int life, int x, int y){
		
		this.type = type;
		this.speed = theSpeed(type);
		this.life = life;
		this.x = x;
		this.y = y;
		this.previous_x = x;
		this.previous_y = y;
		// compute the counters
		counter++; // number of created animals
		
		if (countersByType == null) {
			TheGblVars.echoDebug(4, "countersByType instantiation");
			// create an array of counters for each type of actors
			countersByType = new int[actorType.values().length];
			//initialise the counters to 0
			for (int i : countersByType) { countersByType[i] = 0; }
		}
		id = countersByType[type.ordinal()];
		countersByType[type.ordinal()] = countersByType[type.ordinal()] + 1; 
	}
	
	/*
	 ***** setters and getters
	*/
	
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
	public int getId() {
		return id;
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
	 ******* Methods
	 */
		
	/**
	 *  determines the speed of each kind of actor
	 * @param type
	 * @return the speed
	 * 
	 * The speed value must be less than min of the length and height of the map
	 */
	public int theSpeed(actorType type ) {
		int calcul = 0;
		switch (type) {
		case ZOMBI:
			calcul = 1;
			break;
		case RAT:
			calcul = 2;
			break;
		case PIGEON:
			calcul = 3;
			break;
		default:
			calcul = -1;
			break;
		}
		return calcul;
	}
	
	/**
	 * @return: the graphic representation of the animal
	 */
	public String display() {
		
		String theResult;
		theResult =  String.format("%02d",this.id);

		switch (this.type) {
		case ZOMBI:
			theResult = TheGblVars.colourString(ANSI_YELLOW_BACKGROUND,"Z" + theResult );
			break;
		case RAT:
			theResult = TheGblVars.colourString(ANSI_GREEN,"R" + theResult );
			break;
		case PIGEON:
			theResult = TheGblVars.colourString(ANSI_BLUE,"P" + theResult );
			break;
		default:
			theResult = "Error-type";
			break;
		}
		return theResult;
	}

	/**
	 *  displays the attributes 
	 * @return
	 */
	public String displayAttributes() {
		return this.type + " id: " + this.id + " x: " + this.x + " y: " + this.y + " life : " + this.life +
				" speed:" + this.speed ;
	}
	
	/**
	 *
	 *  compute a random move based on the characteristics of the actor
	 *  the move must remains in the map: 0 < length and 0 < height
	 *  
	 *  the new x,y coordinates are computed, the previous ones are updated
	 *  
	 * @param lenght (the x border of the map)
	 * @param height (the y border of the map)
	 *
	 */
	public void randomMove(int lenght, int height) {
		
		int shift_x = 0;
		int shift_y = 0;
		int direction_y = 0; // -1 or 1 to go up or down
		int new_x;
		int new_y;
		
		if ((Math.random() - 0.5) >= 0) {
			direction_y = 1;
		} else {
			direction_y = -1;
		}
			
		shift_x =  (int) (Math.random() * (2* this.speed) - this.speed );
		shift_y = direction_y * (this.speed - Math.abs(shift_x));
		
		// compute the new coordinates of the actor

		new_x = this.x + shift_x;
		new_y = this.y + shift_y;
		
		switch (this.type) {
		case ZOMBI:
			// a zombi stays stacked to the border, until he changes of direction
			new_x = Math.max(0, Math.min(new_x, lenght - 1));
			new_y = Math.max(0, Math.min(new_y, height - 1));
			// IMPROVE: should create a method for the zombi move
			break;

		default:
			// other actors bounce back
			new_x = bounceBack(new_x, lenght);
			new_y = bounceBack(new_y, height);
			break;
		}
		this.previous_x = this.x;
		this.previous_y = this.y;
		this.x = new_x;
		this.y = new_y;
		TheGblVars.echoDebug(4, "speed s_x s_y dir_y: " +
				this.speed +", "+ shift_x +", "+ shift_y +", "+ direction_y);
		TheGblVars.echoDebug(4, "new_x, new_y: " +
				new_x +", "+ new_y);
	}
	
	/**
	 * 
	 * @return : Computes the coordinate after the bounce against the border
	 * if the coord is in the map returns coord without a modification
	 */
	public int bounceBack(int coord, int border) {
		int theResult = -1; // generates an error must be between 0 and border
		if ((coord < border) && (coord >= 0))  {
			//coord is in the map
			theResult = coord;
		} else {
			if (coord < 0) {
				theResult = - coord;
			} else { // coord > border
				theResult = 2 * border - coord - 2;
			} // end of if (coord < 0)
		} // end of if ((coord < border) and (coord < 0))
		if ((theResult < 0) || (theResult >= border )){
			TheGblVars.echoDebug(0, "Error, the new coord is out of the map: " + theResult);
		}
		return theResult;
	}
	
	//commit death of the animal
	public void death() {
		this.life = 0;
		TheGblVars.echoDebug(2,"Death of the animal: " + this.id);
	}
	
	// transform the animal to a new type
	public void transform(actorType type ) {
		
		if(!this.type.equals(type) ) {
			TheGblVars.echoDebug(0,"transform the animal : " + this.id + " from : " + this.type + " to " + type);
			this.type = type;
			this.speed = theSpeed(type);
			// set a new id in its new type
			this.id = countersByType[type.ordinal()];
			countersByType[type.ordinal()] = countersByType[type.ordinal()] + 1;
		}
		//else nothing to do, its type is not changed
	}
	
}
