package start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import start.GlobalInterface.actorType;

public class Arena implements GlobalInterface  {
		
	private ArrayList<Animal> animalList ;
	
	//use a global variables object
	private GblVars TheGblVars = GblVars.getInstance();	
	
	/*
	 * Constructors
	 */
	
	public Arena(){
		this.animalList = new ArrayList<Animal>();
	}
	
	/*
	 * Setters & Getters
	 */
	
	public List<Animal> getAnimalList() {
		return animalList;
	}
	public void setAnimalList(ArrayList<Animal> animalList) {
		this.animalList = animalList;
	}

	/*
	 * Methods
	 */
	
	/**
	 * displays a symbol (a character) to represent what is in the arena (Z for a zomby,
	 *  R for a Rat, etc and a number when there 2 or more actors in the arena
	 *  it depends on the number of animals in the arena:
	 *  	0: it's empty so display an empty box
	 *  	1: there is an animal => display its representation
	 *  	2: there are many animals => a fight should occur 
	 *  		=> display a represention of a fight, at least a number (of animals)
	 * 
	 * @return	a representation of the status of the arena
	 *
	 */
	public String display() {
		
		//ansi escape console: colors!!!
		// use the eclipse plugin ansi in console
		//mihai-nita.net - http://www.mihai-nita.net/eclipse
		//https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
		
		Animal theAnimal;
		
		String theResult = "error-arrena";
		this.TheGblVars.echoDebug(4, "starting");

		if (this.isEmpty()) {
			theResult = displayEmpty();
		} else {
			if (this.animalList.size() == 1) {
				theAnimal = this.animalList.get(0);
				// display the animal representation with its id
				theResult = theAnimal.display(); 
			} else { //more than 1 animal in the arena
				theResult = ANSI_RED + displayFight(this.animalList.size())+ ANSI_RESET;
				TheGblVars.echoDebug(4, "There are more than one animal:" + theResult);
				//throw new RuntimeException("This is thrown for debug purpose");
			}
		} // end of if this.animalList.isEmpty()

		return theResult;
	}

	public String displayEmpty() {
		return " . ";
	}
	
	public String displayFight(Integer nbAnimals) {
		// in case of an animation for example to show a fight
		// or to show a state of the fight locations before results
		
		return "*"+nbAnimals.toString()+"*";
	}

 /**
  * Display the actors in the arena, in the first instance for DEBUG purpose
  * @return the display of each actor in a string
  */
	public String displayArena(){
		String theResult ="";
		for (Animal itr : this.animalList) {
			theResult = theResult + itr.display() + " ";
		}
		return theResult;
	}
	
	/**
	 * 	add an animal into the arena
	 */
	public void addAnimal(Animal dude) {
		this.animalList.add(dude);
	}
	/**
	 * remove an animal from the arena, but it still exists
	 * @param Animal to remove
	 */
	public void removeAnimal(Animal dude) {

		if (! this.animalList.contains(dude)) {
			TheGblVars.echoDebug(0," error: the animal is not in the arena\n The animal : "
					+ dude.displayAttributes());
		} else {
			if (this.animalList.remove(dude)) {
				TheGblVars.echoDebug(4," the animal is removed");
			}else {
				TheGblVars.echoDebug(0," error: the animal is not removed\n The animal : "
						+ dude.displayAttributes());
			}
		}
	}
	
	/**
	 * Test if the arena is empty
	 *
	 * @return
	 * 
	 */
	public boolean isEmpty() {
		TheGblVars.echoDebug(4,"start " + this.animalList.isEmpty());
		return this.animalList.isEmpty();
	}
	/**
	 * 
	 * @param theList
	 * @param type
	 * @return the numbers of animals of the type type in TheList
	 */
	public int countByType(ArrayList<Animal> theList, actorType type) {
		int count = 0;
		for (Animal theAnimal : theList) {
			if(theAnimal.getType() == type ) count++;
		}
		return count;
	}
/**
 * 
 * @return a list of animals to move in adjacent empty rooms
 */
	public ArrayList<Animal> launchFight() {

		fightAction action = fightAction.DEATH; 
		int [] countersByType;
		countersByType = new int[actorType.values().length];
		int i,j; //working indexes
		ArrayList<Animal> resultAnimalList = new ArrayList<Animal>(); //@return
		Animal animal1, animal2; //working variables 

		/* reminder of definition see the declaration for accurate information
		*	NOTENOUGH, 			// 0 or 1 fighter => no fight
		*	DEATH,				// some must die
		*	ZOMBIFICATION,		// all become zombies
		*	ONETEAM				// they are in the same team => no fight and no death
		*/
		while ((action != fightAction.NOTENOUGH) && (action != fightAction.ONETEAM)) {
			// numbers of actors by type
			this.TheGblVars.echoDebug(4, "the fight begins, nb of fighters: " + this.animalList.size());
			this.TheGblVars.echoDebug(4, "this.animalList: " + this.animalList.toString());
			for (actorType type : actorType.values()) {
				countersByType[type.ordinal()] = countByType(this.animalList, type);
			}
			// fight rules depend on the kinds of actors presents in the same place

			// if there is no more than 1 fighter : stop, no fight
			if (this.animalList.size() <= 1) {
				action = fightAction.NOTENOUGH;
			} else {
				// there are at least 2 actors
				this.TheGblVars.echoDebug(3, "the arena contains the animals: " + this.displayArena());
				// if there is only one kind of actors
				j = 0;
				for ( i = 0; i < countersByType.length; i++) {
					j = j + Math.min(1, countersByType[i]); // if the counter is 0 then returns 0, it counts the non null counters
				}

				if (j == 1 ) { 
					action = fightAction.ONETEAM;
				}
				else {
					// there are fighters of different kinds
					if (countersByType[actorType.ZOMBI.ordinal()] > 0 ) {
						// there is at least one zombie
						action = fightAction.ZOMBIFICATION;
					} else {
						action = fightAction.DEATH;
					}
				}
			} // end of if for fightAction

			switch (action) {
			case NOTENOUGH:
				// nothing to do
				break;

			case ZOMBIFICATION:
				// if there is one zombie, all become zombies
				// the first zombie stays in the arena, the others move to an adjacent room

				for (Animal theAnimal : this.animalList) {
					theAnimal.transform(actorType.ZOMBI);
				}
				action = fightAction.ONETEAM; // a team of zombies

				// no break, because after zombification, the case becomes as ONETEAM

			case ONETEAM:
				// move the animals in order to keep only one by rooms
				// copy the last n-1 animals in resultAnimalList
				resultAnimalList.addAll( this.animalList.subList(1, this.animalList.size() ) );
				this.TheGblVars.echoDebug(1, "case ONETEAM resultAnimalList: " + resultAnimalList.toString());
				// remove the n-1 animals of the list
				this.animalList.subList(1, this.animalList.size() ).clear();
				this.TheGblVars.echoDebug(1, "case ONETEAM only the first this.animalList: " + this.animalList.toString());

				break;

			case DEATH:

				//DCL wait for validation		
				
				// perform one fight between the 2 first actors of different kind				
				animal1 = this.animalList.get(0);
				i = 1;
				do  {
					animal2 =  this.animalList.get(i);
					i++;
				} while ( (i < this.animalList.size()) && ( animal1.getType() == animal2.getType() ) );
				
				if (Math.random() > 0.5 ) {
					//animal1 dies
					//TODO remove also of the list in the ecosystem
					TheGblVars.echoDebug(1,"animal1 is dead and must be removed\nfrom this arena: " +
							animal1.getX() + "," + animal1.getY() + " " + animal1.displayAttributes());
					animal1.death();
					removeAnimal(animal1);
				} else {
					//animal2 dies
					TheGblVars.echoDebug(1,"animal2 is dead and must be removed\nfrom this arena: " +
							animal2.getX() + "," + animal2.getY() + " " + animal2.displayAttributes());
					animal2.death();
					removeAnimal(animal2);
				}
				break;

			default:
				break;
			}
		}

		/**************
		
		// if there is one zombi, all become zombies
		// the first zomby stays in the arena, the others move to an adjacent room

		if (countersByType[actorType.ZOMBI.ordinal()] > 0) {
			for (Animal theAnimal : this.animalList) {
				theAnimal.transform(actorType.ZOMBI);
			}
			//copy the last n-1 animals in resultAnimalList
			resultAnimalList.addAll( this.animalList.subList(1, this.animalList.size() ) );
			this.TheGblVars.echoDebug(1, "Zombi resultAnimalList: " + resultAnimalList.toString());
			// remove the n-1 animals of the list
			this.animalList.subList(1, this.animalList.size() ).clear();
			this.TheGblVars.echoDebug(1, "only the first this.animalList: " + this.animalList.toString());
		} else {// zombi free

		}
			// FIXME !!!
	
			
			while ((action != fightAction.NOTENOUGH) && (action != fightAction.ONETEAM)) {
			
			animal1 = this.animalList.get(0);
			animal2 = this.animalList.get(1); //DCL en erreur car ça fonctionne!!!
			if ((animal1.getType() == actorType.ZOMBI) ||  (animal2.getType() == actorType.ZOMBI)){
				// the 2 actors become zombies, even they were already zombies ...
				animal1.transform(actorType.ZOMBI);
				animal2.transform(actorType.ZOMBI);
				// move animal2 in an adjacent empty room
				resultAnimalList.add(animal2);
			    // remove it from this arena
				TheGblVars.echoDebug(0,"A zombi must be moved from this arena: " +
			    animal2.getX() + "," + animal2.getY() + " " + animal2.displayAttributes());
				this.removeAnimal(animal2);
				
			}else { // the 2 actors are not zombies, one must die
				if (Math.random() > 0.5 ) {
					//animal1 dies
					//TODO remove also of the list in the ecosystem
					TheGblVars.echoDebug(0,"animal1 is dead and must be removed\nfrom this arena: " +
						    animal1.getX() + "," + animal1.getY() + " " + animal1.displayAttributes());
					animal1.death();
					removeAnimal(animal1);
				} else {
					//animal2 dies
					TheGblVars.echoDebug(0,"animal2 is dead and must be removed\nfrom this arena: " +
						    animal2.getX() + "," + animal2.getY() + " " + animal2.displayAttributes());
					animal2.death();
					removeAnimal(animal2);
				}
			}
			*/
			// DEBUG display the animal representation with its id
			//theResult = theAnimal.display() + String.format("%02d",theAnimal.getId()); 
					//TheGblVars.echoDebug(4, "There are more than one animal:" + theResult);
			//throw new RuntimeException("This is thrown for debug purpose");
		//} end of while this.animalList.size() > 1
		
		return resultAnimalList;
	} 

}
