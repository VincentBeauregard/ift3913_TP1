import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class fichier {
	public static Fichier load(String pathString) {
		Path path=FileSystems.getDefault().getPath(pathString);
		List<String> lines = null;   
		String[] tokens = null;
		try{
			        //this returns null
		lines = Files.readAllLines(path, Charset.defaultCharset());
		System.out.println(lines);
		tokens=lines.toArray(new String[0]);
		
		
		for (int i=0;i<tokens.length;i++)
		{
			System.out.println(tokens[i]);
		}
		if(order(tokens)==-1)
			return new Fichier("pathString",null, null,false);
		else 
			return new Fichier("test",null, null,true);
		}catch(IOException ex){
			System.out.println(ex.getMessage());
			return new Fichier("pathString",null, null,false);
		}
	}
	public static int order(String[] tokens)
	{
		String[] attribute;
		
		String[] part=tokens[0].split(" +");
		if (part[0].equals("MODEL")&& part.length==2)
		{
			Model UML = new Model(part[1]);
			return 1;
		}
		else{
			System.out.println("error");
			return -1;
		}
		//for (int x)
	}
}
