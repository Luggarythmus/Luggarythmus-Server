package main;


import java.security.PermissionCollection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Random;


import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import game.Game;
import guildmanage.Guild_Manager;
import guildmanage.Moving;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Invite.Channel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import schedule.Scheduler;





public class apollo_main extends ListenerAdapter {

	public Member battlegegner;
	public List<Message> msg=new  ArrayList<Message>();
	public Scheduler s = new Scheduler();
	//public List<Channel> chlist= new ArrayList<Channel>();
	public static void main(String[] args) {
		
		
		try {
		
			
			
			JDABuilder.createLight("ODQ4OTE5ODEzNDI1NDYzMzE2.YLToLw.wnJP4YkhuYrVX5N_Vk0Igud4IIw",GatewayIntent.GUILD_MESSAGES,GatewayIntent.DIRECT_MESSAGES,
					GatewayIntent.GUILD_VOICE_STATES,GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MEMBERS).
					addEventListeners(new apollo_main()).setActivity(Activity.watching("dich an!")).
					addEventListeners(new Guild_Manager()).
					addEventListeners(new ReactionListener()).build();
			
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		
	}
		 	
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Message msg = event.getMessage();
		MessageChannel channel1; 
		try {
		if (msg.getContentRaw().equals("!ping")){
			 channel1 = event.getChannel();
			channel1.sendMessage("Pong!").queue();
		
		}else if(msg.getContentRaw().equals("!time")){
			 channel1 = event.getChannel();
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy ");
			 channel1.sendMessage(""+ dtf.format(LocalDateTime.now())).queue();
			 
				
		}else if(msg.getContentRaw().substring(0, 5).equals("!Rick")) {
			Rick rick=new Rick(event.getChannel());
		
		}else if(msg.getContentRaw().substring(0, 5).equals("!lama")) {
			Lama lama= new Lama(event.getChannel());
			
		}else if(msg.getContentRaw().substring(0, 5).equals("!help")) {
			helppage h = new helppage();
			try {
				//h.fuellen();
				event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(""+h.getHM())).queue();	
			}catch(UnsupportedOperationException e) {
				System.out.println("UnsupportedOperationException aufgetreten (!help)");
			}
			
	
		
		}else if(msg.getContentRaw().substring(0, 6).equals("!clear")) {
			TextChannel now = event.getTextChannel();
			try {
						
			boolean amloeschen=true;
			
			while(amloeschen) {
				List<Message> his= now.getHistory().retrievePast(50).complete();
				his.removeIf(m->m.getTimeCreated().isBefore(OffsetDateTime.now().minus(2,ChronoUnit.WEEKS)));
				
				if(his.isEmpty()) {
					TextChannel history = event.getGuild().getTextChannelsByName("history", true).get(0);
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy ");
					amloeschen=false;
					history.sendMessage(now.getName()+" am "+dtf.format(LocalDateTime.now())+" gecleared").queue();
					return;
					
				}
				 now.deleteMessages(his).complete();
			}
			}catch(Exception e) {
				now.sendMessage("keine Nachrichten vorhanden oder Nachrichten aelter als 2 Wochen nicht geloescht").queue();
			}
		}else if(msg.getContentRaw().substring(0,5).equals("!roll")) {
				
				channel1 = event.getChannel();
				Roll r = new Roll(channel1, msg);
	
		}else if(msg.getContentRaw().substring(0, 5).equals("!move")) {// Idee: Channel des Servers in Liste packen,durch diese Iterieren und dann Member eines Channels bekommen
			try {
				Moving  m = new Moving();
				String name=msg.getContentRaw().substring(6,msg.getContentRaw().length());
				Guild alle = event.getGuild();
				
				System.out.println(event.getGuild().getVoiceStates());	
			
			
				VoiceChannel ziel=event.getGuild().getVoiceChannelsByName(name, true).get(0);
				
				VoiceChannel quelle;
				Member benutzer= event.getMember();
				System.out.println(benutzer.getEffectiveName());
				System.out.println(benutzer.getGuild().getAudioManager().getConnectedChannel());
				System.out.println(benutzer.getGuild().getAudioManager().getConnectionStatus());
			
				
				
				
			
			}catch(IndexOutOfBoundsException ex){
				event.getChannel().sendMessage("pls name an existing channel").queue();
			}
			
			
			
		}else if(msg.getContentRaw().substring(0,5).equals("!plan")) {
				s.aktualisieren();
				//	event.getChannel().sendMessage("Anfrage bekommen").queue();
					String name=event.getMessage().getContentRaw().substring(6,event.getMessage().getContentRaw().indexOf("?"));
					String zeit=event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("?")+3,event.getMessage().getContentRaw().length());
					//+2 da sonst / und Parameter f�r Zeit mitgenommen wird
					System.out.println(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("?")+1,event.getMessage().getContentRaw().indexOf("?")+2));
					if(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("?")+1,event.getMessage().getContentRaw().indexOf("?")+2).equals("d")) {
				
					//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter dtf2= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); //damit genau an das Event errinnert wird, werden Sekunden mit angegeben
					LocalDateTime ziel=LocalDateTime.parse(zeit,dtf2);
					s.schedule(event.getChannel(),name,ziel);
					
					System.out.println("Now " +dtf2.format(LocalDateTime.now()));
					System.out.println("dann "+ziel);
				}
			 
					 
//					if(event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("/")+1,event.getMessage().getContentRaw().indexOf("/")+2).equals("w")){
//						
//					}
					 
					 
					
					System.out.println("Eingabe " +zeit);
					System.out.println(name);
				//	System.out.println(event.getMessage().getContentRaw().indexOf("/"));
			
	/*
	 * Code fuer Audioausgabe -> erstmal nicht benoetigt da anderer bot vorhanden
	 * 	}else if(msg.getContentRaw().substring(0,5).equals("!sing")) {
		channel1=event.getChannel();
		if(event.getGuild().getSelfMember().getVoiceState().getChannel()==(null)) {
			channel1.sendMessage("I need to join a channel first").queue();
			return;
		}
		
		AudioManager audio = 	event.getGuild().getAudioManager();
		VoiceChannel v= event.getGuild().getSelfMember().getVoiceState().getChannel();
		audio.openAudioConnection(v);
		
		
		AudioPlayerManager manager= new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(manager);
		AudioPlayer p=manager.createPlayer();
		TrackScheduler ts= new TrackScheduler();
		p.addListener(ts);
		String identifier = msg.getContentRaw().substring(6, msg.getContentRaw().length());
		
		manager.loadItem(identifier, new AudioLoadResultHandler() {
			
			@Override
			public void trackLoaded(AudioTrack track) {
				ts.queue(track);
				p.playTrack(track);
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				  for (AudioTrack track : playlist.getTracks()) {
				      ts.queue(track);
				  }
			}
			
			@Override
			public void noMatches() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void loadFailed(FriendlyException exception) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		}else if(msg.getContentRaw().substring(0,5).equals("!join")) {//arbeitet noch nicht wie geplant
			channel1=event.getChannel();
			try {
					
				String name=msg.getContentRaw().substring(6,msg.getContentRaw().length());
				
				VoiceChannel voice;
				Guild hoerer=event.getGuild();
				if(event.getGuild().getSelfMember().getVoiceState().getChannel()==(null)) {
					voice =hoerer.getVoiceChannelsByName(name, true).get(0);	
				}else {
					channel1.sendMessage("I am already in a channal").queue();
					return;
				}
			
				
				//voice state bei einzelnen Nutzer gibt immer null
				
				if(voice == null) {
					channel1.sendMessage("Please join a voice channel").queue();
					return;
				}
				AudioManager audio = 	event.getGuild().getAudioManager();
				if(audio.isAttemptingToConnect()) {
					channel1.sendMessage("already trying to connect").queue();
				}			
				audio.openAudioConnection(voice);
				channel1.sendMessage("connected!").queue();
				}catch(IndexOutOfBoundsException ex){
					channel1.sendMessage("pls name an existing channel").queue();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
							
			}else if(msg.getContentDisplay().substring(0,6).equals("!leave")) {
				channel1=event.getChannel();
				try {
					
				
				Guild hoerer=event.getGuild();
				//VoiceChannel voice =hoerer.getVoiceChannelsByName("Test", true).get(0);
				VoiceChannel voice =event.getGuild().getSelfMember().getVoiceState().getChannel();
				if(voice == null) {
					channel1.sendMessage("not connected with a channel").queue();
					return;
				}
				event.getGuild().getAudioManager().closeAudioConnection();
				channel1.sendMessage("disconnected!").queue();}
				catch(Exception e) {
					e.printStackTrace();
				}
			
				
			
			*/
				 
			}else if(msg.getContentRaw().substring(0,7).equals("!battle")) {
				List<Member> m = msg.getMentionedMembers();

					if (m.isEmpty()) {
						if(msg.getContentRaw().substring(8, msg.getContentRaw().length()).equals("-stats")) {
							Game g = new Game(event.getChannel(),event.getAuthor(),null);
							g.stats(event.getAuthor());
						}else {
							event.getChannel().sendMessage("bittt gebe einen Parameter an").queue();
						}
						
					}else {	
						
						event.getChannel().sendMessage("Akzeptierst du die Anfrage?").queue(message-> {
						//https://github.com/BreadMoirai/DiscordEmoji/blob/master/src/main/java/com/github/breadmoirai/Emoji.java
							message.addReaction("\u2705").queue();//ja
							message.addReaction("\u274E").queue();//nein
							
						});
						TextChannel t= event.getTextChannel();
					
						
						if(!this.msg.isEmpty()) {
							this.msg.clear();
						}
						
						this.msg.addAll(t.getHistory().retrievePast(1).complete());
						
					}
				
				
				
			}else if(msg.getContentRaw().substring(0,7).equals("!insult")) {
				
				Insult i = new Insult();
				i.createInsult();
			}else if(msg.getContentRaw().substring(0,8).equals("!request")) {
			//395937614735212546
			
			channel1 = event.getChannel();
			
			
			try {
				//eingegebenen Text splitten 
				//zu bannenden Spieler mention und dann als Elemente der Liste ausgeben
				String gegen="";
				String namenlaenge="";
				//gegen=Antragsteller(msg);
				List<Member> m = msg.getMentionedMembers();
				//	System.out.println(msg);
				
				//	System.out.println(m);//debugging
				int i=0;
					for(;i<m.size();i++) {
					
						gegen +="<@"+m.get(i).getId()+">"+ " "; //<> werden bei laenge niccht mitgezählt
																//Ids sind immer gleich lang -> immer gleiche Laenge von Gegen trotz unterschiedlicher Namenslaenge
						namenlaenge+=m.get(i).getEffectiveName()+" ";
					}
				
					
				//Bansteller
				String von = ""+msg.getAuthor().getId();
				//Grund 
																												  
				String reason=msg.getContentDisplay().substring(9+namenlaenge.length()+i,msg.getContentDisplay().length());//es wird von der gesamten msg genommen deswegen wird 
																													//wird an der falschen Stelle angefangen
//				
				
				
				//Zeitpunkt des Requests
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy ");
				//channel auswahl 
				TextChannel test = event.getGuild().getTextChannelsByName("test",true).get(0);
				//final msg zusammensetzen
				test.sendMessage("<@395937614735212546>"+" Request von "+"<@"+von+">"+ "\n at "+ dtf.format(LocalDateTime.now())+
									"gegen "+gegen+" \nGrund: "+reason).queue();
				//Best�tigungsnachricht 
				channel1.sendMessage("Request received").queue();
			
				
				
			}catch(Exception e) {
				channel1.sendMessage("Fehler: Request not received").queue();
				e.printStackTrace();
			}
		}
		
		}catch(StringIndexOutOfBoundsException x) {
			System.out.println("String out of Bounds bei einer If");
		}
		
	}
	
	
	
	
	
	
	
	
	public Member getBattlegegner() {
		return battlegegner;
	}
	public List<Message> getMsg() {
		return msg;
	}
	
	

}

	
