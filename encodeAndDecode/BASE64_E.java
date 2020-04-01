//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.binary.Base64;
//说明： Base64来自： lib/commons-codec-1.14.jar这个jar包
public class BASE64_E {
    public static void main(String[] args) {
        String str="12345";
        String encodeStr= new String(Base64.encodeBase64(str.getBytes()));
        byte[] decodeBytes=Base64.decodeBase64(encodeStr);
        String decodeStr=new String(decodeBytes);
        System.out.println("str: "+str);
        System.out.println("encodestr: "+encodeStr);
        System.out.println("decodestr: "+decodeStr);
    }
}
