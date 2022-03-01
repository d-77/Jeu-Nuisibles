package start;

import java.util.ArrayList;
import java.util.List;

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
				theResult = theAnimal.display() + String.format("%02d",theAnimal.getId()); 
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
	 * 	add an animal into the arena
	 */
	public void addAnimal(Animal dude) {
		this.animalList.add(dude);
	}
	/**
	 * remove an animal from the arena
	 * @param Animal to remove
	 */
	public void removeAnimal(Animal dude) {
		
		if (! this.animalList.contains(dude)) {
			TheGblVars.echoDebug(0," error: the animal is not in the arena\n The animal : "
					+ dude.displayAttributes());
		}
		if (this.animalList.remove(dude)) {
			TheGblVars.echoDebug(2," the animal is removed");
		}else {
			TheGblVars.echoDebug(0," error: the animal is not removed\n The animal : "
					+ dude.displayAttributes());
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
 * @return a list of animals to move in adjacent empty rooms
 */
	public ArrayList<Animal> launchFight() {
		//DCL to be continued

		Animal animal1, animal2; //working variables
		ArrayList<Animal> resultAnimalList = new ArrayList<Animal>(); //@return
		
		this.TheGblVars.echoDebug(4, "starting");

		// to fight must be more than 1!!!!
		while (this.animalList.size() > 1) {
			animal1 = this.animalList.get(0);
			animal2 = this.animalList.get(1);
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
			
			// display the animal representation with its id
			//theResult = theAnimal.display() + String.format("%02d",theAnimal.getId()); 
					//TheGblVars.echoDebug(4, "There are more than one animal:" + theResult);
			//throw new RuntimeException("This is thrown for debug purpose");
		}// end of while this.animalList.size() > 1
		
		return resultAnimalList;
	} 

}
