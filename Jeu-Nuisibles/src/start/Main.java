package start;

public class Main implements GlobalInterface {

	
	public static void main(String[] args) {
		
		//use a global variables object
		GblVars TheGblVars = GblVars.getInstance();	

		Ecosystem monEcosystem;
		
		
		TheGblVars.echoDebug(4, "starting");
		
		monEcosystem = new Ecosystem(4,4,8,8,4,3);
		TheGblVars.echoDebug(4, monEcosystem.toString());

		monEcosystem.animate();
	}

}
