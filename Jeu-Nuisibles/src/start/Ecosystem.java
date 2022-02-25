package start;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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
	 * Methods
	 */
	
	/* 
	 * Displays the ecosystem
	 * 
	 */
	public void display()
	{
		System.out.println("Cycle " + this.cycleCounter +
				". The survival counters should be shown here");
		mapArena.display();
		
	}
	
	/* Factory of the animals.... */
	void animalsGeneration() {
		// executed in the constructor of Ecosystem
		// initialization of the map

		mapArena  = new Map(length, height);
		Arena[][] m = mapArena.getArenaMap();
				
//		// generation of the animals and they are added in a list for each position	
//		ArrayList<Animal> listAnimals1 = new ArrayList<Animal>();
//		ArrayList<Animal> listAnimals2 = new ArrayList<Animal>();
		
		// generation of ratsNumber rats,  pigeonsNumber, zombiesNumber
		this.generateActors(actorType.rat, ratsNumber, m);
		this.generateActors(actorType.pigeon, pigeonsNumber, m);
		this.generateActors(actorType.zomby, zombiesNumber, m);
		
//		//TODO faire les boucles for 
//		listAnimals1.add(new Animal(actorType.rat, 2, 1, 0, 0));
//		listAnimals2.add(new Animal(actorType.pigeon, 2, 1, 1, 1));
		
		// set the animals into the map
		//TODO parcourir la liste listAnimals et positionner dans la map
//		m[0][0].setAnimalList(listAnimals1);
//		m[1][1].setAnimalList(listAnimals2);
//		TheGblVars.echoDebug(3, ""+ m[0][0].getAnimalList().get(0).getType());
//		TheGblVars.echoDebug(3, ""+ m[1][1].getAnimalList().get(0).getType());
	}
	
	/*
	 * propose randomly a room without an animal
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
	
	// Generates n actors of one type and set them in available rooms into the map
	private void generateActors(actorType type, int nb, Arena[][] m) {

		int[] theRoom = new int[2];
		Animal MyPet;
		for (int j = 0; j < nb; j++) {
			theRoom = availableRoom(); 
			//TODO manage the case of there isn't available room 
			MyPet = new Animal(type, theSpeed(type), 1, theRoom[0], theRoom[1]);
			m[theRoom[0]][theRoom[1]].addAnimal(MyPet);
		}
	}

	// determines the speed of each kind of actor
	public int theSpeed(actorType type ) {
		int calcul = 0;
		switch (type) {
		case zomby:
			calcul = 1;
			break;
		case rat:
			calcul = 2;
			break;
		case pigeon:
			calcul = 3;
			break;
		default:
			calcul = -1;
			break;
		}
		return calcul;
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
	
}
