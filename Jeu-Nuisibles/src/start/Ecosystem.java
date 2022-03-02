package start;

//import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Random;
import java.util.Iterator;

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
	private ArrayList<Animal> ecoAnimalList; 
		
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
		this.ecoAnimalList = new ArrayList<Animal>();
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
		int nbAlivePopulations = 3;
		ArrayList<Animal> movelList;

		this.cycleCounter = 1;
		while (noMoreCombats == false && (this.cycleCounter <= this.cycleMax)) {
			// one cycle loop
			this.display();
			System.out.println(); // one line between 2 screens
			// the actors move
			for (Animal animal : this.ecoAnimalList) {
				//remove the actor from its arena
				TheGblVars.echoDebug(1, "before remove: " + animal.displayAttributes());
				this.mapArena.removeAnimal(animal);
				animal.randomMove(this.length,this.height);//debug purpose
				//add the the actor to its new arena
				TheGblVars.echoDebug(4, "move: " + animal.displayAttributes());
				this.mapArena.addAnimal(animal);
			}
			// the actors fight
			// display an intermediate screen, where the fights are shown
			System.out.println("\nFight phase"); // one line between 2 screens
			this.display();
			System.out.println(); // one line between 2 screens
			
			movelList = this.mapArena.launchFight(); 
			
			//FIXME ****************!!!!!!!!!!!!!!!!!!!!!
			
			TheGblVars.echoDebug(2, "size of movelList: " + movelList.size());
			//replace the animals which are several in the same place
			for (Iterator iterator = movelList.iterator(); iterator.hasNext();) {
				Animal animal = (Animal) iterator.next();
				mapArena.relocateAnimal(animal);
			}
			remainingAnimals(); //update the AnimalList after the fights
			System.out.println(); // one line between 2 screens
			
			if (zombiesNumber > 0) {nbAlivePopulations = 1;} else {nbAlivePopulations = 0;}
			if (ratsNumber > 0) {nbAlivePopulations++;} 
			if (pigeonsNumber > 0) {nbAlivePopulations++;}
			noMoreCombats = (nbAlivePopulations == 1); //there is only on kind of actors: they can't fight themselves
			
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
		System.out.println("Cycle " + cycleCounter + ", nb-z: " + zombiesNumber + ", nb-r: " + ratsNumber  +
				", nb-p: " +  pigeonsNumber );
		mapArena.display();
	}
	
	/**
	 *  Factory of the animals....
	 */
	void animalsGeneration() {
		// executed in the constructor of Ecosystem
		// initialization of the map
						
		// generation of ratsNumber rats,  pigeonsNumber, zombiesNumber
		this.generateActors(actorType.RAT, ratsNumber);
		this.generateActors(actorType.PIGEON, pigeonsNumber);
		this.generateActors(actorType.ZOMBI, zombiesNumber);
	}
	
	/**
	 *  Generates n actors of one type and set them in available rooms into the map
	 * @param type
	 * @param nb
	 */
	private void generateActors(actorType type, int nb) {

		int[] theRoom = new int[2];
		Animal MyPet;
		for (int j = 0; j < nb; j++) {
			theRoom = mapArena. availableRoom(this.length / 2, this.height / 2, Math.max(this.length, this.height));
			// the area chosen is the entire map.
			// check the unavailable room case
			if (theRoom[0] != -1) {
				//there is an available room
				MyPet = new Animal(type, 1, theRoom[0], theRoom[1]);
				this.mapArena.addAnimal(MyPet);// put the animal in the map
				this.ecoAnimalList.add(MyPet); // add the same animal in a list in order to get an easy access 
			} else {
				TheGblVars.echoDebug(2, "The " + type + " number " + (j+1) + 
						" is not generated because of unfound empty room");
			}
		}
	}


	/**
	 *  Update the animal list and counters with the remaining animals
	 */
	private void remainingAnimals() {
		String msg = "";
		//https://www.java67.com/2015/10/how-to-solve-concurrentmodificationexception-in-java-arraylist.html
		/*
		Exception in thread "main" java.util.ConcurrentModificationException
		 
		at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
		at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967)
		at start.Ecosystem.remainingAnimals(Ecosystem.java:176)
		*/
		/* le code ci-dessous génère l'erreur ci-dessus
		for (Animal animal : this.animalList) {
			if (animal.getLife() == 0) {
				switch (animal.getType()) {
				case ZOMBI:
					this.zombiesNumber--;
					break;
				case RAT:
					this.ratsNumber--;
					break;
				case PIGEON:
					this.pigeonsNumber--;
					break;

				default:
					TheGblVars.echoDebug(0, "This animal : " + animal.getId() + 
							" has an unknown type: " + animal.getType() );
					break;
				}// end of switch
				TheGblVars.echoDebug(2, "This animal : " + animal.getId() + " " + animal.getType() +
						" is removed");
				this.animalList.remove(animal);
			}// end of if dead
		}//end of for
		*/
		
		for (Iterator iterator = ecoAnimalList.iterator(); iterator.hasNext();) {
			Animal animal = (Animal) iterator.next();
			if (animal.getLife() == 0) { // is dead
				switch (animal.getType()) {
				case ZOMBI:
					this.zombiesNumber--;
					TheGblVars.echoDebug(0, "zombiesNumber : " + zombiesNumber );
					break;
				case RAT:
					this.ratsNumber--;
					TheGblVars.echoDebug(0, "ratsNumber : " + ratsNumber );
					break;
				case PIGEON:
					this.pigeonsNumber--;
					TheGblVars.echoDebug(0, "pigeonsNumber : " + pigeonsNumber );
					break;

				default:
					TheGblVars.echoDebug(0, "This animal : " + animal.getId() + 
							" has an unknown type: " + animal.getType() );
					break;
				}// end of switch
				TheGblVars.echoDebug(1, "This animal : " + animal.getId() + " " + animal.getType() +
						" is removed");
				iterator.remove();
			}// end of if dead
		} // end of for
		
		//Display the remaining animals

		for (Animal animal : this.ecoAnimalList) {
			msg = msg + animal.display() + " ";
		}
		System.out.println("Animals presents in the ecosystem: " + msg);
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
