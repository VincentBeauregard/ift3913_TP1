import java.util.ArrayList;
import java.util.List;

public class Metrique {
	//fonction qui appelle les 10 fonctions de calcul et qui retourne les resultats en une string
	public static String calculMetrique(Classe classe, Fichier file){
		//les deux listes sont calculees une seule fois mais utilisees par plusieurs calculs
		//listes des methodes locales+heritees de la classe
		List<Methode> met= new ArrayList<Methode>();
		//listes des attributs locals+herites de la classe
		List<Attribut> att= new ArrayList<Attribut>();
		String ANA = calcul_ANA(classe);
		String NOM = calcul_NOM(classe,met);
		String NOA = calcul_NOA(classe,att);
		String ITC = calcul_ITC(classe, met, file);
		String ETC = calcul_ETC(classe, met, file);
		String DIT = calcul_DIT(classe);
		String NOD = calcul_NOD(classe);
		String CAC = calcul_CAC(classe);
		return ANA+","+NOM+","+NOA+","+ITC+","+ETC+","+ CAC+","+DIT+","+NOD;
		
	}
	//cette fonction communique avec les autres classes du logiciel et formatte la string
	public static String getMetric(Fichier file,Classe classe){
		String metric = calculMetrique(classe, file);
		String[] metrics = metric.split(",");
		String rval = "ANA="+metrics[0]+",";
		rval += "NOM="+metrics[1]+",";
		rval += "NOA="+metrics[2]+",";
		rval += "ITC="+metrics[3]+",";
		rval += "ETC="+metrics[4]+",";
		rval += "CAC="+metrics[5]+",";
		rval += "DIT="+metrics[6]+",";
		rval += "CLD="+metrics[7]+",";
		rval += "NOC="+metrics[8]+",";
		rval += "NOD="+metrics[9]+",";
		return rval;
	}
	public static String calcul_ANA(Classe classe){
		int nb=classe.methodes.length;
		int count=0;
		//compte le nombre d'arguments des methodes une methode a la fois
		for (int i=0;i<nb;i++){
			count+=classe.methodes[i].arg.length;
		}
		//evite la division par zero
		if(nb>0)
			return String.format("%.2f", ((double)count/nb));
		else
			return "0";
	}
	public static String calcul_NOM(Classe classe, List<Methode> met){
		//appelle la fonction recursive qui placera tout les methodes dans les listes SI elle n'y sont pas deja
		recCheck(met,classe);
		//retourne la grandeur de la liste
		return String.valueOf(met.size());
		
	}
	//fonction recursive qui placera tout les methodes dans les listes SI elle n'y sont pas deja puis s'appelle elle-meme avec les surclasses en parametre
	public static void recCheck(List<Methode> met, Classe classe) {

		for (int i=0;i<classe.methodes.length;i++)
		{
			//checkSame verifie si la classe n'est pas deja dans la liste
			if (!checkSame(met,classe.methodes[i] )){
				met.add(classe.methodes[i]);
			}

		}
		for (int i=0;i<classe.surClasses.length;i++)
		{
			//recursion
			recCheck(met, classe.surClasses[i]);
		}
		return;
	}
	//verifie si la methode est deja dans la liste
	public static Boolean checkSame (List<Methode> met, Methode toCheck)
	{
		for (int i=0; i<met.size();i++)
		{
			Boolean flag=false;
			if ((met.get(i).nom).equals(toCheck.nom)&&(met.get(i).type).equals(toCheck.type)&&met.get(i).arg.length==toCheck.arg.length)
			{
				flag=true;
				for (int j=0;j<met.get(i).arg.length;j++){
					if (!(met.get(i).arg[j].type.equals(toCheck.arg[j].type)))
					{
						flag=false;
					}
				}
			}
			if (flag==true)
			{
				return true;
			}
		}
		return false;
	}

	public static String calcul_NOA(Classe classe, List<Attribut> att){
		//appelle la fonction recursive qui placera tout les attributs dans les listes SI elle n'y sont pas deja
		recCheckAtt(att,classe);
		//retourne la grandeur de la liste des attributs
		return String.valueOf(att.size());
		
	}
	//fonction recursive qui placera tout les attributs dans les listes SI elle n'y sont pas deja puis s'appelle elle-meme avec les surclasses en parametre
	public static void recCheckAtt(List<Attribut> att, Classe classe) {

		for (int i=0;i<classe.attributs.length;i++)
		{
			if (!checkSameAtt(att,classe.attributs[i] )){
				att.add(classe.attributs[i]);
			}

		}
		for (int i=0;i<classe.surClasses.length;i++)
		{
			//recursion avec les surclasses
			//la recursion arrete lorsqu'il n'y a plus de surclasse
			recCheckAtt(att, classe.surClasses[i]);
		}
		return;
	}
	//verifie si l'attribut est deja dans la liste
	public static Boolean checkSameAtt (List<Attribut> att, Attribut toCheck)
	{
		for (int i=0; i<att.size();i++)
		{
			if ((att.get(i).nom).equals(toCheck.nom)&&(att.get(i).type).equals(toCheck.type))
			{
				return true;
			}
		}
		return false;
	}
	//compte le nombre de fois que les types des arguments des methodes de la classe sont des autres classes
	public static String calcul_ITC(Classe classe,List<Methode> met, Fichier file ){
		//compte le nombre d'occurences
		int count=0;
		//met est la liste des methodes de la classe
		for(int i=0; i<met.size();i++)
		{
			//pour chaque methode on passe chaque argument et on verifie avec les noms de chaque classe
			for (int k=0; k<met.get(i).arg.length;k++)
			{
				for (int j=0;j<file.classes.length;j++)
				{
					if ((!file.classes[j].nom.equals(classe.nom)))
					{
						if (met.get(i).arg[k].type.equals(file.classes[j].nom))
						{
							count++;
						}
					}
				}

			}
		}
		return String.valueOf(count);
	}
	//calcule le nombre de fois que la classe est un argument de methodes de d'autre classe
	public static String calcul_ETC(Classe classe, List<Methode>met, Fichier file){
		List<Methode> metOther= new ArrayList<Methode>();
		//cree un liste de toutes les methodes du fichier qui ne sont pas locales ou heritee a la classe concernee
		metOther=otherMed(classe,met,metOther,file);
		//compte le nb d'occurence
		int count=0;
		//verifie pour chaque methode de la liste la classe est le type d'un des arguments
		for(int i=0; i<metOther.size();i++)
		{
			for (int k=0; k<metOther.get(i).arg.length;k++)
			{

				if (metOther.get(i).arg[k].type.equals(classe.nom))
				{
					count++;
				}



			}
		}
		return String.valueOf(count);
		
	}
	//cree un liste de toutes les methodes du fichier qui ne sont pas locales ou heritee a la classe concernee
	public static List<Methode> otherMed(Classe classe, List<Methode>met,List<Methode>metOther, Fichier file)
	{
		//pour chaque classe, on prend chaque methode et on verifie si elle n'est pas dans la liste met ou dans la liste qu'on cree (metOther)
		for (int j=0;j<file.classes.length;j++)
		{
			if ((!file.classes[j].nom.equals(classe.nom)))
			{
				for (int i=0; i<file.classes[j].methodes.length;i++)
				{
					if (checkSame(met,file.classes[j].methodes[i])==false)
					{
						if (checkSame(metOther,file.classes[j].methodes[i])==false)
						{
							metOther.add(file.classes[j].methodes[i]);
						}
					}

				}
			}
		}
		return metOther;
	}
	//compte le nombre de lien de la classe et de ses surclasses par la recursion
	public static String calcul_CAC(Classe classe){
		List<Integer> lien=new ArrayList<Integer>();
		recCheckLien(lien,classe);
		return String.valueOf(lien.size());
		
	}
	//rajouter le numero des liens de la classe et s'appelle elle-meme avec les surclasses si presente en parametre
	public static void recCheckLien(List<Integer> lien, Classe classe) {

		for (int i=0;i<classe.indexLiens.length;i++)
		{
			if (!checkSameLien(lien,classe.indexLiens[i] )){
				lien.add(classe.indexLiens[i]);
			}
		}
		for (int i=0;i<classe.surClasses.length;i++)
		{
			recCheckLien(lien, classe.surClasses[i]);
		}
		System.out.println(lien.size());
		return;
	}
	//verifie si le lien est deja dans la liste pour eviter les doublons
	public static Boolean checkSameLien (List<Integer> lien, int toCheck)
	{
		for (int i=0; i<lien.size();i++)
		{
			if ((lien.get(i)).equals(toCheck))
			{
				return true;
			}
		}
		return false;
	}
	private static int nbParent(Classe c){
		int length = c.surClasses.length;
		int rval = 0;
		if(length == 0)
				return 0;
		else{
			int maxtmp=0;
			for(int i=0; i<length; i++){
				rval = nbParent(c.surClasses[i]);
				if(rval>maxtmp)
					maxtmp=rval;
				else
					rval = maxtmp;
			}
			return rval + 1; 
		}
	}
	public static String calcul_DIT(Classe classe){
		return ""+nbParent(classe);
	}
	
	private static int[] nbrchild(Classe c){
		int length = c.sousClasses.length;
		int rval[] = new int[2];
		if(length==0){
			rval[0]=0;
			rval[1]=0;
			return rval;
			}
		else{
			int maxtmp=0;
			int count = 0;
			for(int i =0;i<length;i++){
				rval = nbrchild(c.sousClasses[i]);
				if(rval[1]>maxtmp)
					maxtmp=rval[1];
				else 
					rval[1]=maxtmp;
				rval[0]+=count;
				count = rval[0];
			}
			rval[0] += length;
			rval[1] += 1;
			return rval;
		}
	}
	//considerant qu'il n'y a pas de cycle dans le grape des classes cettee fonction calcul le nombre de sous classe total
	//le cas ou il y a un cycle, tout plente
	public static String calcul_NOD(Classe classe){
		int rvaltab[] = nbrchild(classe);
		return rvaltab[1]+","+classe.sousClasses.length+","+rvaltab[0];
		
	}
}
