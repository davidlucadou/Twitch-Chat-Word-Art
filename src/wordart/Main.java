package wordart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	final static String SENTINEL_VALUE = "<luna_moona> !stopWordArt"; // Feel free to change this to your liking.
	final static String IRC_MESSAGE_INDICATOR = "<"; // This is a value that is in front of all IRC user message,
													// and will not be mistaken for server messages.
													// Feel free to change this for your own IRC client.
	
	public static void main(String[] args) {
		boolean exited = false;
		boolean printedCurMsg = false;
		BufferedReader br;
		Robot r;
		String curMsg, nextMsg, prevMsg, channel, path;
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			r = null;
			e.printStackTrace();
		}
		
		curMsg = "";
		nextMsg = "";
		prevMsg = "";
		channel = "#monotonetim";
		
		path = System.getenv("APPDATA");
		path += "\\mIRC\\logs\\" + channel + ".log";
		while (!exited) {
			try {
				br = new BufferedReader(new FileReader(path));
				System.out.println("Reading from " + path);
				while (nextMsg != null) {
					curMsg = nextMsg;
					nextMsg = br.readLine();
				}
				
				if (!(prevMsg.equals(curMsg))
						&& prevMsg.startsWith(IRC_MESSAGE_INDICATOR)
						&& !(curMsg.equals(SENTINEL_VALUE))) {
					
				} else if (curMsg.equals(SENTINEL_VALUE)) {
					exited = true;
				} else {
					System.out.println("prevMsg is the same as curMsg, not printing out");
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unable to read from " + path);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException while reading from" + path);
				e.printStackTrace();
			}
			
			
			
			
			
			if (printedCurMsg) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep(1000) interrupted!");
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep(1000) interrupted!");
					e.printStackTrace();
				}
			}
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
	}
}
