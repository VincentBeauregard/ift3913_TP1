
public class Metrique {
	
	public static String calculMetrique(Classe classe){
		String ANA = calcul_ANA(classe);
		return ANA+",1,2,3,4,5,6,7,8,9";
		
	}
	public static String getMetric(Classe classe){
		String metric = calculMetrique(classe);
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
	public String calcul_NOM(Classe classe){
		return null;
		
	}
	public String calcul_NOA(Classe classe){
		return null;
		
	}
	public String calcul_ITC(Classe classe){
		return null;
		
	}
	public String calcul_ETC(Classe classe){
		return null;
		
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
