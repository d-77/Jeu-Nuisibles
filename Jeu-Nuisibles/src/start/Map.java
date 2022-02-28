package start;

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
	
	//initialize the map with an Arena object for each element of the array (the map)
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
		//ansi escape console: colors!!!
		for(int i = 0; i < this.length; i++) {
			for(int j = 0; j < this.height; j++) {
				System.out.print((arenaMap[i][j]).display());
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

	public Arena[][] getArenaMap() {
		return arenaMap;
	}

	public void setArenaMap(Arena[][] arenaMap) {
		this.arenaMap = arenaMap;
	}

	
	
}
