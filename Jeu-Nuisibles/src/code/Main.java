package code;

public class Main implements GlobalInterface {

	
	public static void main(String[] args) {
		
		//use a global variables object
		GblVars TheGblVars = GblVars.getInstance();	
		
		DclConsole theConsole = new DclConsole();
		
		//DCL implement the builder for the 3 kinds of ecosystem
	/*	   
	   int tmp = theConsole.collectInputInt("1 test of input of an integer: ");
	   System.out.println("the input is : " + tmp );
	
	   tmp = theConsole.collectInputInt("2 test of input of an integer: ");
	   System.out.println("the input is : " + tmp );

	   
	   String tmpp = theConsole.collectInputString("");
	   System.out.println("the input is : " + tmpp );
	   
	   tmp = theConsole.collectInputInt("1 test of input of an integer: ");
	   System.out.println("the input is : " + tmp );

	    tmpp = theConsole.collectInputString("entre la bonne phrase");
	   System.out.println("the input is : " + tmpp );

	    tmpp = theConsole.collectInputString("entre la 2nde bonne phrase");
	   System.out.println("the input is : " + tmpp );
	   
	   System.out.println("\n               The End\n");
	 */
		Ecosystem monEcosystem;
		
		
		TheGblVars.echoDebug(4, "starting");
		
		monEcosystem = new Ecosystem(24,20,15,15,
				0,300000);
		
		TheGblVars.echoDebug(4, monEcosystem.toString());

		monEcosystem.animate();

	}
	
}
