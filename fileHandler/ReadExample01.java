import java.io.*;

public class ReadExample01 {
    /**
     * FileReader can't design file's encoding, so using InputStreamReader
     */
    public static String readFile(String filename) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));

        String tmp;
        StringBuilder sb=new StringBuilder();
        while((tmp=br.readLine())!=null){
            sb.append(tmp);
            sb.append('\n');
        }
        return sb.toString();
    }
    public static void main(String[] args) throws IOException {
        String ret=readFile("fileHandler/t.txt");
        System.out.println(ret);
    }
}
