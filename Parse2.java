import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Parse2 {
    public static Fichier load(String pathString) {
        Path path=FileSystems.getDefault().getPath(pathString);
        List<String> lines = null;
        String[] linelist=null;
        String[] tokens = new String[0];
        String[] tempTokens=null;
        try {
            //this returns null
            lines = Files.readAllLines(path, Charset.defaultCharset());
            System.out.println(lines);
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
                for (int j=i-start;j<=i;j++)
                {
                    classtokens[j-(i-start)]=tokens[j];
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
                String[] classtokens=new String[i-start];
                for (int j=i-start;j<=i;j++)
                {
                    classtokens[j-(i-start)]=tokens[j];
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
                String[] classtokens=new String[i-start];
                for (int j=i-start;j<=i;j++)
                {
                    classtokens[j-(i-start)]=tokens[j];
                }
                treatRel(classtokens,lien,classe);
            }
            else if(tokens[i].equals("AGGREGATION"))
            {
                int start=i;
                while(!tokens[i].equals(";"))
                {
                    i++;
                }
                String[] classtokens=new String[i-start];
                for (int j=i-start;j<=i;j++)
                {
                    classtokens[j-(i-start)]=tokens[j];
                }
                treatAgg(classtokens,lien,classe);
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
                    attAdd+=classtokens[i];
                    i++;
                    if (classtokens[i].indexOf(',')<0) {flag=false;
                    }
                }
                att.add(attAdd);
            }
            String[] attribute=att.toArray(new String[0]);
            i++;
            List<String> ope=new ArrayList<String>();;
            while(!classtokens[i].equals(";"))
            {
              String opeAdd="";
              Boolean flag=true;
                while(!classtokens[i].equals(";")&&flag==true)
                {System.out.println(classtokens[i]);
                 opeAdd+=classtokens[i];
                 i++;
                 if (classtokens[i].indexOf(',')>0)
                 {
                     flag=false;
                 }
                }
            att.add(opeAdd);
        }
            String[] operation=ope.toArray(new String[0]);
            String[] liens=new String[0];
            classe.add(new Classe(Classnom,attribute,operation,liens));

    }
    public static void treatGen(String[] classtokens, List<Classe> classe )
    {
        String class_concerne=classtokens[1];
        for (int j=3;j<classe.size();j++ )
        {
            if (classe.get(j).nom.equals(class_concerne))
            {
                while(!classtokens[j].equals(";")) {

                    classe.get(j).addsousclasse(classtokens[j]);
                }
            }
        }
    }
    public static void treatRel(String[] reltokens, Lien[] lien,List<Classe>classe)
    {
        String nom_lien=reltokens[1];
        String[] class_concerne=new String[2];
        String[] cardinalite=new String[2];
        class_concerne[0]=reltokens[5];
        cardinalite[0]=reltokens[6];
        class_concerne[1]=reltokens[8];
        cardinalite[1]=reltokens[9];
        for (int j=0;j<classe.size();j++ )
        {
            if (classe.get(j).nom.equals(class_concerne[0])||classe.get(j).nom.equals(class_concerne[1]))
            {
                classe.get(j).addliens(lien.length);
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

    public static void treatAgg(String[] aggtokens, Lien[] lien,List<Classe>classe)
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
            tmp[length] = new Lien(container,container_cardinalite, parts,parts_cardinalite,nom_lien);
            lien = tmp;

    }
}
