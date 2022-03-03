/**
 * 
 */
package code;

/**
 * @author daniel.conil
 *
 */
/**
 * defines the public attributes that can be used as global variables
 * 
 * singleton implementation
 * 
 * to instantiate the object : GblVars TheGblVars = GblVars.getInstance();
 * write this action at the begining of each class that needs to acces to the global variables
 * 
 */
public class GblVars implements GlobalInterface {

	// implements the signleton patern
	// https://fxrobin.developpez.com/tutoriels/java/singleton-patron-conception/

	/**
	 * set manual traces for debug
	 * 0 no debug info
	 * 1 critical info
	 * 2 warnings
	 * 3 verbose
	 * 4 anything
	 * 5 and over: reserved, do not use (in order to do not display info with debug level >= 5 
	 **/
	public int dbglvl = 0;

	/************ singleton implementation ******************/
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
		return String.format("I am the LazySingleton : %s", super.toString());
	}

	/************ end of the singleton implementation ******************/
	
	/*
	 * Methods used for general purpose
	 */
	/**
	 * add the escaped ansi characters to colour the string s
	 * @param colour: must be an ANSI escape code, if empty returns
	 * @param s 
	 * @return the s string with the ANSI escape codes
	 */
	public String colourString(String colour, String s) {
		String TheResult = s;
		if (! colour.isEmpty()) {
			TheResult = colour + s + ANSI_RESET;
		}
		return TheResult;
	}
	
	/**
	 * display debug info with the calling method as prefix
	 *  5 => the debug msg is not displayed
	 *  IMPROVE: add colour parameter, and a different colour by debug level
	 * @param levelDebug
	 * @param msg
	 */
	public void echoDebug(int levelDebug, String msg) {
		
		String theResult;
		String classeAppelante = Thread.currentThread().getStackTrace()[2].getClassName() +
				" -> " + Thread.currentThread().getStackTrace()[2].getMethodName();
		theResult = "dbg: " + classeAppelante + ": " + msg;
		switch (levelDebug) {
		case 0:
			theResult = colourString(ANSI_RED, theResult);
			break;
		case 1:
			theResult = colourString(ANSI_PURPLE, theResult);
			break;
		case 2:
			theResult = colourString(ANSI_YELLOW, theResult);
			break;
		case 3:
			theResult = colourString(ANSI_GREEN, theResult);
			break;
		case 4:
			theResult = colourString(ANSI_BLUE, theResult);
			break;
		default:
			break;
		}
		if (levelDebug <= dbglvl) {
			System.out.println(theResult);
		}
	}

}
