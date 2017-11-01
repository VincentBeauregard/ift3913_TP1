import java.util.ArrayList;
import java.util.List;

public class Metrique {
	
	public static String calculMetrique(Classe classe, Fichier file){
		List<Methode> met=new ArrayList<Methode>();
		List<Methode> metOther=new ArrayList<Methode>();
		List<Attribut> att=new ArrayList<Attribut>();
		String ANA = calcul_ANA(classe);
		String NOM = calcul_NOM(classe,met);
		String NOA = calcul_NOA(classe,att);
		String ITC = calcul_ITC(classe, met, file);
		String ETC = calcul_ETC(classe, met, metOther, file);
		return ANA+","+NOM+","+NOA+","+ITC+","+ETC+",4,5,6,7,8,9";
		
	}
	public static String getMetric(Classe classe, Fichier file){
		String metric = calculMetrique(classe, file);
		String[] metrics = metric.split(",");
		String rval = "ANA="+metrics[0]+"\n";
		rval += "NOM="+metrics[1]+"\n";
		rval += "NOA="+metrics[2]+"\n";
		rval += "ITC="+metrics[3]+"\n";
		rval += "ETC="+metrics[4]+"\n";
		rval += "CAC="+metrics[5]+"\n";
		rval += "DIT="+metrics[6]+"\n";
		rval += "CLD="+metrics[7]+"\n";
		rval += "NOC="+metrics[8]+"\n";
		rval += "NOD="+metrics[9]+"\n";
		return rval;
	}
	public static String calcul_ANA(Classe classe){
		int nb=classe.methodes.length;
		System.out.println(nb);
		int count=0;
		for (int i=0;i<nb;i++){
			count+=classe.methodes[i].arg.length;
		}
		if(nb>0)
			return String.format("%.2f", ((double)count/nb));
		else
			return "0";
	}
	public static String calcul_NOM(Classe classe, List<Methode> met){
		recCheck(met,classe);
		System.out.println(met.size());
		return String.valueOf(met.size());
		
	}
	public static void recCheck(List<Methode> met, Classe classe) {

		for (int i=0;i<classe.methodes.length;i++)
		{
			if (!checkSame(met,classe.methodes[i] )){
				met.add(classe.methodes[i]);
			}

		}
		for (int i=0;i<classe.surClasses.length;i++)
		{
			recCheck(met, classe.surClasses[i]);
		}
		System.out.println(met.size());
		return;
	}
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
		recCheckAtt(att,classe);
		System.out.println(att.size());
		return String.valueOf(att.size());
		
	}
	public static void recCheckAtt(List<Attribut> att, Classe classe) {

		for (int i=0;i<classe.attributs.length;i++)
		{
			if (!checkSameAtt(att,classe.attributs[i] )){
				att.add(classe.attributs[i]);
			}

		}
		for (int i=0;i<classe.surClasses.length;i++)
		{
			recCheckAtt(att, classe.surClasses[i]);
		}
		System.out.println(att.size());
		return;
	}
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
	public static String calcul_ITC(Classe classe,List<Methode> met, Fichier file ){
		int count=0;
		for(int i=0; i<met.size();i++)
		{
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
	public static String calcul_ETC(Classe classe, List<Methode>met, List<Methode>metOther, Fichier file){

		metOther=otherMed(classe,met,metOther,file);
		int count=0;
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

	public static List<Methode> otherMed(Classe classe, List<Methode>met,List<Methode>metOther, Fichier file)
	{
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
	public String calcul_CAC(Classe classe){
		return null;
		
	}
	public String calcul_DIT(Classe classe){
		return null;
		
	}
	public String calcul_CLD(Classe classe){
		return null;
		
	}
	public String calcul_NOC(Classe classe){
		return null;
		
	}
	public String calcul_NOD(Classe classe){
		return null;
		
	}
}
