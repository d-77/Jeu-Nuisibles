package start;

/*
 * 
 * http://patatos.over-blog.com/article-antipattern-java-interface-de-constantes-114377630.html
 * 
 * pour remplacer l'interface
 * 
 * En fait il existe un moyen plus sûr de faire la même chose. Au lieu d'utiliser une interface il faut mieux utiliser une classe non instanciable, puis utiliser un import static dans la classe qui utilise ces constantes: 

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

On garde la même lisibilité qu'avec l'interface et les constantes ne sont plus héritées ce qui évite le risque de confusion.

 *
 */


public interface GlobalInterface {

	/**
	 *  defines the types of actors
	 */
	enum actorType {
		ZOMBI,
		RAT,
		PIGEON
	}
	
	/**
	 * duration of one cycle in ms
	 */
	public static final long cycleDuration = 1000;
	
	//TODO définir les constantes des vitesses de chaque acteur
	
}
