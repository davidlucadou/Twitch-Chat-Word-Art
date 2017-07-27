package wordart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Main {
	
	public static void main(String[] args) {
		Robot r;
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			r = null;
			e.printStackTrace();
		}
		
		/*r.mouseMove(200, 400);
		r.mousePress(InputEvent.BUTTON1_MASK); //proof of concept test code
		r.mouseRelease(InputEvent.BUTTON1_MASK);*/
		
		// loop
		
		// poll for log file, get last message
			// make in separate method that returns string
		
		// check if newest message is irc chat message or info message
		
		// if irc, check if it's the same as last message
		
		// if not the same, gen rand # [0, 14], assign to a Styles enum for its type, copy to clipboard
		
		// move mouse to x/y for approximate center of wordart and press delete
			// put into separate method
		
		// move mouse to x/y in enum
		// press and release
		// paste text
		// press enter
			/// put into separate method
		
		// sleep 100 ms
		
		// 
		
		
		
		
	}
}
