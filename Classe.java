
public class Classe {
	public String attributs[];
	public String methodes[];
	public String sousClasses[];
	public int lien[];
	public String nom;
	
	public Classe(String nom, String[] attributs,String[]methodes,String[] sousClasses){
		this.attributs = attributs;
		this.methodes = methodes;
		this.sousClasses = sousClasses;
		this.nom = nom;
		this.lien = new int[0];
	}
	public void addLien(int index){
		int length = lien.length;
		int tmp[] = new int[length + 1];
		for(int i=0;i<length;i++)
			tmp[i]=lien[i];
		tmp[length] = index;
		lien=tmp;		
	}
}
