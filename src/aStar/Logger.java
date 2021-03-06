package aStar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * Einfacher Logger fuer die Einheitliche-Ausgabe von Nachrichten
 * 
 * @author Julian Pritzi und Jonas Zöschg
 *
 */
public class Logger {
	
	public enum Level{
		DEBUG(4), INFO(3), WARNING(2), ERROR(1);
		
		public final int value;
	    private Level(int value) {
	        this.value = value;
	    }
	    @Override
	    public String toString() {
	    	String s = super.toString();
	    	while(s.length() < 7) {
	    		s = s + " ";
	    	}
	    	return s;
	    }
	}
	
	private static Level level = Level.INFO;

	public static Level getLevel() {
		return level;
	}
	
	public static void setLevel(Level level) {
		Logger.level = level;
	}
	
	/**
	 * 
	 * gibt die Nachricht mit Zeitstempel aus wenn das level für die Ausgaben richtig gesetzt wurde
	 * 
	 * @param level wichtigkeit der Nachricht
	 * @param s ANchricht
	 */
	public static void log(Level level, String s) {
		if(level != null && level.value <= Logger.level.value) {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss SSS");
			Date date = new Date();
			System.out.println(dateFormat.format(date) + " | " + level + " - " + s);
		}
	}
}
