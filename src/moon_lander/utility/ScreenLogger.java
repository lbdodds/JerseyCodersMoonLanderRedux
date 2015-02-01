package moon_lander.utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that draws messages to the top left hand side of the screen
 * @author Liam Dodds
 *
 */
public class ScreenLogger {
	private static Boolean on = false;
	private static List<String> messages = new ArrayList<>();
	private static final int offset = 15;
	
	/**
	 * Activates the screen logger
	 */
	public static void turnOn() {
		on = true;
	}
	
	/**
	 * Adds a message to the queue
	 * @param message
	 */
	public static void add(String message) {
		messages.add(message);
	}
	
	/**
	 * Flushes all the messages to the screen
	 * @param g2d
	 */
	public static void flush(Graphics2D g2d) {
		int xOffset = 5;
		int yOffset = offset;
		for (String message : messages) {
			g2d.setColor(Color.white);		
	        g2d.drawString(message, xOffset, yOffset);
	        
	        yOffset += offset;
		}
		
		messages.clear();
	}	
}
