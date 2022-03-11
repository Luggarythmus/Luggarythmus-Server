package schedule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import game.Player;
import net.dv8tion.jda.api.entities.MessageChannel;

public class Scheduler {
//	private final ScheduledExecutorService scheduler =  Executors.newScheduledThreadPool(1);
	private HashMap<String, Ereignis> m; //Key ist der Name und der Zeitpunkt der Ausgabe der Wert
	public Scheduler() {
		aktualisieren();
	}
	public void aktualisieren() { //um Zwischendurch die Map zu aktualisieren (z.B. wenn neue Ereignisse hinzukommen etc.)
		m=laden(); 
	}
	public Ereignis auswahl(HashMap<String, Ereignis> m) {//Ereignis mit der kuerzesten Zeit bis zum Event auswaehlen
		Ereignis kurz=null;
		
		Iterator it = m.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Ereignis> en= (Entry<String, Ereignis>) it.next();
			if(en.getValue().getDif()==null||en.getValue().getDif().getSeconds()<kurz.getDif().getSeconds()) {
				kurz=en.getValue();
			}
		}
		
		return kurz;
	}
	
	public void schedule(MessageChannel channel, String name   , LocalDateTime zeit) {
		
		String chID=channel.getId();
		
		Ereignis er = new Ereignis(name);
		System.out.println("m: "+m);
		if(!(m.containsKey(er.getName()))) { //nur wenn ein 
			channel.sendMessage("Anfrage registriert").queue();
			m.put(er.getName(), er);
			
			//sortieren danach abspeichern
			Ereignis k = auswahl(m); //-> return Wert
			k.setChannel(channel);
			speichern(m); 
			//Das Ereignis was am kuerzesten (zeitlich gesehen) weg ist soll Rechenzeit bekommen -> potentielles 
			
			
			k.wielange(zeit);
			k.sendMsg();
			//k.StopScheduler();
			
			
		}else {
			channel.sendMessage("Event mit diesen Namen bereits registriert es startet in"
					+m.get(er.getName())).queue();
		}
		
	
	
		
	
	}
		
		
	//speichern der Evente als HashMap
	
	public void speichern(HashMap<String,Ereignis> ar) {
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		
		try {
		//	fos= new FileOutputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt"); -> Linux
			String s = Paths.get("").toAbsolutePath().toString();
			fos=new FileOutputStream(s+"/Kalender.txt");
			oos= new ObjectOutputStream(fos);
		
			
			oos.writeObject(ar);
			
			
			oos.close();
			oos.flush();
			
			
		}catch(Exception e) {
			System.out.println("Speichern fehlgeschlagen!");
			e.printStackTrace();
		}
		
		
	}

	public HashMap<String,Ereignis> laden() {
	InputStream fis=null;
		ObjectInputStream ois=null;
		HashMap<String,Ereignis> calandarlist= new HashMap<String,Ereignis>();
	
		try {
			//fis=new FileInputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt");   ->Linux
		
			String s = Paths.get("").toAbsolutePath().toString();
			System.out.println(s);
			fis=new FileInputStream(s+"/Kalender.txt");
			
			ois= new ObjectInputStream(fis);
			calandarlist= (HashMap<String, Ereignis>) ois.readObject();
			ois.close();
			fis.close();
				
				
		}catch(Exception e) {
			System.out.println("Laden fehlgeschlagen!");
			//e.printStackTrace();
		}
		return calandarlist;
	}
	
}
