package start;

/*
 * 
 * http://patatos.over-blog.com/article-antipattern-java-interface-de-constantes-114377630.html
 * 
 * pour remplacer l'interface
 * 
 * En fait il existe un moyen plus sûr de faire la même chose. 
 * Au lieu d'utiliser une interface il faut mieux utiliser une classe non instanciable, 
 * puis utiliser un import static dans la classe qui utilise ces constantes: 

public final class Constantes {

   private Constantes(){
      throw new UnsupportedOperationException();
   }

   public static final String MY_NAME = "my_name";
   // etc ...

} 

Ensuite dans la classe qui utilise les constantes:

import static mon.package.Constantes.*;

public class MaClass {

   private String name = MY_NAME;
   // etc ...

} 

On garde la même lisibilité qu'avec l'interface et les constantes ne sont plus héritées
 ce qui évite le risque de confusion.

 */


public interface GlobalInterface {

	/**
	 *  defines the types of actors
	 *  The order of values is important, it used for the priorities in combat
	 */
	enum actorType {
		ZOMBI,
		RAT,
		PIGEON
	}
	
	/**
	 *  qualify the way of fighting: no fight, fight to death, transformation...
	 */
	enum fightAction {
		NOTENOUGH, 			// 0 or 1 fighter => no fight
		DEATH,				// some must die
		ZOMBIFICATION,		// all become zombies
		ONETEAM				// they are in the same team => no fight and no death
	}
	
	/**
	 * duration of one cycle in ms
	 */
	public static final long cycleDuration = 1;
	
	//https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	
	/**
	 * ANSI escape codes to use color
	 * 
	 * https://en.wikipedia.org/wiki/ANSI_escape_code
	 * 
	 */
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_ORANGE = "\u001b[38;5;220m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_ORANGE_BACKGROUND = "\u001b[48;5;220m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	// IMPROVE définir les constantes des vitesses de chaque acteur
	
}
