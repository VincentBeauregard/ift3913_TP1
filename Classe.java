
public class Classe {
	public String attributs[];
	public String methodes[];
	public String sousClasses[];
	public int indexLiens[];
	public String nom;
	
	public Classe(String nom, String[] attributs,String[]methodes,String[] sousClasses){
		this.attributs = attributs;
		this.methodes = methodes;
		this.sousClasses = sousClasses;
		this.nom = nom;
		this.indexLiens = new int[0];
	}
	public void addliens(int index) {
		int length = indexLiens.length;
		int tmp[] = new int[length + 1];
		for (int i = 0; i < length; i++) {
			tmp[i] = indexLiens[i];
		}
		tmp[length] = index;
		indexLiens = tmp;
	}
	public void addsousclasse(String toAdd) {
		String[] temp = new String[sousClasses.length + 1];
		for (int i = 0; i < sousClasses.length; i++) {
			temp[i] = sousClasses[i];
		}
		temp[sousClasses.length] = toAdd;
		sousClasses = temp;
	}
}
