import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


public class fichier {
	public static Model load(String pathString) {
		Path path = FileSystems.getDefault().getPath(pathString);
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

		/*if(order(tokens)==-1)
			return new Model("pathString",null, null,false);
		else 
			return new Model("test",null, null,true);
		}
		*/
			return order(tokens);
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
			return new Model("pathString",null, null,false);
		}

	}

	public static Model order(String[] tokens)
	{
		String[] part=tokens[0].split(" +");
		List<Classe> classe= new ArrayList<Classe>();
		Classe[] classarray;
		String nom_model="";
		if (part[0].trim().equals("MODEL")&& part.length==2)
		{
			nom_model = part[1];
			System.out.println("The model name is "+nom_model);
		}
		else{
			System.out.println("error");

		}
		int i =0;
		while (i<tokens.length)
		{
			i++;
			part=tokens[i].split(" +");
			if (part[0].trim().equals("CLASS")&& part.length==2)
			{
				System.out.println("The class name is "+part[1]);
				String Classnom=part[1];
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
					System.out.println(part.length);
					System.out.println(part[0]);
					System.out.println(!part[0].equals("OPERATIONS"));
					System.out.println(part[0]+" "+part[1]);

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
					System.out.println(part[0]);
					i++;
					part[0] = tokens[i];
				}
				String[] operation=ope.toArray(new String[0]);
				classe.add(new Classe(Classnom,attribute,operation));
			}
			/*else if(part[0].equals("GENERALIZATION"))
			{

			}
			*/
		}
		//for (int x)
		classarray= classe.toArray(new Classe[0]);
		return new Model(nom_model,classarray,null,true);
	}
}
