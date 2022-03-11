package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;


public class Anpassung {
	
	public static void main(String[] args) {
		
		HashMap<String, Player> ar= new HashMap<String,Player>();
		ar=laden();
		Player lukas= new Player("Lukinator153 | ルーカス");
		Player Phillip = new Player("IluPlays");
		
		lukas.setWins(4);
		lukas.setLosses(0);
		Phillip.setWins(0);
		Phillip.setLosses(3);
		
		ar.put("Lukinator153 | ルーカス",lukas);
		ar.put("IluPlays",Phillip);
		speichern(ar);
		
		
		
	}
	
	public static void speichern(HashMap<String,Player> ar) {
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		
		try {
		//	fos= new FileOutputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt"); ->Linux
			String s = Paths.get("").toAbsolutePath().toString();
			fos=new FileOutputStream(s+"/registrierteSpieler.txt");
			oos= new ObjectOutputStream(fos);
		
			
			oos.writeObject(ar);
			
			
			oos.close();
			oos.flush();
			
			
		}catch(Exception e) {
			System.out.println("Speichern fehlgeschlagen!");
			e.printStackTrace();
		}
		
		
	}

	public static HashMap<String,Player> laden() {
	InputStream fis=null;
		ObjectInputStream ois=null;
		HashMap<String,Player> playerlist= new HashMap<String,Player>();
	
		try {
		//	fis=new FileInputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt"); ->Linux
			String s = Paths.get("").toAbsolutePath().toString();
			fis=new FileInputStream(s+"/registrierteSpieler.txt");
			ois= new ObjectInputStream(fis); 
			playerlist= (HashMap<String, Player>) ois.readObject();
			ois.close();
			fis.close();
				
				
		}catch(Exception e) {
			System.out.println("Laden fehlgeschlagen!");
			e.printStackTrace();
		}
		return playerlist;
	}
	
}
