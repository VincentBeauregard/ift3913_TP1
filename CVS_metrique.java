import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class CVS_metrique {
	public static void createFile(Fichier fichier) throws IOException{
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File(fichier.modele+".csv"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("csv file","csv"));
		int result = fileChooser.showSaveDialog(new JPanel());
		File selectedFile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			
		}
			if (selectedFile.createNewFile()){
		        System.out.println("File is created!");
		        FileWriter writer = new FileWriter(selectedFile);
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
	
	public static String calculAll(Fichier fichier){
		String rString = fichier.modele+",ANA,NOM,NOA,ITC,ETC,CAC,DIT,CLD,NOC,NOD\n";
		for(int i=0; i < fichier.classes.length ; i++){
			Classe c = fichier.classes[i];
			rString+=c.nom;
			rString+=","+Metrique.calculMetrique(c);
			rString+="\n";
		}
		
		return rString;
	}

}
