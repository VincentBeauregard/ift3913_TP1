import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Parse {
	public static Fichier load(String pathString) {
		Path path=FileSystems.getDefault().getPath(pathString);
		List<String> lines = null;   
		String[] tokens = null;
		try {
			//this returns null
			lines = Files.readAllLines(path, Charset.defaultCharset());
			System.out.println(lines);
			tokens = lines.toArray(new String[0]);


			for (int i = 0; i < tokens.length; i++) {
				System.out.println(tokens[i]);
			}
		/*
		if(order(tokens)==1)
			return new Fichier("Fichier invalide",null, null,false);
		else 
			return new Fichier("test",null, null,true);
		}catch(IOException ex){
			System.out.println(ex.getMessage());
			return new Fichier("Impossible d'ouvrir le fichier",null, null,false);
		}
		*/
			return getInfo(tokens);
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
			return new Fichier("Impossible d'ouvrir le fichier",null, null,false);
		}
	}
	//cette fonction recoit la table des string et renvoit le tout en Fichier. S'il y a une erreur dans le fichier, il revoit un fichier non valide
	public static Fichier getInfo(String[] tokens)
	{
		String[] part=tokens[0].split(" +");
		List<Classe> classe= new ArrayList<Classe>();
		Classe[] classarray;
		String nom_model="";
		Lien[] lien =new Lien[0];
		if (part[0].trim().equals("MODEL")&& part.length==2)
		{
			nom_model = part[1];
			//System.out.println("The model name is "+nom_model);
		}
		else{
			System.out.println("error");

		}
		int i =1;
		while (i<tokens.length)
		{

			part=tokens[i].split(" +");
			if (part[0].trim().equals("CLASS")&& part.length==2)
			{
				//System.out.println("The class name is "+part[1]);
				String Classnom=part[1].trim();
				i++;
				part=tokens[i].split(" +");
				while(part.length==0)
				{
					i++;
					part=tokens[i].split(" +");
				}
				if((!part[0].trim().equals("ATTRIBUTES")))
				{
					return null;
				}
				else
				{
					i++;
					part=tokens[i].split(":");
				}
				List<String> att=new ArrayList<String>();
				while(!part[0].trim().equals("OPERATIONS"))
				{
				    /*
					System.out.println(part.length);
					System.out.println(part[0]);
					System.out.println(!part[0].equals("OPERATIONS"));
					System.out.println(part[0]+" "+part[1]);
*/
					att.add(part[0]+" "+part[1]);
					i++;
					part=tokens[i].split(":");
				}
				String[] attribute=att.toArray(new String[0]);
				i++;
				part[0]=tokens[i];
				List<String> ope=new ArrayList<String>();;
				while(!part[0].equals(";")) {
					ope.add(part[0]);
					//System.out.println(part[0]);
					i++;
					part[0] = tokens[i];
				}
				String[] operation=ope.toArray(new String[0]);
				String[] liens=new String[0];
				classe.add(new Classe(Classnom,attribute,operation,liens));
			}
			else if(part[0].trim().equals("GENERALIZATION")&& part.length==2)
			{
			    String class_concerne=part[1].trim();
                i++;
                part[0]=tokens[i].trim();
                for (int j=0;j<classe.size();j++ )
                {
                    if (classe.get(j).nom.equals(class_concerne))
                    {
                        classe.get(j).addsousclasse(part[0]);
                        j=classe.size();
                    }
                }
			}
            else if(part[0].trim().equals("RELATION")&& part.length==2)
            {
                String nom_lien=part[1].trim();
                String[] class_concerne=new String[2];
                String[] cardinalite=new String[2];
                i++;//TODO check role yo
                i++;
                part=tokens[i].trim().split(" +");
                class_concerne[0]=part[1];
                cardinalite[0]=part[2];
                System.out.println(part[1].trim());
                for (int j=0;j<classe.size();j++ )
                {
                    if (classe.get(j).nom.equals(part[1].trim()))
                    {
                        classe.get(j).addliens(lien.length);
                        //System.out.println("link added1");
						//System.out.println(classe.get(j).indexLiens[0]);
						j=classe.size();
                    }
                }
                i++;
                part=tokens[i].trim().split(" +");
                class_concerne[1]=part[1];
                cardinalite[1]=part[2];
                for (int j=0;j<classe.size();j++ )
                {
                    if (classe.get(j).nom.equals(part[1].trim()))
                    {
                        classe.get(j).addliens(lien.length);
                        //System.out.println("link added1");
						//System.out.println(classe.get(j).indexLiens[0]);
						j=classe.size();
                    }
                }
                int length = lien.length;
                Lien tmp[] = new Lien[length + 1];
                for (int k = 0; k < length; k++) {
					tmp[k] = lien[k];
				}
                tmp[length] = new Lien(class_concerne,cardinalite,nom_lien);
                lien = tmp;
            }
			else if(part[0].trim().equals("AGGREGATION"))
			{System.out.println("agregation worked");
			String nom_lien="Aggregation";
			String[] parts_cardinalite=new String[1];
			String[] parts=new String[1];
			String[] container_cardinalite=new String[1];
			String[] container=new String[1];
			i++;
			i++;
			part=tokens[i].trim().split(" +");
			container[0]=part[1];
			container_cardinalite[0]=part[2];
			for (int j=0;j<classe.size();j++ )
			{
				if (classe.get(j).nom.equals(part[1].trim()))
				{
					classe.get(j).addliens(lien.length);
					//System.out.println("link added1");
					//System.out.println(classe.get(j).indexLiens[0]);
					j=classe.size();
				}
			}
			i++;
			i++;
			part=tokens[i].trim().split(" +");
			parts[0]=part[1];
			parts_cardinalite[0]=part[2];
			for (int j=0;j<classe.size();j++ )
			{
				if (classe.get(j).nom.equals(part[1].trim()))
				{
					classe.get(j).addliens(lien.length);
					//System.out.println("link added1");
					//System.out.println(classe.get(j).indexLiens[0]);
					j=classe.size();
				}
			}
			int length = lien.length;
			Lien tmp[] = new Lien[length + 1];
			for (int k = 0; k < length; k++) {
				tmp[k] = lien[k];
			}
			tmp[length] = new Lien(container,container_cardinalite, parts,parts_cardinalite,nom_lien);
			lien = tmp;
			}
			i++;
		}
		//for (int x)
		classarray= classe.toArray(new Classe[0]);

		return new Fichier(nom_model,classarray,lien,true);
	}
}
