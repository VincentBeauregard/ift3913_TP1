import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class CVS_metrique {
	
	/* La fonction createFile prend un Objet de type fichier en parametre
	 * cree un fichier de type cvs contenant une matrice 2D dont les ligne 
	 * correspondent aux classe de l'bjoet Fichier et les colonnes correspondent
	 * aux different metrique de chaque classes.
	 * */
	public static void createFile(Fichier fichier) throws IOException{
		if (fichier!=null){
		//ouverture d'un navigateur de fichier JFileChooser
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(new File(fichier.modele+".csv"));
			fileChooser.setFileFilter(new FileNameExtensionFilter("csv file","csv"));
			int result = fileChooser.showSaveDialog(new JPanel());
			File selectedFile = null;
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				
			}
			if (selectedFile.createNewFile()){
		        FileWriter writer = new FileWriter(selectedFile);
		        //ecrit dans le fichier
		        writer.write(calculAll(fichier));
		        writer.close();
			}
			else{
				JOptionPane.showMessageDialog(null, "Ce Fichier existe deja.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Veuiller d'abord ouvrir un fichier.");}
	}
	
	/* La calculAll prend en parametre un objet de type Fichier
	 * retourne en objet la liste des classe de l'objet Fichier
	 * avec leur metrique dans un string.
	 * 
	 * */
	public static String calculAll(Fichier fichier){
		//initialisation de la 1ere ligne
		String rString = fichier.modele+",ANA,NOM,NOA,ITC,ETC,CAC,DIT,CLD,NOC,NOD\n";
		//ajout des metrique correspondant a la classe i
		for(int i=0; i < fichier.classes.length ; i++){
			Classe c = fichier.classes[i];
			rString+=c.nom;
			rString+=","+Metrique.calculMetrique(c,fichier);
			rString+="\n";
		}
		
		return rString;
	}

}
