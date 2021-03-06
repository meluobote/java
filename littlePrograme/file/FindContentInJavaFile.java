package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FindContentInJavaFile {
	/**
	 * 获得目录下java文件中含有指定内容的java文件 或者直接
	 * 查找该文件是否有指定内容，如果有那么打印该文件名
	 * @param args
	 * @throws IOException 
	 */		
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file=new File("D:\\Docu\\java\\jxl\\jxl_src");
		System.out.println("start");
		FileWriter fw=new FileWriter("ret.txt");
		fw.close();
//		findContent(file, "implements Sheet");
		findContent(file, "class SheetReader");
	}
	
	public static void findContent(File file, String content) throws IOException{
		if(file.isDirectory()){
			File[] files=file.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File arg0) {
					// TODO Auto-generated method stub
					String filename=arg0.getName();
					if(filename.endsWith(".java")){
						return true;
					}
					//过滤隐藏文件夹但是不过滤普通文件夹
					else if(filename.startsWith(".")){
						return false;
					}else if(arg0.isDirectory()){
						return true;
					}
					return false;
				}
			});
			
			for(File f:files){
				findContent(f, content);
			}
		}else{
			findContentInFile(file, content);
		}
	}
	
	public static void findContentInFile(File file, String content) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(file));
		String line=null;	
		BufferedWriter bw=new BufferedWriter(new 
				OutputStreamWriter(new FileOutputStream(new File("ret.txt"), true)));
		while((line=br.readLine())!=null){
			int index=line.indexOf(content);
			if(index!=-1){
				System.out.println(file.getAbsolutePath());
				bw.write(file.getAbsolutePath()+": "+line);
				bw.newLine();
				bw.flush();
				bw.close();
				br.close();
				break;
			}
		}
	}
}
