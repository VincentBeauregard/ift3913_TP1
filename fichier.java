import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class fichier {
	public static void main (String[] args) {
		Path path=FileSystems.getDefault().getPath("C:/Users/Admin/Downloads/Ligue.ucd.txt");
		List<String> lines = null;   
		String[] tokens = null;
		try{
			        //this returns null
		lines = Files.readAllLines(path, Charset.defaultCharset());  
		tokens=lines.toArray(new String[0]);
		}catch(IOException ex){
			System.out.println(ex.getMessage());
		}
		
		for (int i=0;i<tokens.length;i++)
		{
			System.out.println(tokens[i]);
		}
		order(tokens);
	}
	public static void order(String[] tokens)
	{
		String[] attribute;
		
		String[] part=tokens[0].split(" +");
		if (part[0].equals("MODEL")&& part.length==2)
		{
			Model UML = new Model(part[1]);
		}
		else{
			System.out.println("error");
		}
		//for (int x)
	}
}
