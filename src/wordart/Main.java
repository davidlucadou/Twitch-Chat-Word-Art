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
		
		r.mouseMove(200, 400);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
