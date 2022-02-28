package start;

import java.util.ArrayList;

public class Map_listOfLists implements GlobalInterface {
	
	private int length;
	private int height;
	private ArrayList<ArrayList<Arena>> arenaMap;
	// private ArrayList<Arena>[] arenaMap; //an array of lists of arenas Pas possible 
	// de faire un tableau de listes car une liste n'a pas de taille fixe
	//voir https://community.oracle.com/tech/developers/discussion/2062620/cannot-create-a-generic-array-of-arraylist
	
	// http://blog.paumard.org/cours/java-api/chap01-api-collection-list.html
	// https://eticweb.info/tutoriels-java/deplacer-des-elements-dans-un-arraylist/
	// https://www.developpez.net/forums/d1281945/java/general-java/liste-liste-java/
////	private Arena arenaTmp; // a working variable
	
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();
	
	/*
	 * 		Constructors
	 */
	
	public Map_listOfLists(int length, int height) {
	
		this.length = length;
		this.height = height;
		this.arenaMap = new ArrayList<ArrayList<Arena>>();
////		this.arenaTmp = new Arena();
		
		mapInitialization();
	}

	/*
	 * 		Methods
	 */
	
	/**
	 * initialize the map with an Arena list for each line of the map
	 */
	public void mapInitialization() {
		//TODO
		for(int i = 0; i < this.height; i++) {
			this.arenaMap.add(i, new ArrayList<Arena>()) ;
		}		
	}
	
	/**
	 * 	add an animal into the map
	 * 
	 * @param dude
	 * 
	 */
	public void addAnimal(Animal dude) {
	//	this.arenaMap[dude.getY()].addAnimal(dude);
	}
	
	public void display() {
		TheGblVars.echoDebug(5, "enter in Map.display"); //5 no display
		//ansi escape console: colors!!!
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.length; j++) {
				
				
				
	//			System.out.print((arenaMap[i].l'élementde laliste).display());
			}
			System.out.println();
		}
	}
	
	
	/*
	 * 		Setters & Getters
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
/*
	public Arena[][] getArenaMap() {
		return arenaMap;
	}
*/
	public void setArenaMap(Arena[][] arenaMap) {
//		this.arenaMap = arenaMap;
	}

	
	
}
