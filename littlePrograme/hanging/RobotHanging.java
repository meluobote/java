package hanging;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RobotHanging {
	Timer t = null;
	Robot rb = null;
	
	public RobotHanging() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
		rb = new Robot();
	}
	public void start(){
		t = new Timer();
		t.schedule(new TimerTask() {

			public void run() {
				// TODO Auto-generated method stub
				rb.mousePress(InputEvent.BUTTON1_MASK);
				rb.mouseRelease(InputEvent.BUTTON1_MASK);
				System.out.println(new Date());
				
			}
		}, 1000, 300000);
	}

	public static void main(String[] args) throws AWTException {
		// TODO Auto-generated method stub
		RobotHanging rh = new RobotHanging();
		rh.start();
		
	}

}
