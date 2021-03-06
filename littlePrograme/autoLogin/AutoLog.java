package autoLogin;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;

class Key{
	int event_key_value;
	public boolean need_shift;
	public Key(int event_key_value, boolean need_shift) {
		super();
		this.event_key_value = event_key_value;
		this.need_shift = need_shift;
	}
}
public class AutoLog {
	public static HashMap<Character, Key> hashmap=charToKeyMap();
	public static void main(String[] args) throws AWTException {
		// TODO Auto-generated method stub
		Robot robot=new Robot();
		robot.mouseMove(700, 400);
		robot.mousePress(KeyEvent.BUTTON1_MASK);
		robot.mouseRelease(KeyEvent.BUTTON1_MASK);
		String password="Czbank@35";
		passwordInput(robot, password);

	}
public static void passwordInput(Robot robot, String password){
		char[] pwd=password.toCharArray();
		for(int i=0; i<pwd.length; i++){
			Key key=hashmap.get(pwd[i]);
			if(key==null){
				System.out.println("key is not setted!");
			}
			if(key.need_shift){
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(key.event_key_value);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(key.event_key_value);
			}else{
				robot.keyPress(key.event_key_value);
				robot.keyRelease(key.event_key_value);
			}
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);			
	}
	
	public static HashMap<Character, Key> charToKeyMap() {
		// TODO Auto-generated method stub
		HashMap<Character, Key> hashmap=new HashMap<Character, Key>();
		//�����ַ�
		Key k=new Key(KeyEvent.VK_2, true);
		hashmap.put('@', k);
		
		//��ͨ�ַ�
		k=new Key(KeyEvent.VK_A, true);
		hashmap.put('A', k);
		k=new Key(KeyEvent.VK_B, true);
		hashmap.put('B', k);
		k=new Key(KeyEvent.VK_C, true);
		hashmap.put('C', k);
		k=new Key(KeyEvent.VK_D, true);
		hashmap.put('D', k);
		k=new Key(KeyEvent.VK_E, true);
		hashmap.put('E', k);
		k=new Key(KeyEvent.VK_F, true);
		hashmap.put('F', k);
		k=new Key(KeyEvent.VK_G, true);
		hashmap.put('G', k);
		k=new Key(KeyEvent.VK_H, true);
		hashmap.put('H', k);
		k=new Key(KeyEvent.VK_I, true);
		hashmap.put('I', k);
		k=new Key(KeyEvent.VK_J, true);
		hashmap.put('J', k);
		k=new Key(KeyEvent.VK_K, true);
		hashmap.put('K', k);
		k=new Key(KeyEvent.VK_L, true);
		hashmap.put('L', k);
		k=new Key(KeyEvent.VK_M, true);
		hashmap.put('M', k);
		k=new Key(KeyEvent.VK_N, true);
		hashmap.put('N', k);
		k=new Key(KeyEvent.VK_O, true);
		hashmap.put('O', k);
		k=new Key(KeyEvent.VK_P, true);
		hashmap.put('P', k);
		k=new Key(KeyEvent.VK_Q, true);
		hashmap.put('Q', k);
		k=new Key(KeyEvent.VK_R, true);
		hashmap.put('R', k);
		k=new Key(KeyEvent.VK_S, true);
		hashmap.put('S', k);
		k=new Key(KeyEvent.VK_T, true);
		hashmap.put('T', k);
		k=new Key(KeyEvent.VK_U, true);
		hashmap.put('U', k);
		k=new Key(KeyEvent.VK_V, true);
		hashmap.put('V', k);
		k=new Key(KeyEvent.VK_W, true);
		hashmap.put('W', k);
		k=new Key(KeyEvent.VK_X, true);
		hashmap.put('X', k);
		k=new Key(KeyEvent.VK_Y, true);
		hashmap.put('Y', k);
		k=new Key(KeyEvent.VK_Z, true);
		hashmap.put('Z', k);
		k=new Key(KeyEvent.VK_A, true);
		hashmap.put('a', k);
		k=new Key(KeyEvent.VK_B, true);
		hashmap.put('b', k);
		k=new Key(KeyEvent.VK_C, true);
		hashmap.put('c', k);
		k=new Key(KeyEvent.VK_D, true);
		hashmap.put('d', k);
		k=new Key(KeyEvent.VK_E, true);
		hashmap.put('e', k);
		k=new Key(KeyEvent.VK_F, true);
		hashmap.put('f', k);
		k=new Key(KeyEvent.VK_G, true);
		hashmap.put('g', k);
		k=new Key(KeyEvent.VK_H, true);
		hashmap.put('h', k);
		k=new Key(KeyEvent.VK_I, true);
		hashmap.put('i', k);
		k=new Key(KeyEvent.VK_J, true);
		hashmap.put('j', k);
		k=new Key(KeyEvent.VK_K, true);
		hashmap.put('k', k);
		k=new Key(KeyEvent.VK_L, true);
		hashmap.put('l', k);
		k=new Key(KeyEvent.VK_M, true);
		hashmap.put('m', k);
		k=new Key(KeyEvent.VK_N, true);
		hashmap.put('n', k);
		k=new Key(KeyEvent.VK_O, true);
		hashmap.put('o', k);
		k=new Key(KeyEvent.VK_P, true);
		hashmap.put('p', k);
		k=new Key(KeyEvent.VK_Q, true);
		hashmap.put('q', k);
		k=new Key(KeyEvent.VK_R, true);
		hashmap.put('r', k);
		k=new Key(KeyEvent.VK_S, true);
		hashmap.put('s', k);
		k=new Key(KeyEvent.VK_T, true);
		hashmap.put('t', k);
		k=new Key(KeyEvent.VK_U, true);
		hashmap.put('u', k);
		k=new Key(KeyEvent.VK_V, true);
		hashmap.put('v', k);
		k=new Key(KeyEvent.VK_W, true);
		hashmap.put('w', k);
		k=new Key(KeyEvent.VK_X, true);
		hashmap.put('x', k);
		k=new Key(KeyEvent.VK_Y, true);
		hashmap.put('y', k);
		k=new Key(KeyEvent.VK_Z, true);
		hashmap.put('z', k);
		k=new Key(KeyEvent.VK_0, true);
		hashmap.put('0', k);
		k=new Key(KeyEvent.VK_1, false);
		hashmap.put('1', k);
		k=new Key(KeyEvent.VK_2, false);
		hashmap.put('2', k);
		k=new Key(KeyEvent.VK_3, false);
		hashmap.put('3', k);
		k=new Key(KeyEvent.VK_4, false);
		hashmap.put('4', k);
		k=new Key(KeyEvent.VK_5, false);
		hashmap.put('5', k);
		k=new Key(KeyEvent.VK_6, false);
		hashmap.put('6', k);
		k=new Key(KeyEvent.VK_7, false);
		hashmap.put('7', k);
		k=new Key(KeyEvent.VK_8, false);
		hashmap.put('8', k);
		k=new Key(KeyEvent.VK_9, false);
		hashmap.put('9', k);
		
		return hashmap;
	}
	
}
