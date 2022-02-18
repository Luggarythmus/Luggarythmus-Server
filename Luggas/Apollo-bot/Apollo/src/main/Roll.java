package main;

import java.util.HashMap;
import java.util.Random;

import game.Game;
import game.Player;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Roll {

	public Roll(MessageChannel channel1, Message msg) {
		
		if(!(msg==null)) {
			String benutzer =(msg.getAuthor().getId());
			String swert=msg.getContentRaw().substring(6,msg.getContentRaw().length());
			try {
				int iwert = Integer.parseInt(swert);
				channel1.sendMessage(""+"<@"+benutzer+">"+" hat gerollt: "+rollen(iwert)).queue();
			}catch(Exception e){
				channel1.sendMessage("Fehler: Bitte Wert richtig eingeben!").queue();
				//e.printStackTrace();
			}
		}
		
	
	}
	
	public int rollen(int roll) {
		Random rnd = new Random();
		return rnd.nextInt(roll)+1;
		
	}
	public void rollGame(MessageChannel channel, User von, User gegner) {
		Random rnd=new Random();
		Game ga=new Game(channel,von,gegner);
		HashMap<String, Player> ar= new HashMap<String,Player>();
		ar=ga.laden();
		Player vonp=ar.get(von.getName());
		Player gegenp= ar.get(gegner.getName());
		
		
		int v= rnd.nextInt(100)+1;
		int ge= rnd.nextInt(100)+1;
		try {
			Thread.sleep(1000);
			channel.sendMessage("Rollen fuer: "+"<@"+gegner.getId()+">").queue();
			Thread.sleep(3000);
			channel.sendMessage("Ergebnis: "+ge).queue();
			channel.sendMessage("Rollen fuer: "+"<@"+von.getId()+">").queue();
			Thread.sleep(3000);
			channel.sendMessage("Ergebnis: "+v).queue();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(v>ge) {
			vonp.setWins(vonp.getWins()+1);
			gegenp.setLosses(gegenp.getlosses()+1);
			channel.sendMessage("<@"+von.getId()+">"+ " gewinnt").queue();
		}else if(ge>v) {
			vonp.setLosses(vonp.getlosses()+1);
			gegenp.setWins(gegenp.getWins()+1);
			channel.sendMessage("<@"+gegner.getId()+">"+ " gewinnt").queue();
		}
		
		ar.put(von.getName(), vonp);
		ar.put(gegner.getName(), gegenp);
		
		ga.speichern(ar);
		
	}
	

}
