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
import wordart.Styles;

public class Main {
	
	private static final int MAX_LEN = 25; // Feel free to change this to your liking, just keep in mind
											// the text gets squished and deletion less reliable at higher
											// character counts.
											// Also, the max length on the website is 50 characters.
	private static final String SENTINEL_VALUE = "<luna_moona> !stopWordArt"; // Feel free to change this to your liking.
	private static final String IRC_MESSAGE_INDICATOR = "<"; // This is a value that is in front of all IRC user message,
													// and will not be mistaken for server messages.
													// Feel free to change this for your own IRC client.
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
		
		try {
			Thread.sleep(5000);
			// Give time to tab out of console
		} catch (InterruptedException e1) {
			System.out.println("Thread.sleep(5000) interrupted!");
			e1.printStackTrace();
		}
		
		curMsg = "";
		nextMsg = "";
		prevMsg = "";
		channel = "#monotonetim";
		
		path = System.getenv("APPDATA");
		path += "\\mIRC\\logs\\" + channel + ".log";
		while (!stopRunning) {
			curMsg = Main.getLatestMsg(path);
			
			if (curMsg.startsWith(IRC_MESSAGE_INDICATOR)
					&& !(curMsg.equals(prevMsg))
					&& !(curMsg.equals(SENTINEL_VALUE))) {
				Main.deletePrevMsg(prevMsg);
				Styles wordArtStyle = Main.chooseStyle();
				Main.makeArt(wordArtStyle, curMsg.substring(0, MAX_LEN));
				prevMsg = curMsg;
				printedCurMsg = true;
				System.out.println("DEBUG: Printed message");
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
			} else if (curMsg.equals(prevMsg)) {
				System.out.println("prevMsg is the same as curMsg, not printing out");
				System.out.println(prevMsg + "\n" + curMsg);
			} else if (curMsg.equals(SENTINEL_VALUE)) {
				System.out.println("curMsg is the sentinel value, exiting");
				stopRunning = true;
			} else {
				System.out.println("curMsg is not being printed for an unknown reason. Debugging info:"
						+ "\ncurMsg=" + curMsg
						+ "\nprevMsg=" + prevMsg);
			}
			
			if (printedCurMsg) {
				try {
					Thread.sleep(3000);
					//System.out.println("DEBUG: Sleeping for 3000 milliseconds");
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep(3000) interrupted!");
					e.printStackTrace();
				}
				printedCurMsg = false;
			} else {
				try {
					Thread.sleep(1000);
					//System.out.println("DEBUG: Sleeping for 1000 milliseconds");
				} catch (InterruptedException e) {
					System.out.println("Thread.sleep(1000) interrupted!");
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String getLatestMsg(String path) {
		BufferedReader br;
		
		String curMsg = "";
		String nextMsg = "";
		
		
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
			br.close(); // Close file handle
		} catch (FileNotFoundException e) {
			System.out.println("Unable to read from \"" + path + "\"");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException while reading from \"" + path + "\"");
			e.printStackTrace();
		}
		return curMsg;
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
	
	private static void deletePrevMsg(String prevMsg) {
		if (prevMsg.equalsIgnoreCase("")) {
			return;
		} else {
			for (int x = 620; x < 700; x += 2) {
				for (int y = 240; y < 300; y += 2) {
					r.mouseMove(x, y);
					r.mousePress(InputEvent.BUTTON1_MASK);
					r.mouseRelease(InputEvent.BUTTON1_MASK);
					
					r.keyPress(KeyEvent.VK_DELETE);
					r.keyRelease(KeyEvent.VK_DELETE);
					/*try {
						Thread.sleep(10);
						// I put this in to help make sure I wasn't overloading my VM, but now I wonder
						// if it actually does anything helpful.
					} catch (InterruptedException e) {
						System.out.println("Thread.sleep(10) interrupted!");
						e.printStackTrace();
					}*/
				}
			}
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
