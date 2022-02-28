package start;

public class Main implements GlobalInterface {

	
	public static void main(String[] args) {
		
		//use a global variables object
		GblVars TheGblVars = GblVars.getInstance();	

		Ecosystem monEcosystem;
		
		
		TheGblVars.echoDebug(4, "starting");
		
		monEcosystem = new Ecosystem(10,20,2,3,5,3);
		TheGblVars.echoDebug(4, monEcosystem.toString());

		monEcosystem.display();
		monEcosystem.animate();
	}

}
