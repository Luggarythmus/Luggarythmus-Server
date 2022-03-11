package game;


import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.entities.User;


public class Coinflip {
	public User von;
	public MessageChannel channel;
	public User gegen;
	public Game g;
	
	public Coinflip(MessageChannel channel,User von, User gegen) {
		this.channel=channel;
		this.von=von;
		this.gegen=gegen;
		this.g=new Game(channel,von,gegen);
	}
	
	public void ChooseSide() {
		channel.sendMessage("Kopf oder Zahl? " + "<@"+gegen.getId()+">"+"\n"
				+"\u0031\u20E3   "+ "Kopf" + "\n"
				+"\u0032\u20E3   "+"Zahl ").queue(message-> {
					//https://github.com/BreadMoirai/DiscordEmoji/blob/master/src/main/java/com/github/breadmoirai/Emoji.java
					message.addReaction("\u0031\u20E3").queue();//nein
					message.addReaction("\u0032\u20E3").queue();//ja
				});
			
		/*	try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
		//RestAction<List<Message>> msgs=channel.getHistoryAfterretrieveFuture(1);
		
		List<Message> decisiomm = null;
		try {
		//	decisiomm = msgs.completeAfter(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Fehler bei zukünftige Messages");;
		}
		
		Message decisionmsg= decisiomm.get(0);
		
		*/
	}
	public boolean Werfen() {
		Random r= new Random();
		int auswahl   = r.nextInt(2)+1;
		boolean gewonnen;
		if(auswahl==1) {
			gewonnen=true;
		}else {
			gewonnen=false;
		}
		
		return gewonnen;
	}

	
	public void Spiel(ReactionEmote rE) {
		
		//Reactions von der Spielauswahl entfernen
		List<Message>h=channel.getHistory().retrievePast(2).complete();
		for(int i =0; i<h.size(); i++) {
			h.get(i).clearReactions().complete();
		}
		
		
		boolean decision=true;
		if(rE.getName().equals("\u0031\u20E3")) {//Kopf
			decision=true;
		}else if(rE.getName().equals("\u0032\u20E3")) {//Zahl
			decision=false;
		}
	
			if(decision) {
				channel.sendMessage("Entscheidung registriert"+"\n"
						+ "<@"+von.getId()+">" +" bekommt Zahl" 
						).queue();
			}else {
				channel.sendMessage("Entscheidung registriert"+"\n"
						+ "<@"+von.getId()+">" +" bekommt Kopf" 
						).queue();
			}
			
			
			HashMap<String, Player> ar= new HashMap<String,Player>();
			ar=g.laden();
			Player vonp=ar.get(von.getName());
			Player gegenp= ar.get(gegen.getName());
			
			//etwas verzoegert um alles gut verfolgen zu koennen
			try {
				Thread.sleep(1000);
				channel.sendMessage("Muenze wird geworfen...").queue();
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(Werfen()==decision) {
				if(decision) {
					channel.sendMessage("Es ist Kopf zu sehen").queue();
				}else {
					channel.sendMessage("Es ist Zahl zu sehen").queue();
				}
				channel.sendMessage("<@"+gegen.getId()+">"+" hat gewonnen").queue();
				
				
				vonp.setLosses(vonp.getlosses()+1);
				gegenp.setWins(gegenp.getWins()+1);
				
			}else {
				if(decision) {
					channel.sendMessage("Es ist Zahl zu sehen").queue();
				}else {
					channel.sendMessage("Es ist Kopf zu sehen").queue();
				}
				channel.sendMessage("<@"+von.getId()+">"+" hat gewonnen").queue();
				
				
				vonp.setWins(vonp.getWins()+1);
				gegenp.setLosses(gegenp.getlosses()+1);
				
			}
			
			ar.put(von.getName(), vonp);
			ar.put(gegen.getName(), gegenp);
			
			g.speichern(ar);
				
			
			
		}
}
