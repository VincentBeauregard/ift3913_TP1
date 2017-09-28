
public class Model {


        public Classe classes[];
        public Lien liens[];
        public boolean valide;
        public String modele;
        public Model(String modele,Classe classes[],Lien liens[], boolean valide){
        this.valide=valide;
        this.classes = classes;
        this.liens = liens;
        this.modele = modele;
        }
    public Model(String modele){
        this.modele = modele;
    }

}
