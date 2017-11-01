
public class TP1 {
	//private static Fichier test;
	private static Gui gui;

	public static void main(String[] args){

		Gui.runGui();
	}
	
	public static Fichier loadFile(String path){
		System.out.println("path : "+path);
		return Parse2.load(path);
		
	}
}
