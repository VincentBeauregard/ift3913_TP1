import java.io.File;
import java.io.IOException;


public class TP1 {
	//private static Fichier test;
	private static Gui gui;

	public static void main(String[] args){
	//creation d'un fichier test pour le debug
		/*
		String tab[] = new String[3];
		tab[0]="a";
		tab[1]="b";
		tab[2]="c";
		String tab2[] = new String[3];
		tab2[0]="d";
		tab2[1]="e";
		tab2[2]="f";
		String tab3[] = new String[3];
		tab3[0]="g";
		tab3[1]="h";
		tab3[2]="i";
		String tab4[] = new String[2];
		tab4[0]="11";
		tab4[1]="01";
		Classe c1 = new Classe("c1",tab, tab, tab);
		Classe c2 = new Classe("c2",tab2, tab2, tab2);
		Classe cx[] = new Classe[2];
		cx[0]=c1;
		cx[1]=c2;
		String[] cname= {"c1","c2"};
		Lien l1 = new Lien(cname,tab4,cname,tab4,"lien1");
		c1.addliens(0);
		c2.addliens(0);
		Lien lx[] = new Lien[1];
		lx[0]=l1;

		test = new Fichier("test",cx,lx,true);
		*/
		Gui.runGui();
	}
	
	// effectue la communication entre parse et Gui
	//envoie le path de gui a parse et parse retourne un objet Fichier
	public static Fichier loadFile(String path){
		System.out.println("path : "+path);
		return Parse2.load(path);
	}
	public static void newCVS(Fichier fichier) throws IOException{
		CVS_metrique.createFile(fichier);
	}
}
