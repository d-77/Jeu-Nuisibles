package start;

public class Main implements GlobalInterface {

	
	public static void main(String[] args) {
		
		//use a global variables object
		GblVars TheGblVars = GblVars.getInstance();	

		Ecosystem monEcosystem;
		
		
		TheGblVars.echoDebug(4, "starting");
		
		monEcosystem = new Ecosystem(10,10,4,4,4,300);
		TheGblVars.echoDebug(4, monEcosystem.toString());

		monEcosystem.animate();
	}

}
