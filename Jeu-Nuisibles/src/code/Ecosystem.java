package code;

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
	
	//use a global object
	private GblVars TheGblVars = GblVars.getInstance();
	
	/*
	 * Class variables
	 */
	
	static int [] actorsNumber = new int[nbTypes];
	
	/*
	 * attributes
	 */
	
	private int length;
	private int height;
	private int cycleCounter;
	private int cycleMax;
	public static long cycleDuration = 0; // duration of one cycle in ms
	private Map mapArena;
	private ArrayList<Animal> ecoAnimalList; 
	
	/* 
	 * Constructors
	 * 
	 * */
	
	public Ecosystem(int length, int height, int ratsNumber, int pigeonsNumber, int zombiesNumber,
			int cycleMax) {
		this.length = length;
		this.height = height;
		
		// IMPROVE access in a static way
		// https://www.google.fr/search?q=java+the+static+field+should+be+accessed+in+a+static+way&ei=gPUfYuWJCrPO7_UPreS7kAM&ved=0ahUKEwjlyau-wqj2AhUz57sIHS3yDjIQ4dUDCA4&uact=5&oq=java+the+static+field+should+be+accessed+in+a+static+way&gs_lcp=Cgdnd3Mtd2l6EAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsAMyBwgAEEcQsANKBAhBGABKBAhGGABQsAZY340CYOWTAmgBcAF4AIABAIgBAJIBAJgBAKABAcgBCMABAQ&sclient=gws-wiz
		
		// IMPROVE the counters of actors should be updated in the generateActors method
		// instead of be set in the constructor
		this.actorsNumber[actorType.RAT.ordinal()]    = ratsNumber;
		this.actorsNumber[actorType.PIGEON.ordinal()] = pigeonsNumber;
		this.actorsNumber[actorType.ZOMBI.ordinal()]  = zombiesNumber;
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
		boolean moreCombats = true;
		int nbAlivePopulations = 3;
		ArrayList<Animal> moveList;
		int i;

		this.cycleCounter = 1;
		while (moreCombats == true && (this.cycleCounter <= this.cycleMax)) {
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
			
			moveList = this.mapArena.launchFight(); 
			
			TheGblVars.echoDebug(2, "size of movelList: " + moveList.size());
			
			//move the animals because they are too many in the same place
			
			for (i = 0; i < moveList.size(); i++) {
				TheGblVars.echoDebug(1," the " + i + " animal of moveList: "
						+ moveList.get(i).displayAttributes());
				mapArena.relocateAnimal(moveList.get(i));  //IMPROVE in case of zombification there is a tentative to remove an object which isn't part of the list
			}

			remainingAnimals(); //update the AnimalList after the fights
			System.out.println(); // one line between 2 screens
			
			//IMPROVE with Mat.min(1, population number
			
			if (this.actorsNumber[actorType.ZOMBI.ordinal()] > 0) {nbAlivePopulations = 1;} else {nbAlivePopulations = 0;}
			if (this.actorsNumber[actorType.RAT.ordinal()] > 0) {nbAlivePopulations++;} 
			if (this.actorsNumber[actorType.PIGEON.ordinal()] > 0) {nbAlivePopulations++;}
			moreCombats = (nbAlivePopulations > 1); //if there is only one kind of actors: they can't fight themselves
			
			this.cycleCounter++;
			// wait cycleDuration ms
			try  { Thread.sleep(cycleDuration); } catch (Exception e)  { } 
		} // end of while
		this.display(); // displays the last state of the ecosystem
	}
	
	/* 
	 * Displays the ecosystem
	 * 
	 */
	public void display()
	{
		System.out.println("Cycle " + cycleCounter + ", nb-z: " + 
				this.actorsNumber[actorType.ZOMBI.ordinal()] +
				", nb-r: " + this.actorsNumber[actorType.RAT.ordinal()] +
				", nb-p: " + this.actorsNumber[actorType.PIGEON.ordinal()] );
		mapArena.display();
	}
	
	/**
	 *  Factory of the animals....
	 */
	void animalsGeneration() {
		// executed in the constructor of Ecosystem
		// initialization of the map
						
		// generation of ratsNumber rats,  pigeonsNumber, zombiesNumber
		this.generateActors(actorType.RAT, this.actorsNumber[actorType.RAT.ordinal()]);
		this.generateActors(actorType.PIGEON, this.actorsNumber[actorType.PIGEON.ordinal()]);
		this.generateActors(actorType.ZOMBI, this.actorsNumber[actorType.ZOMBI.ordinal()]);
	}

	/**
	 *  Generates n actors of one type and set them in available rooms into the map
	 * @param type
	 * @param nb
	 */
	private void generateActors(actorType type, int nb) {

		// IMPROVE the counters of actors should be updated in this method
		// instead of be set in the constructor
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
					this.actorsNumber[actorType.ZOMBI.ordinal()]--;
					TheGblVars.echoDebug(2, "zombiesNumber : " + 
					this.actorsNumber[actorType.ZOMBI.ordinal()] );
					break;
				case RAT:
					this.actorsNumber[actorType.RAT.ordinal()]--;
					TheGblVars.echoDebug(2, "ratsNumber : " + 
					this.actorsNumber[actorType.RAT.ordinal()] );
					break;
				case PIGEON:
					this.actorsNumber[actorType.PIGEON.ordinal()]--;
					TheGblVars.echoDebug(2, "pigeonsNumber : " + 
					this.actorsNumber[actorType.PIGEON.ordinal()] );
					break;

				default:
					TheGblVars.echoDebug(2, "This animal : " + animal.getId() + 
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
		System.out.println(msg + "still alive");
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
		return this.actorsNumber[actorType.RAT.ordinal()];
	}
	public int getPigeonsNumber() {
		return this.actorsNumber[actorType.PIGEON.ordinal()];
	}
	public int getZombiesNumber() {
		return this.actorsNumber[actorType.ZOMBI.ordinal()];
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
