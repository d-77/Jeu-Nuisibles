package start;

public class Main implements GlobalInterface {

	
	public static void main(String[] args) {
		
		//use a global variables object
		GblVars TheGblVars = GblVars.getInstance();	

		Ecosystem monEcosystem;
		
		
		TheGblVars.echoDebug(4, "starting");
		
		monEcosystem = new Ecosystem(10,10,2,2,2,10);
		TheGblVars.echoDebug(4, monEcosystem.toString());

		monEcosystem.display();
	}

}
