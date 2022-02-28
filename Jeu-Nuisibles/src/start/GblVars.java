/**
 * 
 */
package start;

/**
 * @author daniel.conil
 *
 */
public class GblVars implements GlobalInterface {

	// implements the signleton patern
	// https://fxrobin.developpez.com/tutoriels/java/singleton-patron-conception/
	
	/* 
	 * defines the public attributes that can be used as global variables
	 */
		
	// set manual traces for debug
	
	public int dbglvl = 3;
	/*
	 * 0 no debug info
	 * 1 critical info
	 * 2 warnings
	 * 3 verbose
	 * 4 anything
	 */
	
	/*
	 * singleton implementation
	 * 
	 * to instantiate the object : GblVars TheGblVars = GblVars.getInstance();
	 * write this action at the begining of each class that needs to acces to the global variables
	 * 
	 */
	

	private static final GblVars instance = new GblVars();

	private GblVars()
	{
		
		this.echoDebug(4, "Construction du Singleton GblVars au premier appel");
	}

	public static final GblVars getInstance() 
	{
		return instance;
	}

	@Override
	public String toString()
	{
		return String.format("Je suis le LazySingleton : %s", super.toString());
	}

	
	
	/*
	 * Methods
	 */
	
	// display debug info with the calling method as prefix
	// 5 => the debug msg is not displayed
	public void echoDebug(int levelDebug, String msg) {
		//System.out.println("debugg " + msg);
		String classeAppelante = Thread.currentThread().getStackTrace()[2].getClassName() +
				" -> " + Thread.currentThread().getStackTrace()[2].getMethodName();
		if (levelDebug <= dbglvl) {
			System.out.println(classeAppelante + ": " + msg);
		}
	}

}
