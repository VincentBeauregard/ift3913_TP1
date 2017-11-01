
public class Classe {
	public Attribut attributs[];
	public Methode methodes[];
	public String sousClasses[];
	public int indexLiens[];
	public String nom;
	
	//Constructeur
	public Classe(String nom, Attribut[] attributs,Methode[]methodes,String[] sousClasses){
		this.attributs = attributs;
		this.methodes = methodes;
		this.sousClasses = sousClasses;
		this.nom = nom;
		this.indexLiens = new int[0];
	}
	
	//fonction pour ajouter des lien un a un correspondant a la position dans la liste de lien du fichier
	public void addliens(int index) {
		int length = indexLiens.length;
		int tmp[] = new int[length + 1];
		for (int i = 0; i < length; i++) {
			tmp[i] = indexLiens[i];
		}
		tmp[length] = index;
		indexLiens = tmp;
	}
	
	//fonction pour ajouter des sousclasse une a une
	public void addsousclasse(String toAdd) {
		String[] temp = new String[sousClasses.length + 1];
		for (int i = 0; i < sousClasses.length; i++) {
			temp[i] = sousClasses[i];
		}
		temp[sousClasses.length] = toAdd;
		sousClasses = temp;
	}
}
