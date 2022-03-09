

public class Main implements GlobalInterface {

	
	public static void main(String[] args) {
		
		//use a global variables object
		GblVars TheGblVars = GblVars.getInstance();	
		
		DclConsole theConsole = new DclConsole();
		
		//DCL implement the builder for the 3 kinds of ecosystem


		Ecosystem monEcosystem;
			
		TheGblVars.echoDebug(4, "starting");
		
		monEcosystem = new Ecosystem(24,20,1,1,
				0,3000);
		
		TheGblVars.echoDebug(4, monEcosystem.toString());

		monEcosystem.animate();

	}
	
}
