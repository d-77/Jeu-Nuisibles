

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

	// use a global object
	private GblVars TheGblVars = GblVars.getInstance();

	/*
	 * Class variables
	 */
	/**
	 * an array of counters, one for each kind of actors
	 */
	//IMPROVE FIXME this attribute is static to give access to Arena Class !!!!!
	// the method in the arena class should set the same kind of attributes and give access to the caller object
	static int[] actorsNumber = new int[nbTypes];

	/*
	 * attributes
	 */

	private int length;
	private int height;
	/**
	 * attributes in order to stop when the ecosystem is stable, it will loop
	 * infinitely...
	 */
	private int cycleCounter;
	private int cycleMax;
	/**
	 * after nbCyclesStable of cycles without changes, in terms of actor population:
	 * stop
	 * written in percentage, should be between 1 and 100
	 */
	private int nbCyclesStable = 10;
	private long cycleDuration = 0; // duration of one cycle in ms
	private String lastEcoSystemState; // a string that represents the state of the ecosystem, a concatenation of
										// counters
	private int lastEcoSystemChange = 1;
	private Map mapArena;
	private ArrayList<Animal> ecoAnimalList;

	/*
	 * Constructors
	 * 
	 */

	public Ecosystem(int length, int height, int ratsNumber, int pigeonsNumber, int zombiesNumber, int cycleMax) {
		this.length = length;
		this.height = height;

		// IMPROVE access in a static way
		// https://www.google.fr/search?q=java+the+static+field+should+be+accessed+in+a+static+way&ei=gPUfYuWJCrPO7_UPreS7kAM&ved=0ahUKEwjlyau-wqj2AhUz57sIHS3yDjIQ4dUDCA4&uact=5&oq=java+the+static+field+should+be+accessed+in+a+static+way&gs_lcp=Cgdnd3Mtd2l6EAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsANKBAhBGABKBAhGGABQsAZY340CYOWTAmgBcAF4AIABAIgBAJIBAJgBAKABAcgBCMABAQ&sclient=gws-wiz

		// IMPROVE the counters of actors should be updated in the generateActors method
		// instead of be set in the constructor
		this.actorsNumber[actorType.RAT.ordinal()] = ratsNumber;
		this.actorsNumber[actorType.PIGEON.ordinal()] = pigeonsNumber;
		this.actorsNumber[actorType.ZOMBI.ordinal()] = zombiesNumber;
		this.cycleMax = cycleMax;
		this.cycleCounter = 1;
		this.lastEcoSystemChange = 1;
		this.lastEcoSystemState = EcoSystemState();

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
		boolean moreCombats = true;
		int nbAlivePopulations = 3;
		ArrayList<Animal> moveList;
		int i;

		this.cycleCounter = 1;
		while (! stopCycle()) {
			// one cycle loop
			this.display();
			System.out.println(); // one line between 2 screens
			// the actors move
			for (Animal animal : this.ecoAnimalList) {
				// remove the actor from its arena
				TheGblVars.echoDebug(1, "before remove: " + animal.displayAttributes());
				this.mapArena.removeAnimal(animal);
				animal.randomMove(this.length, this.height);// debug purpose
				// add the the actor to its new arena
				TheGblVars.echoDebug(4, "move: " + animal.displayAttributes());
				this.mapArena.addAnimal(animal);
			}
			// the actors fight
			// display an intermediate screen, where the fights are shown
			System.out.println("\nFight phase"); // one line between 2 screens
			this.display();
			System.out.println(); // one line between 2 screens

			moveList = this.mapArena.launchFight();

			TheGblVars.echoDebug(2, "size of movelList: " + moveList.size());

			// move the animals because they are too many in the same place

			for (i = 0; i < moveList.size(); i++) {
				TheGblVars.echoDebug(1, " the " + i + " animal of moveList: " + moveList.get(i).displayAttributes());
				mapArena.relocateAnimal(moveList.get(i)); // IMPROVE in case of zombification there is a tentative to
															// remove an object which isn't part of the list
			}

			remainingAnimals(); // update the AnimalList after the fights
			System.out.println(); // one line between 2 screens

			this.cycleCounter++;
			// wait cycleDuration ms
			try {
				Thread.sleep(cycleDuration);
			} catch (Exception e) {
			}
		} // end of while
		this.display(); // displays the last state of the ecosystem
	}

	/*
	 * Displays the ecosystem
	 * 
	 */
	public void display() {
		System.out.println("Cycle " + cycleCounter + ", nb-z: " + this.actorsNumber[actorType.ZOMBI.ordinal()]
				+ ", nb-r: " + this.actorsNumber[actorType.RAT.ordinal()] + ", nb-p: "
				+ this.actorsNumber[actorType.PIGEON.ordinal()]);
		mapArena.display();
	}

	/**
	 * Factory of the animals....
	 */
	void animalsGeneration() {
		// executed in the constructor of Ecosystem
		// initialization of the map

		// generation of ratsNumber rats, pigeonsNumber, zombiesNumber
		this.generateActors(actorType.RAT, this.actorsNumber[actorType.RAT.ordinal()]);
		this.generateActors(actorType.PIGEON, this.actorsNumber[actorType.PIGEON.ordinal()]);
		this.generateActors(actorType.ZOMBI, this.actorsNumber[actorType.ZOMBI.ordinal()]);
	}

	/**
	 * Generates n actors of one type and set them in available rooms into the map
	 * 
	 * @param type
	 * @param nb
	 */
	private void generateActors(actorType type, int nb) {

		// IMPROVE the counters of actors should be updated in this method
		// instead of be set in the constructor
		int[] theRoom = new int[2];
		Animal MyPet;
		for (int j = 0; j < nb; j++) {
			theRoom = mapArena.availableRoom(this.length / 2, this.height / 2, Math.max(this.length, this.height));
			// the area chosen is the entire map.
			// check the unavailable room case
			if (theRoom[0] != -1) {
				// there is an available room
				MyPet = new Animal(type, 1, theRoom[0], theRoom[1]);
				this.mapArena.addAnimal(MyPet);// put the animal in the map
				this.ecoAnimalList.add(MyPet); // add the same animal in a list in order to get an easy access
			} else {
				TheGblVars.echoDebug(2,
						"The " + type + " number " + (j + 1) + " is not generated because of unfound empty room");
			}
		}
	}

	/**
	 * Update the animal list and counters with the remaining animals
	 */
	private void remainingAnimals() {
		String msg = "";

		// https://www.java67.com/2015/10/how-to-solve-concurrentmodificationexception-in-java-arraylist.html

		/*
		 * Exception in thread "main" java.util.ConcurrentModificationException
		 * 
		 * at
		 * java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1013)
		 * at java.base/java.util.ArrayList$Itr.next(ArrayList.java:967) at
		 * start.Ecosystem.remainingAnimals(Ecosystem.java:176)
		 */

		/*
		 * le code ci-dessous g�n�re l'erreur ci-dessus for (Animal animal :
		 * this.animalList) { if (animal.getLife() == 0) { switch (animal.getType()) {
		 * case ZOMBI: this.zombiesNumber--; break; case RAT: this.ratsNumber--; break;
		 * case PIGEON: this.pigeonsNumber--; break;
		 * 
		 * default: TheGblVars.echoDebug(0, "This animal : " + animal.getId() +
		 * " has an unknown type: " + animal.getType() ); break; }// end of switch
		 * TheGblVars.echoDebug(2, "This animal : " + animal.getId() + " " +
		 * animal.getType() + " is removed"); this.animalList.remove(animal); }// end of
		 * if dead }//end of for
		 */

		for (Iterator iterator = ecoAnimalList.iterator(); iterator.hasNext();) {
			Animal animal = (Animal) iterator.next();
			if (animal.getLife() == 0) { // is dead
				switch (animal.getType()) {
				case ZOMBI:
					this.actorsNumber[actorType.ZOMBI.ordinal()]--;
					TheGblVars.echoDebug(2, "zombiesNumber : " + this.actorsNumber[actorType.ZOMBI.ordinal()]);
					break;
				case RAT:
					this.actorsNumber[actorType.RAT.ordinal()]--;
					TheGblVars.echoDebug(2, "ratsNumber : " + this.actorsNumber[actorType.RAT.ordinal()]);
					break;
				case PIGEON:
					this.actorsNumber[actorType.PIGEON.ordinal()]--;
					TheGblVars.echoDebug(2, "pigeonsNumber : " + this.actorsNumber[actorType.PIGEON.ordinal()]);
					break;

				default:
					TheGblVars.echoDebug(2,
							"This animal : " + animal.getId() + " has an unknown type: " + animal.getType());
					break;
				}// end of switch
				TheGblVars.echoDebug(1, "This animal : " + animal.getId() + " " + animal.getType() + " is removed");
				iterator.remove();
			} // end of if dead
		} // end of for

		// Display the remaining animals

		for (Animal animal : this.ecoAnimalList) {
			msg = msg + animal.display() + " ";
		}
		System.out.println(msg + "still alive");
	}
/**
 * 
 * @return a string that represents the state of te ecosystem in terms of actor populations
 */
	private String EcoSystemState() {
		int i;
		String theResult = "";
		for (i = 0; i < this.actorsNumber.length; i++) {
			theResult += "-" + this.actorsNumber[i];
		}

		return theResult;
	}
/**
 * 
 * @return true when the ecosystem should stop to cycle
 */
	private boolean stopCycle() {
		boolean theResult = false;
		boolean moreCombats = true;
		int i;
		int nbAlivePopulations = 0;
		boolean stableEcoSystem = false;
		String currentEcosystemState = "";
		
		// check the demographic variables
		for (i = 0; i < this.actorsNumber.length; i++) {
			nbAlivePopulations += Math.min(1, this.actorsNumber[i]);
		}
		moreCombats = (nbAlivePopulations > 1); // to fight it must be at least 2 kinds of actors in presence
		
		// check if the ecosystem is in a stable period
		currentEcosystemState = EcoSystemState();
		TheGblVars.echoDebug(0, currentEcosystemState + " - " + lastEcoSystemState);
		if (currentEcosystemState.equals(this.lastEcoSystemState)) {
			// the state is stable
			if (this.cycleCounter >= (this.lastEcoSystemChange + ((this.cycleMax * this.nbCyclesStable) / 100))) {
				// the period from the last change is completed
				stableEcoSystem = true;
				TheGblVars.echoDebug(0,
						cycleCounter + " - " + (this.lastEcoSystemChange + (this.cycleMax % this.nbCyclesStable)));
			}
		} else {
			// the state has changed;
			stableEcoSystem = false;
			this.lastEcoSystemChange = this.cycleCounter;
			this.lastEcoSystemState = currentEcosystemState;
		}

		theResult = ( (moreCombats == false) || (this.cycleCounter >= this.cycleMax)
				|| (stableEcoSystem == true));
	
		TheGblVars.echoDebug(0, "stableEcoSystem: " + stableEcoSystem + " moreCombats : " + moreCombats + " theResult : " + theResult );
		return theResult;
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

	/* IMPROVE all the actors informations should be accessed with an array... or an index in the array... 
	* or better a value of the enum actorType
	*/
	public int getRatsNumber() {
		return this.actorsNumber[actorType.RAT.ordinal()];
	}

	public int getPigeonsNumber() {
		return this.actorsNumber[actorType.PIGEON.ordinal()];
	}

	public int getZombiesNumber() {
		return this.actorsNumber[actorType.ZOMBI.ordinal()];
	}

	public int getCycleCounter() {
		return cycleCounter;
	}

	// must not be used public void setCycleCounter(int cycleCounter) {
	// this.cycleCounter = cycleCounter; }
	public int getCycleMax() {
		return cycleMax;
	}

	public void setCycleMax(int cycleMax) {
		this.cycleMax = cycleMax;
	}

	/**
	 * @return the nbCyclesStable
	 */
	public int getNbCyclesStable() {
		return nbCyclesStable;
	}

	/**
	 * @param nbCyclesStable the nbCyclesStable to set
	 */
	public void setNbCyclesStable(int nbCyclesStable) {
		this.nbCyclesStable = nbCyclesStable;
	}

}
