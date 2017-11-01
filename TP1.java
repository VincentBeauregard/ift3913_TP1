
public class TP1 {
	//private static Fichier test;
	private static Gui gui;

	public static void main(String[] args){

		Gui.runGui();
	}
	
	// effectue la communication entre parse et Gui
	//envoie le path de gui a parse et parse retourne un objet Fichier
	public static Fichier loadFile(String path){
		System.out.println("path : "+path);
		return Parse2.load(path);
		
	}
}
