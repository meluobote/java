package autoLogin;

import java.awt.event.KeyEvent;

public class GenerateCode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		generateCode();
	}
	
	public static void generateCode(){
		char[] ca={'A'};
		for(;ca[0]<='Z';ca[0]++){
			System.out.println("k=new Key(KeyEvent.VK_"+new String(ca)+", true);");
			System.out.println("hashmap.put('"+new String(ca)+"', k);");
		}
		
		ca[0]='A';
		char[] caLower={'a'};
		for(;ca[0]<='Z';ca[0]++){
			System.out.println("k=new Key(KeyEvent.VK_"+new String(ca)+", true);");
			System.out.println("hashmap.put('"+new String(caLower)+"', k);");
			caLower[0]++;
		}
		
		ca[0]='0';
		for(;ca[0]<='9';ca[0]++){
			System.out.println("k=new Key(KeyEvent.VK_"+new String(ca)+", false);");
			System.out.println("hashmap.put('"+new String(ca)+"', k);");
		}
	}
}
