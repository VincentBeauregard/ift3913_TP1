import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Parse2 {
    public static Fichier load(String pathString) {
        List<String> lines = null;
        String[] linelist=null;
        String[] tokens = new String[0];
        String[] tempTokens=null;
        Path path=FileSystems.getDefault().getPath(pathString);
        try {
            //this returns null
            lines = Files.readAllLines(path, Charset.defaultCharset());
            //System.out.println(lines);
            linelist = lines.toArray(new String[0]);


            for (int i = 0; i < linelist.length; i++) {
                tempTokens=linelist[i].trim().split(" +");
                int length = tokens.length;
                String tmp[] = new String[length + tempTokens.length];
                for (int j = 0; j < length; j++) {
                    tmp[j] = tokens[j];
                }
                tokens=tmp;
                for (int j = length; j < length+tempTokens.length; j++) {
                    tokens[j] = tempTokens[j-length];
                }
            }

            for(int i=0;i<tokens.length;i++)
            {
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
            return divide(tokens);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return new Fichier("Impossible d'ouvrir le fichier",null, null,false);
        }
    }
    public static Fichier divide(String[] tokens)
    {
        List<Classe> classe= new ArrayList<Classe>();
        Classe[] classarray;
        String nom_model="";
        Lien[] lien =new Lien[0];
        if (!tokens[0].equals("MODEL"))
        {
            return new Fichier(null,null,null,false);
        }
        else
        {
            nom_model=tokens[1];
        }
        for(int i=2;i<tokens.length;i++)
        {
            if(tokens[i].equals("CLASS"))
            {
                int start=i;
                while(!tokens[i].equals(";"))
                {
                    i++;
                }
                String[] classtokens=new String[i-start+1];
                for (int j=i;j>=start;j--)
                {
                    classtokens[j-start]=tokens[j];
                }
                treatClass(classtokens,classe);
            }
            else if(tokens[i].equals("GENERALIZATION"))
            {
                int start=i;
                while(!tokens[i].equals(";"))
                {
                    i++;
                }
                String[] classtokens=new String[i-start+1];
                for (int j=i;j>=start;j--)
                {
                    classtokens[j-start]=tokens[j];
                }
                treatGen(classtokens,classe);
            }
            else if(tokens[i].equals("RELATION"))
            {
                int start=i;
                while(!tokens[i].equals(";"))
                {
                    i++;
                }
                String[] classtokens=new String[i-start+1];
                for (int j=i;j>=start;j--)
                {
                    classtokens[j-start]=tokens[j];
                }
                lien = treatRel(classtokens,lien,classe);
                //System.out.println("lien.length parse2if1 : "+lien.length);
            }
            else if(tokens[i].equals("AGGREGATION"))
            {
                int start=i;
                while(!tokens[i].equals(";"))
                {
                    i++;
                }
                String[] classtokens=new String[i-start+1];
                for (int j=i;j>=start;j--)
                {
                    classtokens[j-start]=tokens[j];
                }
                lien = treatAgg(classtokens,lien,classe);
                //System.out.println("lien.length parse2if2: "+lien.length);
            }
            i++;
        }
        classarray= classe.toArray(new Classe[0]);

        return new Fichier(nom_model,classarray,lien,true);
    }
    public static void treatClass(String[] classtokens,List<Classe> classe )
    {
            String Classnom=classtokens[1];
            List<String> att=new ArrayList<String>();
            int i=3;
            while(!classtokens[i].equals("OPERATIONS"))
            {
                String attAdd="";
                Boolean flag=true;
                while(!classtokens[i].equals("OPERATIONS")&&flag==true)
                {
                    if (classtokens[i].indexOf(',')>0)
                    {
                        flag=false;
                        attAdd+=classtokens[i].substring(0, classtokens[i].length() - 1)+" ";
                        i++;
                    }
                    else{
                        attAdd+=classtokens[i]+ " ";
                        i++;
                    }
                }
                att.add(attAdd);
            }
            i++;
            String[] attribute=att.toArray(new String[0]);
            List<String> ope=new ArrayList<String>();;
            while(!classtokens[i].equals(";"))
            {
              String opeAdd="";
              Boolean flag=true;
                while(!classtokens[i].equals(";")&&flag==true) {
                    if (classtokens[i].indexOf(',')>0)
                    {
                        flag=false;
                        opeAdd+=classtokens[i].substring(0, classtokens[i].length() - 1)+" ";
                        i++;
                    }
                    else{
                        opeAdd+=classtokens[i]+ " ";
                        i++;
                    }
                }
                ope.add(opeAdd);
            }
            String[] operation=ope.toArray(new String[0]);
            String[] liens=new String[0];
            classe.add(new Classe(Classnom,attribute,operation,liens));

    }
    public static void treatGen(String[] classtokens, List<Classe> classe )
    {
        String class_concerne=classtokens[1].trim();
        for (int j=0;j<classe.size();j++ )
        {
            if (classe.get(j).nom.equals(class_concerne))
            {
                int i=3;
                while(!classtokens[i].equals(";")) {
                    if (classtokens[i].indexOf(',')>0)
                    {
                        classe.get(j).addsousclasse(classtokens[i].substring(0, classtokens[i].length() - 1)+" ");
                        i++;
                    }
                    else{
                        classe.get(j).addsousclasse(classtokens[i]);
                        i++;
                    }
                }
            }
        }
    }
    public static Lien[] treatRel(String[] reltokens, Lien[] lien,List<Classe>classe)
    {
        String nom_lien=reltokens[1];
        String[] class_concerne=new String[2];
        String[] cardinalite=new String[2];
        class_concerne[0]=reltokens[4];
        cardinalite[0]=reltokens[5];
        class_concerne[1]=reltokens[7];
        cardinalite[1]=reltokens[8];
        for (int j=0;j<classe.size();j++ )
        {
            if (classe.get(j).nom.trim().equals(class_concerne[0].trim())||classe.get(j).nom.trim().equals(class_concerne[1].trim()))
            {
                classe.get(j).addliens(lien.length);
            }
        }
        int length = lien.length;
        Lien tmp[] = new Lien[length + 1];
        for (int k = 0; k < length; k++) {
            tmp[k] = lien[k];
        }
        lien = tmp;
        lien[length] = new Lien(class_concerne,cardinalite,nom_lien);
        return lien;
    }

    public static Lien[] treatAgg(String[] aggtokens, Lien[] lien,List<Classe>classe)
    {
            String nom_lien="Aggregation";
            List<String> parts_card= new ArrayList<String>();
            List<String> partss= new ArrayList<String>();
            List<String> cont= new ArrayList<String>();
            List<String> cont_card= new ArrayList<String>();
            String[] parts_cardinalite;
            String[] parts=new String[1];
            String[] container_cardinalite=new String[1];
            String[] container=new String[1];
            int i=2;
            while(!aggtokens[i].equals("PARTS")) {
                if (aggtokens[i].equals("CLASS")) {
                    i++;
                    cont.add(aggtokens[i]);
                    i++;
                    cont_card.add(aggtokens[i]);
                    i++;
                }
                else{
                    i++;
                }

            }
            while(!aggtokens[i].equals(";")) {
                 if (aggtokens[i].equals("CLASS")) {
                    i++;
                    partss.add(aggtokens[i]);
                    i++;
                    parts_card.add(aggtokens[i]);
                    i++;
                }
                 else{
                     i++;
                 }
            }
            for (int j=0;j<classe.size();j++ )
            {
               for (int k=0;k<cont.size();k++) {
                   if (classe.get(j).nom.equals(cont.get(k))) {
                       classe.get(j).addliens(lien.length);
                   }
               }
                for (int k=0;k<partss.size();k++) {

                    if (classe.get(j).nom.equals(partss.get(k))) {
                        classe.get(j).addliens(lien.length);
                    }
                }
            }
            int length = lien.length;
            Lien tmp[] = new Lien[length + 1];
            for (int k = 0; k < length; k++)
            {
                tmp[k] = lien[k];
            }
            container= cont.toArray(new String[0]);
            container_cardinalite= cont_card.toArray(new String[0]);
            parts= partss.toArray(new String[0]);
            parts_cardinalite= parts_card.toArray(new String[0]);
            lien = tmp;
            lien[length] = new Lien(container,container_cardinalite, parts,parts_cardinalite,nom_lien);
            return lien;
    }
}
