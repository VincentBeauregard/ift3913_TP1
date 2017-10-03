
public class Fichier {
    public Classe classes[];
    public Lien liens[];
    public boolean valide;
    public String modele;
    public Fichier(String modele,Classe classes[],Lien liens[], boolean valide){
        this.valide=valide;
        this.classes = classes;
        this.liens = liens;
        this.modele = modele;
    }
}