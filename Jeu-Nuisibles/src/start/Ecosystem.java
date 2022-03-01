package start;

//import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Random;

/**
 * @author daniel.conil
 *
 */
public class Ecosystem implements GlobalInterface {
	
	private int length;
	private int height;
	private int	ratsNumber;
	private int pigeonsNumber;
	private int zombiesNumber;
	private int cycleCounter;
	private int cycleMax;
	private Map mapArena;
	private ArrayList<Animal> AnimalList; 
		
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();
	
	/* 
	 * Constructors
	 * 
	 * */
	
	public Ecosystem(int length, int height, int ratsNumber, int pigeonsNumber, int zombiesNumber,
			int cycleMax) {
		this.length = length;
		this.height = height;
		this.ratsNumber = ratsNumber;
		this.pigeonsNumber = pigeonsNumber;
		this.zombiesNumber = zombiesNumber;
		this.cycleMax = cycleMax;
		this.mapArena = new Map(length, height);
		this.AnimalList = new ArrayList<Animal>();
		animalsGeneration();
	}

	/*
	 * Methods
	 */
	
	/**
	 * animate the ecosystem until the end (no more combats or cyleMax is reached
	 */
	public void animate() {
		boolean noMoreCombats = false;
		
		this.cycleCounter = 1;
		while (noMoreCombats == true || (this.cycleCounter <= this.cycleMax)) {
			// one cycle loop
			this.display();
			System.out.println(); // one line between 2 screens
			// the actors move
			for (Animal animal : this.AnimalList) {
				//remove the actor from the arena
				this.mapArena.removeAnimal(animal);
				animal.move(this.length,this.height);//debug purpose
				//add the the actor to its new arena
				TheGblVars.echoDebug(4, "move: " + animal.displayAttributes());
				this.mapArena.addAnimal(animal);
			}
			// the actors fight
			// display an intermediate screen, where the fights are shown
			//this.display();
			this.cycleCounter++;
			// wait cycleDuration ms
			try  { Thread.sleep(cycleDuration); } catch (Exception e)  { } 

		}	
	}
	
	/* 
	 * Displays the ecosystem
	 * 
	 */
	public void display()
	{
		mapArena.display();
		//TODO add counters: cycle, numbers of actors...
	}
	
	/**
	 *  Factory of the animals....
	 */
	void animalsGeneration() {
		// executed in the constructor of Ecosystem
		// initialization of the map

		Arena[][] m = mapArena.getArenaMap();
						
		// generation of ratsNumber rats,  pigeonsNumber, zombiesNumber
		this.generateActors(actorType.RAT, ratsNumber, m);
		this.generateActors(actorType.PIGEON, pigeonsNumber, m);
		this.generateActors(actorType.ZOMBI, zombiesNumber, m);
		
		
		/* debug
		for (Animal animal : this.AnimalList) {
			animal.movement();//debug purpose
		}
		for (Animal animal : this.AnimalList) {
			animal.movement();//debug purpose
		}
		for (Animal animal : this.AnimalList) {
			animal.movement();//debug purpose
		}
		for (Animal animal : this.AnimalList) {
			animal.movement();//debug purpose
		}
		for (Animal animal : this.AnimalList) {
			animal.movement();//debug purpose
		}
		*/
	}
	

	  /**
	   *  propose randomly an available room ie without an animal
	   * @return theResult[0] = x; 	theResult[1] = y;
	   */
	private int[] availableRoom(){
		
		int[] theResult = new int[2];
		Arena[][] m = mapArena.getArenaMap();
		
		int x ;
		int y ;
		do {
			x = (int) (Math.random() * (this.length));
			y = (int) (Math.random() * (this.height));
			TheGblVars.echoDebug(4," x,y : " + x + ","+ y);
		} while (! (m[x][y].isEmpty())); // empty condition
		
		theResult[0] = x;
		theResult[1] = y;
		return theResult;
	}
	
	/**
	 *  Generates n actors of one type and set them in available rooms into the map
	 * @param type
	 * @param nb
	 * @param m
	 */
	private void generateActors(actorType type, int nb, Arena[][] m) {

		int[] theRoom = new int[2];
		Animal MyPet;
		for (int j = 0; j < nb; j++) {
			theRoom = availableRoom(); 
			//TODO manage the case of there isn't available room 
			MyPet = new Animal(type, theSpeed(type), 1, theRoom[0], theRoom[1]);
			m[theRoom[0]][theRoom[1]].addAnimal(MyPet); // put the animal in the map
			this.AnimalList.add(MyPet); // add the same animal in a list in order to get an easy access 
		}
	}

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
	 *  Display all the remaining animals
	 */
	private void showRemainingAnimals() {
		String TheResult = "";
		for (Animal animal : this.AnimalList) {
			TheResult = TheResult + animal.getType()+ " ";
		}
		System.out.println(TheResult);
	}

	/**
	 * Setters and Getters
	 */
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getRatsNumber() {
		return ratsNumber;
	}
	public void setRatsNumber(int ratsNumber) {
		this.ratsNumber = ratsNumber;
	}
	public int getPigeonsNumber() {
		return pigeonsNumber;
	}
	public void setPigeonsNumber(int pigeonsNumber) {
		this.pigeonsNumber = pigeonsNumber;
	}
	public int getZombiesNumber() {
		return zombiesNumber;
	}
	public void setZombiesNumber(int zombiesNumber) {
		this.zombiesNumber = zombiesNumber;
	}
	public int getCycleCounter() { return cycleCounter; } 
	// must not be used	  public void setCycleCounter(int cycleCounter) { this.cycleCounter = cycleCounter; }
	public int getCycleMax() {
		return cycleMax;
	}
	public void setCycleMax(int cycleMax) {
		this.cycleMax = cycleMax;
	}
	
}
