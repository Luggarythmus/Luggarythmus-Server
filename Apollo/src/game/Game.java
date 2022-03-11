package game;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.InputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;



import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;


public class Game {
	private HashMap<String,Player> ar;
	private MessageChannel channel;
	private User von;
	private User gegen;
	public Game(MessageChannel channel,User von, User gegen) {//Uebergabe von PlayerID
		// belegen der Globalen Variablen um zum Beispiel Spielauswahl zu nutzen
		this.channel=channel;
		this.von=von;
		this.gegen=gegen;
			
			
		Player player1= new Player(von.getName());
		Player player2;
		if(gegen==null) {
			 player2= new Player("Dummy");
		}else {
			 player2= new Player(gegen.getName());
		}
		
		
		
		//channel.sendMessage(player1.getname()+"\n"+player2.getname()).queue();
		 ar= new HashMap<String,Player>();
		
		try {
			
		ar=laden();
	
		System.out.println(ar);
		if(!(ar.containsKey(player1.getname()))) {
			
			ar.put(player1.getname(), player1);
		}
		if(!(ar.containsKey(player2.getname()))) {
			
			ar.put(player2.getname(),player2);
		}
		//nicht komplett ueberschreiben sondern nur anhaengen
		
		
		//Spieler mitteils einer ArrayList speichern -> nur wenn noch keine Statistik vorhanden ist
		speichern(ar);
		
		}catch(Exception e) {
			System.out.println("Fehler");
		}
	}
	
	public void stats(User user) {
		
		if(laden().containsKey(user.getName())) {
			Player p=laden().get(user.getName());
		
			channel.sendMessage("Username: "+p.getname() +
								"\n"+"Wins: "+p.getWins()+
								"\n"+"Losses: "+p.getlosses()).queue();
			
		}else {
			channel.sendMessage("User nicht registriert").queue();
		}
		
	}
	
	public void speichern(HashMap<String,Player> ar) {
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		
		try {
		//	fos= new FileOutputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt"); -> Linux
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

	public HashMap<String,Player> laden() {
	InputStream fis=null;
		ObjectInputStream ois=null;
		HashMap<String,Player> playerlist= new HashMap<String,Player>();
	
		try {
			//fis=new FileInputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt");   ->Linux
		
			String s = Paths.get("").toAbsolutePath().toString();
			fis=new FileInputStream(s+"/registrierteSpieler.txt");
			//System.out.println(s);
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
	
	public void ChooseGame() {
		channel.sendMessage("Spiel auswählen:"+"\n"
				+"\u0031\u20E3   "+"Coinflip" +"\n"
				+"\u0032\u20E3   "+"Roll" 	+"\n"		
				).queue(message-> {
					//https://github.com/BreadMoirai/DiscordEmoji/blob/master/src/main/java/com/github/breadmoirai/Emoji.java
					
					message.addReaction("\u0031\u20E3").queue();//nein
					message.addReaction("\u0032\u20E3").queue();//ja
				
				});
		//Coinflip
	
		
		
		
	}
	

}
