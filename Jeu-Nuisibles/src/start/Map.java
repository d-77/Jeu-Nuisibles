package start;

import java.util.ArrayList;
import java.util.Iterator;

public class Map implements GlobalInterface {
	
	private int length;
	private int height;
	private Arena[][] arenaMap; 
	
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();
	
	/*
	 * 		Constructors
	 */
	
	public Map(int length, int height) {
	
		this.length = length;
		this.height = height;
		this.arenaMap = new Arena[length][height];
		mapInitialization();
	}

	/*
	 * 		Methods
	 */
	
	/**
	 * initialize the map with an Arena object for each element of the array (the map)
	 */
	public void mapInitialization() {
		
		for(int i = 0; i < this.length; i++) {
			for(int j = 0; j < this.height; j++) {
				this.arenaMap[i][j] = new Arena();
			}
		}
	}
	
	/**
	 * add an animal into the map in the box x,y 
	 * The coordinates are known by the actor
	 * 
	 * @param dude
	 * 
	 */
	public void addAnimal(Animal dude) {
		this.arenaMap[dude.getX()][dude.getY()].addAnimal(dude);
	}
	
	/**
	 * remove an animal from the map
	 * The coordinates are known by the actor
	 *
	 * @param dude
	 * 
	 */
	public void removeAnimal(Animal dude) {
		this.arenaMap[dude.getX()][dude.getY()].removeAnimal(dude);
	}
	
	public void display() {
		TheGblVars.echoDebug(5, "enter in Map.display"); //5 no display
		int i,j;
		//for( j = (this.height - 1); j > 0; j--) {
		for( j = 0; j < this.height; j++) {
			for( i = 0; i < this.length; i++) {//begin with the highest y
				System.out.print((arenaMap[i][j]).display());
			}
			System.out.println();
		}
	}
	
	/**
	 * 
	 * @return a list of animals to move in adjacent empty rooms
	 */
	public ArrayList<Animal> launchFight() {
		int i,j;
		ArrayList<Animal> movelList;
		movelList = new ArrayList<Animal>();

		for( i = 0; i < this.length; i++) {
			for( j = 0; j < this.height; j++) {
				movelList.addAll(arenaMap[i][j].launchFight());
			}
		}
		return movelList;
	}
	
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the distance between (x1,y1) and (x2,y2)
	 */
	public int distance(int x1,int y1,int x2,int y2) {

		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	/**
	 *  propose randomly an available room ie without an animal
	 * 
	 * @param x coord of the center of the search
	 * @param y coord of the center of the search
	 * @param r distance max from the center of the search
	 *
	 * @return theResult[0] = x; 	theResult[1] = y;
	 * 
	 */
	public int[] availableRoom(int x, int y, int r){
		//TODO move this in Map object
		// manage the case of there isn't available room (or is cannot randomly found)
		int nbAttempts = 0;
		int maxAttempts = this.length * this.height * 10; // 10 times the map surface is a very good limit
		
		int[] theResult = new int[2];
		
		int x_random ;
		int y_random ;
		//DCL: rajouter la condition de distance
		//DCL modifier l'appel en rajoutant les parametres
		do {
			nbAttempts++; // stop when nbAttempts > maxAttempts
			x_random = (int) (Math.random() * (this.length));
			y_random = (int) (Math.random() * (this.height));
			TheGblVars.echoDebug(4," x,y : " + x_random + ","+ y_random);
		} while ((! (arenaMap[x_random][y_random].isEmpty())) && (nbAttempts <= maxAttempts)); // empty condition and limited loop
		
		if (arenaMap[x_random][y_random].isEmpty()) {
			theResult[0] = x_random;
			theResult[1] = y_random;
		} else {
			TheGblVars.echoDebug(2, "Error: no available room is found: the numbers of animals may be too large");
			theResult[0] = -1;
			theResult[1] = -1;
		}

		return theResult;
	}
	
	public void relocateAnimal(Iterator iterator) {
		// TODO Auto-generated method stub
		
	}

	
	/*
	 * 		Setters & Getters
	 */
	
	public int getLength() {
		return length;
	}
	public int getHeight() {
		return height;
	}

}
