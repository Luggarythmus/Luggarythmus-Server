package main;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.Random;

import net.dv8tion.jda.api.entities.MessageChannel;

public class Lama {
	
	public Lama(MessageChannel mc) {
		Random rnd = new Random();
		int auswahl=rnd.nextInt(laden().size());
		mc.sendMessage(laden().get(auswahl)).queue();
		
	}
	
	
		private ArrayList<String> laden() {
		InputStream fis=null;
			
			BufferedReader br=null;
			ArrayList<String> lamas= new ArrayList<String>();
		
			try {
				String s = Paths.get("").toAbsolutePath().toString();
				fis=new FileInputStream(s+"/src/Lamas.txt");
		
				br=new BufferedReader(new InputStreamReader(fis));
				
				
				
				  String zeile = null;
		            while ((zeile = br.readLine()) != null) {
		              lamas.add(zeile);
		            } 
				
			br.close();
				fis.close();
					
					
			}catch(Exception e) {
				System.out.println("Laden fehlgeschlagen!");
				e.printStackTrace();
			}
			return lamas;
		}
		

}
