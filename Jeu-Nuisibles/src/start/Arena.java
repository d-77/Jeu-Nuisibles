package start;

import java.util.ArrayList;
import java.util.List;

public class Arena implements GlobalInterface  {
	
	
	private List<Animal> animalList ;
	
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
	public void setAnimalList(List<Animal> animalList) {
		this.animalList = animalList;
	}

	/*
	 * Methods
	 */
	
	public void launchCombat(ArrayList<Animal> fighters) {
		//Cas Liste vide + Cas 1 + Cas plusieurs
		// TODO be implemented
	}

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
	 * @param dude
	 */
	public void removeAnimal(Animal dude) {
		
		if (this.animalList.remove(dude)) {
			TheGblVars.echoDebug(4," the animal is removed");
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
	
}
