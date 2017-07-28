package wordart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	
	private static final String SENTINEL_VALUE = "<luna_moona> !stopWordArt"; // Feel free to change this to your liking.
	private static final String IRC_MESSAGE_INDICATOR = "<"; // This is a value that is in front of all IRC user message,
													// and will not be mistaken for server messages.
													// Feel free to change this for your own IRC client.
	private static final int APPROX_CENTER_X = 639;
	private static final int APPROX_CENTER_Y = 282;
	private static Robot r;
	
	public static void main(String[] args) {
		boolean stopRunning = false;
		boolean printedCurMsg = false;
		BufferedReader br;
		String curMsg, nextMsg, prevMsg, channel, path;
		
		try {
			r = new Robot();
		} catch (AWTException e) {
			r = null;
			e.printStackTrace();
		}
		
		//TODO: move file handling to separate method
		curMsg = "";
		nextMsg = "";
		prevMsg = "";
		channel = "#monotonetim";
		
		path = System.getenv("APPDATA");
		path += "\\mIRC\\logs\\" + channel + ".log";
		while (!stopRunning) {
			try {
				br = new BufferedReader(new FileReader(path));
				System.out.println("Reading from " + path);
				while (nextMsg != null) {
					curMsg = nextMsg;
					nextMsg = br.readLine();
					/* curMsg will never be null because nextMsg is always not null
					 * for the assignment statement to work, thus preventing
					 * NullPointerExceptions.
					 */
				}
				
				if (prevMsg.startsWith(IRC_MESSAGE_INDICATOR)
						&& !(prevMsg.equals(curMsg))
						&& !(curMsg.equals(SENTINEL_VALUE))) {
					Main.deletePrevMsg(prevMsg, curMsg);
					Styles wordArtStyle = Main.chooseStyle();
					Main.makeArt(wordArtStyle, curMsg);
					prevMsg = curMsg;
					printedCurMsg = true;
				} else if (!(curMsg.startsWith(IRC_MESSAGE_INDICATOR))) {
					System.out.println("curMsg does not start with the IRC message indicator, not printing out");
					//prevMsg = curMsg;
					/*
					 * I commented that out because server messages could cause duplicate
					 * messages to be printed on the screen.
					 * For example, sending the same message twice will give the error:
					 * "[Info] Your message was not sent because it is identical to the
					 * previous one you sent, less than 30 seconds ago."
					 * If I did prevMsg = curMsg, if the user sent the same message a 3rd time,
					 * the message would be printed despite being a duplicate because prevMsg
					 * would be the info message.
					 */
				} else if (prevMsg.equals(curMsg)) {
					System.out.println("prevMsg is the same as curMsg, not printing out");
				} else if (curMsg.equals(SENTINEL_VALUE)) {
					System.out.println("curMsg is the sentinel value, exiting");
					stopRunning = true;
				} else {
					System.out.println("curMsg is not being printed for an unknown reason. Debugging info:"
							+ "\ncurMsg=" + curMsg
							+ "\nprevMsg=" + prevMsg);
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unable to read from \"" + path + "\"");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException while reading from \"" + path + "\"");
				e.printStackTrace();
			}
			
			
			
			
			
			if (printedCurMsg) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep(1000) interrupted!");
					e.printStackTrace();
				}
				printedCurMsg = false;
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
	
	private static String getLatestMsg(String channel) {
		
		
		return "";
	}
	
	private static String getLatestMsg(String channel, String path) {
		
		
		
		return "";
	}
	
	private static Styles chooseStyle() {
		int rand = (int) (Math.random() * 15); // Range [0, 14]
		Styles[] allStyles = {Styles.One, Styles.Two, Styles.Three,
				Styles.Four, Styles.Five, Styles.Six, Styles.Seven,
				Styles.Eight, Styles.Nine, Styles.Ten, Styles.Eleven,
				Styles.Twelve, Styles.Thirteen, Styles.Fourteen, Styles.Fifteen};
		Styles style = allStyles[rand];
		return style;
	}
	
	private static void deletePrevMsg(String prevMsg, String curMsg) {
		if (prevMsg.equalsIgnoreCase("")) {
			return;
		} else {
			r.mouseMove(APPROX_CENTER_X, APPROX_CENTER_Y);
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			r.keyPress(KeyEvent.VK_DELETE);
			r.keyRelease(KeyEvent.VK_DELETE);
		}
	}
	
	private static void makeArt(Styles style, String curMsg) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		// Select style
		r.mouseMove(style.getX(), style.getY());
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		
		// Insert text
		clipboard.setContents(new StringSelection(curMsg), null);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
}
