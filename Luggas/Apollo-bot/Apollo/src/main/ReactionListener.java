package main;


import java.util.List;
import game.Coinflip;
import game.Game;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class ReactionListener extends ListenerAdapter{

	private apollo_main am ;
	private	Message msg;
	private User gegner1;
	private User von;
	private Coinflip cf;
	
	public ReactionListener() {
		am = new apollo_main();
	}

	@Override //Gegner und von bereits in main belgen lassen? Message per ID bekommen?
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
		super.onMessageReactionAdd(event);
		MessageReaction.ReactionEmote mr= event.getReactionEmote();
//		System.out.println(event.getReactionEmote().getName());
//		System.out.println(event.getReactionEmote());
		//wenn das Emoji ein Kreuz oder Haken ist -> Anfrage fÃ¼r Battle 
		if((mr.isEmoji() && mr.getEmoji().equals("\u274E"))||(mr.isEmoji() && mr.getEmoji().equals("\u2705"))){
				
			
				//folgendes ist zur Battle - Funktion gehÃ¶rig
				List<Message> m = event.getChannel().getHistory().retrievePast(2).complete();
				
				von=m.get(1).getAuthor();
				
		//		System.out.println(event.getUser());//richtiger Spieler
		//		System.out.println(gegner1);//null
				try {
							
				gegner1 = m.get(1).getMentionedUsers().get(0);
				}catch (Exception e) {
				}
			if(!(gegner1==null)) {
				
			
				if(event.getUser().equals(gegner1)) {
					
					
					
					
					if(mr.isEmoji() && mr.getEmoji().equals("\u274E")){//nein
						event.getChannel().sendMessage("Spieler hat abgelehnt").queue();
						gegner1=null;
						
					}else if(mr.isEmoji() && mr.getEmoji().equals("\u2705")) {//ja
						
						Game g= new Game(event.getChannel(),event.getUser(),gegner1);
						
						List<Message>h=event.getChannel().getHistory().retrievePast(2).complete();
						for(int i =0; i<h.size(); i++) {
							h.get(i).clearReactions().complete();
						}
						event.getChannel().sendMessage("Spieler hat akzeptiert").queue();
						g.ChooseGame();
						
					}
					
					
					
				}else if(event.getUser().isBot()){
					
				}else {
					//event.getChannel().sendMessage("nur der Herausgeforderte kann entscheiden").queue();
				}
				
			}//"\u0031\u20E3"
		}else if(mr.isEmoji()&&(mr.getEmoji().equals("\u0031\u20E3")||mr.getEmoji().equals("\u0032\u20E3"))){//1
			//Ueberdenken der Ifs
			if(!(event.getUser().isBot())&& event.getUser().equals(von) && mr.getEmoji().equals("\u0031\u20E3")) {//Coinflip ausgewaehlt
				
				cf= new Coinflip(event.getChannel(), von, gegner1);
				cf.ChooseSide();
				// am Ende cf= null -> da dann Abfrage moeglich bei Entscheidung Kopf/Zahl als Reaction
			}else if(!(event.getUser().isBot())&&event.getUser().equals(gegner1)) {//das erste wurde bereits asugefuehrt daher cf belegt
				System.out.println("2");
				if(cf==null) {
					event.getChannel().sendMessage("der Herausforderer darf das Spiel aussuchen").queue();
				}else {
					cf.Spiel(event.getReactionEmote());
					cf=null;
					
				}
				
			}else if(!(event.getUser().isBot())&& event.getUser().equals(von) && mr.getEmoji().equals("\u0032\u20E3")){//2 --> sollte funktionen da von das game entscheidet
				//Rollen besitz keine weitere Entscheidung wie Coinflip daher ist dies so mgl.
				//wenn man nochmal ein game mit mehren Entscheidungen macht dann sollte man die IF-Bedingungen ueberarbeiten
				System.out.println("1");
				Roll r = new Roll(event.getChannel(),null);
				r.rollGame(event.getChannel(),von,gegner1);
				
			}
		
		}
		
		
		
	}
}
