import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class Parse2 {
    //La fonction load prends en argument le path du document a analyser et retourne un objet Fichier qui sera utiliser par le GUI
    public static Fichier load(String pathString) {
        List<String> lines = null;
        String[] linelist=null;
        String[] tokens = new String[0];
        String[] tempTokens=null;
        Path path=FileSystems.getDefault().getPath(pathString);
        try {
            //this returns null
            lines = Files.readAllLines(path, Charset.defaultCharset());
            lines.removeAll(Collections.singleton(null));
            lines.removeAll(Collections.singleton(""));
            linelist = lines.toArray(new String[0]);

            //cette boucle divise les lignes du fichier en mots et les store dans le tableau tokens
            for (int i = 0; i < linelist.length; i++) {
                linelist[i]=linelist[i].replace(":","  ");
                linelist[i]=linelist[i].replace(",","  ");
                linelist[i]=linelist[i].replace(";"," ; ");
                linelist[i]=linelist[i].replace("("," ( ");
                linelist[i]=linelist[i].replace(")"," ) ");
                tempTokens=linelist[i].trim().split(" +");
                int length = tokens.length;
                String tmp[] = new String[length + tempTokens.length];
                for (int j = 0; j < length; j++) {
                    tmp[j] = tokens[j].trim();
                }
                tokens=tmp;
                for (int j = length; j < length+tempTokens.length; j++) {
                    tokens[j] = tempTokens[j-length];
                }
            }
            for (int i=0; i<tokens.length;i++)
            {
                System.out.println(i + " "+ tokens[i]+" "+tokens[i].length());
            }
            //appelle la fonction divide qui creera l,objet Fichier
            return divide(tokens);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return new Fichier("Impossible d'ouvrir le fichier",null, null,false);
        }
    }
    public static Fichier divide(String[] tokens)
    {
        System.out.println("ok");
        //creation des variables necessaires
        List<Classe> classe= new ArrayList<Classe>();
        Classe[] classarray;
        String nom_model="";
        Lien[] lien =new Lien[0];
        List<String[]> gen= new ArrayList<String[]>();
        List<String[]> rel= new ArrayList<String[]>();
        List<String[]> agg= new ArrayList<String[]>();
        for (int i=0;i<tokens.length;i++)
        {
            tokens[i]=tokens[i].replaceAll("[\u0000-\u001f]", "");
        }
        //si le document ne commence pas par MODEL, ce nest pas le bon format
        if (!tokens[0].equals("MODEL"))
        {
            System.out.println(tokens[0]+tokens[0]);
            System.out.println(tokens[0].length());
            System.out.println(tokens[0].charAt(0));
            System.out.println(tokens[0].charAt(1));
            System.out.println(tokens[0].charAt(2));
            System.out.println(tokens[0].charAt(3));
            System.out.println(tokens[0].charAt(4));
            System.out.println(tokens[0].charAt(5));
            return new Fichier(null,null,null,false);
        }
        else
        {
            System.out.println("oka");
            nom_model=tokens[1];
        }
        //cherche les mots class, generalization, relation et aggregation pour traiter les mots qui suivent
        for(int i=2;i<tokens.length;i++)
        {
            //jusquau prochain point virgule tout les mots seront envoye a un fonction qui creera la classe dans la liste classe
            if(tokens[i].equals("CLASS"))
            {
                int start=i;
                while(!tokens[i].equals(";"))
                {
                    System.out.println(tokens[i]);
                    i++;
                }
                String[] classtokens=new String[i-start+1];
                for (int j=i;j>=start;j--)
                {
                    classtokens[j-start]=tokens[j];
                }
                treatClass(classtokens,classe);
            }
            //jusquau prochain point virgule tout les mots seront envoye a un fonction qui creera la sousclasse qui sera appeler plus tard
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
                gen.add(classtokens);
            }
            //jusquau prochain point virgule tout les mots seront envoye a un fonction qui creera la relation qui sera appeler plus tard
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
                rel.add(classtokens);
                //System.out.println("lien.length parse2if1 : "+lien.length);
            }
            //jusquau prochain point virgule tout les mots seront envoye a un fonction qui creera l,aggregation qui sera appeler plus tard
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
                agg.add(classtokens);
                //System.out.println("lien.length parse2if2: "+lien.length);
            }
            System.out.println("ok "+i);

        }
        //fait tout les appels pour creer les generalizations, relations et aggregation
        for (int i=0;i<gen.size();i++) {
            System.out.println("ok1");
            treatGen(gen.get(i), classe);
        }
        for (int i=0;i<rel.size();i++) {
            System.out.println("ok2");
            lien = treatRel(rel.get(i),lien,classe);
        }
        for (int i=0;i<agg.size();i++) {
            System.out.println("ok3");
            lien = treatAgg(agg.get(i),lien,classe);
        }
        classarray= classe.toArray(new Classe[0]);
        //cree le fichier
        System.out.println("ok4");
        return new Fichier(nom_model,classarray,lien,true);
    }
    //cette fonction creer un objet classe et le place dans la liste classe
    public static void treatClass(String[] classtokens,List<Classe> classe )
    {
            //
            String Classnom=classtokens[1];
            List<Attribut> att=new ArrayList<Attribut>();
            int i=3;
            //cette boucle place tout les attributs dans une liste
            while(!classtokens[i].equals("OPERATIONS"))
            {
                String nom=classtokens[i];
                String type="";
                i++;
                if (classtokens[i].indexOf(',')>0) {
                    type = classtokens[i].substring(0, classtokens[i].length() - 1);
                }
                else
                {
                    type = classtokens[i];
                }
                i++;
                att.add(new Attribut(nom, type));
            }
            i++;
            Attribut[] attribute=att.toArray(new Attribut[0]);
            List<Methode> ope=new ArrayList<Methode>();;
            //cette boucle place toute les operations dans une liste
            while(!classtokens[i].equals(";"))
            {
                if (classtokens[i].equals(","))
                {
                    i++;
                }
              List<Attribut> attOpp=new ArrayList<Attribut>();
              String nom=classtokens[i];
              String typeAtt="";
              String type="";
              i++;
              if (classtokens[i].equals("("))
              {
                  i++;
                  while(!classtokens[i].equals(")"))
                  {
                      String nomAtt=classtokens[i];
                      i++;

                      if (classtokens[i].equals(","))
                      {
                          i++;
                      }
                      else if (classtokens[i].indexOf(',')>0) {
                          typeAtt = classtokens[i].substring(0, classtokens[i].length() - 1);
                      }
                      else{
                          typeAtt = classtokens[i];
                      }
                      i++;
                      attOpp.add(new Attribut(nomAtt, typeAtt));
                  }
                  i++;
                  if (classtokens[i].equals(","))
                  {
                      i++;
                  }
                  if (classtokens[i].indexOf(',')>0) {
                      type = classtokens[i].substring(0, classtokens[i].length() - 1);
                  }
                  else{
                      type = classtokens[i];
                  }
                  i++;

              }
              Attribut[] arg=attOpp.toArray(new Attribut[0]);
              ope.add(new Methode(nom, type, arg));
            }
            Methode[] operation=ope.toArray(new Methode[0]);
            String[] liens=new String[0];
            //cree la classe
            classe.add(new Classe(Classnom,attribute,operation));

    }
    //cette fonction modifie la liste sous-classe de la classe concerne
    public static void treatGen(String[] classtokens, List<Classe> classe )
    {
        String class_concerne=classtokens[1].trim();
        for (int j=0;j<classe.size();j++ )
        {
            if (classe.get(j).nom.equals(class_concerne))
            {
                int i=3;
                while(!classtokens[i].equals(";")) {
                    if(classtokens[i].equals(",")){
                        i++;
                    }
                    else if (classtokens[i].indexOf(',')>0)
                    {
                        for (int k=0;k<classe.size();k++ ) {
                            if (classe.get(k).nom.equals(classtokens[i].substring(0, classtokens[i].length() - 1))) {
                                //enleve la virgule a la fin des mots si necessaire
                                classe.get(j).addsousclasse( classe.get(k));
                                classe.get(k).addsurclasse( classe.get(j));
                            }

                        }

                    }
                    else{
                        for (int k=0;k<classe.size();k++ ) {
                            if (classe.get(k).nom.equals(classtokens[i])) {
                                classe.get(j).addsousclasse( classe.get(k));
                                classe.get(k).addsurclasse( classe.get(j));
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }
    //cette fonction rajoute un lien dans la liste des liens
    public static Lien[] treatRel(String[] reltokens, Lien[] lien,List<Classe>classe)
    {
        String nom_lien=reltokens[1];
        String[] class_concerne=new String[2];
        String[] cardinalite=new String[2];
        class_concerne[0]=reltokens[4];
        cardinalite[0]=reltokens[5];
        class_concerne[1]=reltokens[7];
        cardinalite[1]=reltokens[8];
        //dans les classes concernees, dans sa liste d,index rajoute lindex du lien qui sera cree a la fin de la fonction
        for (int j=0;j<classe.size();j++ )
        {
            if (classe.get(j).nom.trim().equals(class_concerne[0].trim())||classe.get(j).nom.trim().equals(class_concerne[1].trim()))
            {
                classe.get(j).addliens(lien.length);
            }
        }
        //creation du lien
        int length = lien.length;
        Lien tmp[] = new Lien[length + 1];
        for (int k = 0; k < length; k++) {
            tmp[k] = lien[k];
        }
        lien = tmp;
        lien[length] = new Lien(class_concerne,cardinalite,nom_lien);
        return lien;
    }
    //cette fonction rajoute un lien dans la liste des liens
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
            //parse tout les containers ainsi que leurs cardinalites
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
            //parse tout les parts ainsi que leur cardinalites
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
        //dans les classes concernees, dans sa liste d,index rajoute lindex du lien qui sera cree a la fin de la fonction
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
            //creation du lien
            container= cont.toArray(new String[0]);
            container_cardinalite= cont_card.toArray(new String[0]);
            parts= partss.toArray(new String[0]);
            parts_cardinalite= parts_card.toArray(new String[0]);
            lien = tmp;
            lien[length] = new Lien(container,container_cardinalite, parts,parts_cardinalite,nom_lien);
            return lien;
    }
}
