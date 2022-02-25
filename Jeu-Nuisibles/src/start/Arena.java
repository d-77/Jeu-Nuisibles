package start;

import java.util.ArrayList;
import java.util.List;

public class Arena implements GlobalInterface {
	
	
	private List<Animal> animalList ;
	
	public Arena(){
		animalList = new ArrayList<Animal>();
	}
	
	public List<Animal> getAnimalList() {
		return animalList;
	}

	public void setAnimalList(List<Animal> animalList) {
		this.animalList = animalList;
	}

	public void launchCombat(ArrayList<Animal> fighters) {
		//Cas Liste vide + Cas 1 + Cas plusieurs
		
	}
	public String displayArena() {
		return "0"; //Affichage de la map sur l'arene en question si un zombie = return Z
	}

}
