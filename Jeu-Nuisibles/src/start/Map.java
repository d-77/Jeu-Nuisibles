package start;

public class Map {
	
	private int length;
	private int height;
	private Arena[][] arenaMap; 
	
	public Map(int length, int height) {
	
		this.length = length;
		this.height = height;
		this.arenaMap = new Arena[length][height];
		mapInitialization();
	}
	
	public void mapInitialization() {
		
		for(int i = 0; i < this.length; i++) {
			for(int j = 0; j < this.height; j++) {
				System.out.println("mapIniztalization");
				this.arenaMap[i][j] = new Arena();
				System.out.println("mapIniztalization2");
			}
		}
		
	}
	
	public void displayMap() {
		System.out.println("Show map");
		for(int i = 0; i < this.length; i++) {
			for(int j = 0; j < this.length; j++) {
				//System.out.print("i,j " + i +","+j +" - ");	
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
