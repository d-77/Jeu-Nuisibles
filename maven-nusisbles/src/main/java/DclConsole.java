

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DclConsole implements GlobalInterface {

	// use a global object
	private GblVars TheGblVars = GblVars.getInstance();
	
	// for each interactive inputs there is a limit of attempts
	private static final int maxAttempts = 10;	
	private Scanner consoleInput;	
	
	/************ singleton implementation for  our DclConsole ******************/
	
	//private static final DclConsole instance = new DclConsole();
	public DclConsole()
	//private DclConsole()
	{
		
		//TheGblVars.echoDebug(1, "Construction du Singleton DclConsole au premier appel");
	}

//	public static final DclConsole getInstance() 
//	{
//		return instance;
//	}
	
	
	/**
	 * 
	 * @param askingInput : Message to request a value, if empty  default is "Please enter a number: "
	 * @return : an integer, in case of error : null
	 */
	public Integer collectInputInt(String askingInput) {

		int itr = 1;
		String msg = askingInput;
		Integer theResult = null;

		consoleInput = new Scanner(System.in);
		if (msg == "") {
			msg = "Please enter a number: ";
		}

		while (itr++ <= maxAttempts) {
			try {
				System.out.println(msg);
				theResult = consoleInput.nextInt();
				itr = maxAttempts + 1; // exits the loop

			} catch (InputMismatchException err) {
				theResult = null;
				TheGblVars.echoDebug(4, "InputMismatchException: " + err.toString());
				consoleInput.nextLine(); // flush the input
				System.out.println("the value is not valid, please retry.");
			}
		}
		return theResult;
	}
	    
	/**
	 * 
	 * @param askingInput : Message to request a value, if empty  default is "Please type an answer: "
	 * @return : an integer, in case of error : null
	 */
	public String collectInputString(String askingInput) {

		int itr = 1;
		String msg = askingInput;
		String theResult = null;
		
		consoleInput = new Scanner(System.in);
		//https://stackoverflow.com/questions/10604125/how-can-i-clear-the-scanner-buffer-in-java
		if (msg == "") {
			msg = "Please type an answer: ";
		}

		while (itr++ <= maxAttempts) {
			try {
				System.out.println(msg);
				theResult = consoleInput.nextLine();
				itr = maxAttempts + 1; // exits the loop

			} catch (NoSuchElementException err) {
				theResult = null;
				TheGblVars.echoDebug(2, "InputMismatchException: " + err.getMessage());
				System.out.println("the value is not valid, please retry.");
			}
		}
		return theResult;
	}	    

}
