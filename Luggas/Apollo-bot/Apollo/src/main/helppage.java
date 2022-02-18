package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import game.Player;
import schedule.Ereignis;

public class helppage {
	private HashMap<String, String> hm ;
	public helppage() {
			hm= new HashMap<String,String>();
			hm=laden();
	}
	
	
	
	public void fuellen() {
		hm.put("!ping", "Ping-Pong-Spiel mit dem Bot");
		hm.put("!time", "Rueckgabe der Zeit");
		hm.put("!Rick", "Rick-Roll");
		hm.put("!lama", "ein zufaelliges Lama .gif wird ausgegeben");
		hm.put("!help", "Anzeige aller moeglichen Befehle");
		hm.put("!clear", "loeschen der letzten 50 Nachrichten (sofern diese nicht laenger als 14 Tage her sind");
		hm.put("!Roll", "erzeugen einer Zufallszahl im Bereich: 1- angegebener Zahl");
		hm.put("!plan", "setzt ein Reminder  fuer ein angegebnes Event (angeben mit Parameter noetig) [!plan eventname Parameter Zeitpunkt]");
		hm.put("!join", "laesst den Bot in ein VoiceChannel joinen (dessen Namen als Parameter angegeben wurde)");
		hm.put("!leave", "laesst den Bot den VoiceChannel leaven (Vorraussetzung dafuer ist, dass dieser in einen VoiceChannel ist");
		hm.put("!battle", "Sendet eine Battle Anfrage an den mentioned Member, die Auswahl des Spiels erfolgt mittels Reaktionen"+
				"\n          -stats als Parameter gibt die Statistik des Spielers aus");
		hm.put("!request", "Dient zur Erstellung eines Ban-Antrags an den mentioned Member[!request @Member Grund");
		speichern(hm);
	}
	
	
	
	public void speichern(HashMap<String,String> ar) {
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		
		try {
		//	fos= new FileOutputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt"); -> Linux
			String s = Paths.get("").toAbsolutePath().toString();
			fos=new FileOutputStream(s+"/src/helppage.txt");
			oos= new ObjectOutputStream(fos);
		
			
			oos.writeObject(ar);
			
			
			oos.close();
			oos.flush();
			
			
		}catch(Exception e) {
			System.out.println("Speichern fehlgeschlagen!");
			e.printStackTrace();
		}
		
		
	}

	public HashMap<String,String> laden() {
	InputStream fis=null;
		ObjectInputStream ois=null;
		HashMap<String,String> playerlist= new HashMap<String,String>();
	
		try {
			//fis=new FileInputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt");   ->Linux
		
			String s = Paths.get("").toAbsolutePath().toString();
			fis=new FileInputStream(s+"/src/helppage.txt");
			
			ois= new ObjectInputStream(fis);
			playerlist= (HashMap<String, String>) ois.readObject();
			ois.close();
			fis.close();
				
				
		}catch(Exception e) {
			System.out.println("Laden fehlgeschlagen!");
			e.printStackTrace();
		}
		return playerlist;
	}
	
	
	public String getHM() {
		String Ausgabe="";
		Iterator it = hm.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> en= (Entry<String, String>) it.next();
			Ausgabe += ""+en.getKey()+" - "+en.getValue()+"\n";
		}
		
		return Ausgabe;
	}
}
