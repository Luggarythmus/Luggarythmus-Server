package guildmanage;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;

import game.Game;
import game.Player;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberUpdateEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import schedule.Ereignis;
import schedule.Scheduler;


public class Guild_Manager extends ListenerAdapter{

	
	public Guild_Manager() {
	
	}
	
	
		
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		//https://github.com/DV8FromTheWorld/JDA/releases/tag/v4.0.0
		
		System.out.println("Test");
		Role r = event.getGuild().getRoleById("897198698998661192");//Legende   ---> Test Server
																	//694946995651084360     --> Glaeubige - Rolle - ID 
		event.getGuild().addRoleToMember(event.getMember(), r).queue();;

		
		super.onGuildMemberJoin(event);
	
		
	}
	
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {//voice channel join --> funktioniert zur Zeit nur fuer Bot
		System.out.println("Popo");
		super.onGuildVoiceJoin(event);
	}
	
	@Override
	public void onGuildVoiceMute(GuildVoiceMuteEvent event) {
	System.out.println("Papa");
		super.onGuildVoiceMute(event);
	}
	/*   Zaehlen der VoiceChannel-Zeit eines Users
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {  //------> testen erforderlich
		
		Member m = event.getMember();
		VoiceChannel voice=event.getChannelJoined();
		System.out.println("Time:" +m.getTimeJoined());//wann hat der Member den Server betreten
//		long joinTime=System.currentTimeMillis();
//		Integer Zeit=0;
//		
		
			
			
			//Spieler mitteils einer ArrayList speichern -> nur wenn noch keine Statistik vorhanden ist
			
			Runnable tick = new Runnable() {
			
			@Override
			public void run() {
				try {
					HashMap<String,Player>ar= new HashMap<String,Player>();
					Player p= new Player(m.getEffectiveName());
						
							
						ar=laden();
					
						System.out.println(ar);
						if(!(ar.containsKey(p.getname()))) {
							
							ar.put(p.getname(), p);
						}
						
					while(event.getMember().getVoiceState().getChannel()!=null) {
						Thread.sleep(1000);
						p.settimeVoice(p.gettimeVoice()+1000);//Millisekunden?
						speichern(ar);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}
			}
			
			
			
			
		};
		tick.run();
		
		super.onGuildVoiceJoin(event);
	}

	
/*
	@Override
	public void onGuildMemberUpdate(GuildMemberUpdateEvent event) {//Rollenupdate, Profilbildupdate
		System.out.println("Test2");
		super.onGuildMemberUpdate(event);
	}
	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		System.out.println("nein");
		super.onGuildJoin(event);
	}
	
	@Override
	public void onRoleCreate(RoleCreateEvent event) {
		System.out.println("Pipi");
		super.onRoleCreate(event);
	}
	@Override
	public void onGenericGuildMessage(GenericGuildMessageEvent event) {//wenn nachricht des joinens Kommt
		System.out.println("Pi");
		
		
		
		super.onGenericGuildMessage(event);
	}
*/
	
	public void speichern(HashMap<String,Player> ar) {
		FileOutputStream fos=null;
		ObjectOutputStream oos=null;
		
		try {
		//	fos= new FileOutputStream("/home/luggas/Schreibtisch/Discord_Bot/registrierteSpielert.txt"); -> Linux
			String s = Paths.get("").toAbsolutePath().toString();
			fos=new FileOutputStream(s+"/src/registrierteSpieler.txt");
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
			fis=new FileInputStream(s+"/src/registrierteSpieler.txt");
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

}
