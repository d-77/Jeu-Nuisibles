package start;

import java.util.ArrayList;

public class Ecosystem {
	
	private int length;
	private int height;
	private int	ratsNumber;
	private int pigeonsNumber;
	private int zombiesNumber;
	private int cycleCounter;
	private int cycleMax;
	private Map mapArena;
	
	
	
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
	
	
	public void animalsGeneration() {
		Arena[][] m;
		ArrayList<Animal> listAnimals = new ArrayList<Animal>();
		mapArena  = new Map(length, height);
		m = mapArena.getArenaMap();
		
		
		listAnimals.add(new Animal("Rat", 2, 1, 0, 0));
		
		m[0][0].setAnimalList(listAnimals);
		System.out.println("animalsGeneration : "+ m[0][0].getAnimalList().get(0).getType());
	}
	
	public void displayEcosystem()
	{
		System.out.println("display ecosystem 1 rat en 0,0");
		mapArena.displayMap();
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
