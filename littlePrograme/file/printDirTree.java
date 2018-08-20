package file;

import java.io.File;

public class printDirTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file=new File("D:\\Docu");
		printFile(file);
	}
	public static void printFile(File file){
		printFile0(file, 0);
	}
	public static void printFile0(File file, int spaces){
		print(spaces, file.getAbsolutePath());
		if(file.isDirectory()){
			 File[] files=file.listFiles();
			 for(File f:files){
				 printFile0(f, spaces+1);
			 }
		}
	}
	
	public static void print(int spaces, String filePath){
		for(int i=0;i<spaces;i++){
			System.out.print("\t");
		}
		System.out.println(filePath);
	}
}
