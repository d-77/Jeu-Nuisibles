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
		animalList = new ArrayList<Animal>();
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

	/*
	 * displays a symbol (a character) to represent what is in the arena (Z for a zomby,
	 *  R for a Rat, etc and a number when there 2 or more actors in the arena
	 *  it depends on the number of animals in the arena:
	 *  	0: it's empty so display an empty box
	 *  	1: there is an animal => display its representation
	 *  	2: there are many animals => a fight should occur 
	 *  		=> display a represention of a fight, at least a number (of animals)
	 */
	public String display() {
		String theResult = "error-arrena";
		this.TheGblVars.echoDebug(4, "starting");

		if (this.isEmpty()) {
			theResult = displayEmpty();
		} else {
			if (this.animalList.size() == 1) {
				theResult = this.animalList.get(0).display(); // display the animal representation
			} else { //more than 1 animal in the arena
				theResult = displayFight(this.animalList.size());
			}
		} // end of if this.animalList.isEmpty()

		return theResult;
	}

	public String displayEmpty() {
		return ".";
	}
	
	public String displayFight(Integer nbAnimals) {
		// in case of an animation for example to show a fight
		// or to show a state of the fight locations before results
		
		return nbAnimals.toString();
	}

	/*
	 * 	add an animal into the arena
	 */
	public void addAnimal(Animal dude) {
		this.animalList.add(dude);
	}
	
	/*
	 * Test if the arena is empty
	 */
	public boolean isEmpty() {
		TheGblVars.echoDebug(4,"start " + this.animalList.isEmpty());
		return this.animalList.isEmpty();
	}
	
}
