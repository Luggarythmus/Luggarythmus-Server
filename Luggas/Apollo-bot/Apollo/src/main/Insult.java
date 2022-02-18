package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Insult {
	final String usrAgent ="Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:93.0) Gecko/20100101 Firefox/93.0";
	
	public void createInsult() {
		System.out.println("!1");
		try {
			URL url = new URL("https://www.merriam-webster.com/words-at-play/top-10-rare-and-amusing-insults-vol-1");
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("User-Agent", usrAgent);
			System.out.println(conn.getContent());
		
		/*	BufferedReader in = new BufferedReader(new InputStreamReader(
		               conn.getInputStream()));
		         String str;
		         StringBuilder builder = new StringBuilder(1024);
		         while ((str = in.readLine()) != null) {
		            builder.append(str);
		            builder.append("\n"); //Formatierung
		         }
		System.out.println(builder);*/
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
