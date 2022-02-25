package start;

import java.util.ArrayList;

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
		animalsGeneration();
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
	public int getCycleCounter() {
		return cycleCounter;
	}
	public void setCycleCounter(int cycleCounter) {
		this.cycleCounter = cycleCounter;
	}
	public int getCycleMax() {
		return cycleMax;
	}
	public void setCycleMax(int cycleMax) {
		this.cycleMax = cycleMax;
	}
	
	/**
	 * Methods
	 */
	
	/* Displays the main info of the animal */
	public void display()
	{
		System.out.println("display ecosystem 1 rat en 0,0");
		mapArena.display();
	}
	
	/* Factory of the animals.... */
	void animalsGeneration() {
		// executed in the constructor of Ecosystem
		// initialization of the map
		Arena[][] m;
		mapArena  = new Map(length, height);
		m = mapArena.getArenaMap();
		// generation of the animals and they are added in a list for each position	
		ArrayList<Animal> listAnimals1 = new ArrayList<Animal>();
		ArrayList<Animal> listAnimals2 = new ArrayList<Animal>();
		
		// generation of ratsNumber rats,  pigeonsNumber, zombiesNumber
		//TODO faire les boucles for 
		listAnimals1.add(new Animal(actorType.rat, 2, 1, 0, 0));
		listAnimals2.add(new Animal(actorType.pigeon, 2, 1, 1, 1));
		
		// set the animals into the map
		//TODO parcourir la liste listAnimals et positionner dans la map
		m[0][0].setAnimalList(listAnimals1);
		m[1][1].setAnimalList(listAnimals2);
		TheGblVars.echoDebug(3, ""+ m[0][0].getAnimalList().get(0).getType());
		TheGblVars.echoDebug(3, ""+ m[1][1].getAnimalList().get(0).getType());
	}
}
