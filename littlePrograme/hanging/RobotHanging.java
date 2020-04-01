package hanging;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.dom4j.ElementHandler;

public class RobotHanging {
	Timer t = null;
	Robot rb = null;
	
	public RobotHanging() {
		super();
		// TODO Auto-generated constructor stub
		try{
			rb = new Robot();
		}catch(AWTException e){
			e.printStackTrace();
		}
	}
	public void start(){
		t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				rb.mousePress(InputEvent.BUTTON1_MASK);
				rb.mouseRelease(InputEvent.BUTTON1_MASK);
				System.out.println(new Date());
				
			}
		}, 1000, 300000);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RobotHanging rh = new RobotHanging();
		rh.start();
		
	}

}
