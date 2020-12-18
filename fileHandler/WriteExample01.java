import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteExample01 {
    public static void copyFile(String srcFile, String dstFile) throws IOException {
        String s=ReadExample01.readFile(srcFile);

        BufferedWriter bw=new BufferedWriter(new FileWriter(dstFile));
        bw.write(s);
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        copyFile("fileHandler/t.txt", "fileHandler/t_bak.txt");
    }
}
