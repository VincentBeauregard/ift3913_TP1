
public class Metrique {
	
	public static String calculMetrique(Classe classe){
		String ANA = calcul_ANA(classe);
		return "0,1,2,3,4,5,6,7,8,9";
		
	}
	public static String calcul_ANA(Classe classe){
		int nb=classe.methodes.length;
		int count=0;
		for (int i=0;i<nb;i++)
		{
			count+=classe.methodes[i].arg.length;
		}

		return String.valueOf(count/nb);

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
