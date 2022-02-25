package start;

public class Map implements GlobalInterface {
	
	private int length;
	private int height;
	private Arena[][] arenaMap; 
	
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();

	
	public Map(int length, int height) {
	
		this.length = length;
		this.height = height;
		this.arenaMap = new Arena[length][height];
		mapInitialization();
	}
	//initialize the map with an Arena object for each element of the array (the map)
	public void mapInitialization() {
		
		for(int i = 0; i < this.length; i++) {
			for(int j = 0; j < this.height; j++) {
				this.arenaMap[i][j] = new Arena();
			}
		}
		
	}
	
	public void display() {
		TheGblVars.echoDebug(4, "enter in Map.display");
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.length; j++) {
				//TODO next!!!	
			System.out.print(((arenaMap[i][j]).getAnimalList().get(0).getType()));
			}
			System.out.println();
		}
	}
	
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
