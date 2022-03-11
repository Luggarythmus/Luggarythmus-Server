package schedule;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.MessageChannel;

public class Ereignis implements Serializable{
	private final ScheduledExecutorService scheduler =  Executors.newScheduledThreadPool(1);
	private Duration zeitpunkt;
	private String name;
	private MessageChannel channel;
	
	public Ereignis(String name) {
		
		this.name=name;
		
	}
	public void wielange(LocalDateTime zeit) {
		DateTimeFormatter dtf2= DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String strtoday=dtf2.format(LocalDateTime.now());
		LocalDateTime dttoday=LocalDateTime.parse(strtoday,dtf2);
		
		Duration dif= Duration.between(dttoday, zeit);
		System.out.println(dif.getSeconds());
		this.zeitpunkt=dif;
		
	}
	public void sendMsg() {
		 
		try {
			
			Runnable send = new Runnable() {
				
				@Override
				public void run() {
					channel.sendMessage(name + "passiert jetzt").queue();
				//nach Zeitpunkt des Events wird das Event aus der Liste geloescht
					Scheduler s = new Scheduler();
					
					HashMap<String, Ereignis> hs = s.laden();
					hs.remove(name);
					s.speichern(hs);
				}
			};
			
			
			ScheduledFuture<?> sf = scheduler.schedule(send, zeitpunkt.getSeconds() , TimeUnit.SECONDS);
			
			
			
		}catch(Exception e){
			
		}finally {
			scheduler.shutdown();
		}

	}
	public void StopScheduler() {
		try {
			scheduler.shutdown();
			System.out.println("stopped");
		}catch(Exception e) {
			
		}
	}
	
	public Duration getDif() {
		return zeitpunkt;
	}
	public void setZeitpunkt(Duration zeitpunkt) {
		this.zeitpunkt = zeitpunkt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setChannel(MessageChannel channel) {
		this.channel=channel;
	}
}
