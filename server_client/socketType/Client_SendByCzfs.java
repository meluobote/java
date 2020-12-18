package socketType;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Client_SendByCzfs {
    private static Logger log= LogManager.getLogManager().getLogger("a");
    private static int MAC_LENGTH=64;

    public static void main(String[] args) {
        final String[] fArgs=new String[]{"5000"+".txt", "172.168.253.86", "49129"};

        for (int ti = 0; ti < 1; ti++) {
            Thread t=new Thread(){
                @Override
                public void run() {
                    for(int j=0;j<1;j++){
                        String fileName=fArgs[0];
                        ClassLoader cl= Client_SendByCzfs.class.getClassLoader();
                        String msg="";
                        SAXReader reader=new SAXReader();
                        Document doc=null;
                        InputStream is=cl.getResourceAsStream("socketType/"+fileName);
                        try {
                            doc= reader.read(is);
                            System.out.println(doc.asXML().trim());
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        System.out.println("doc before encode: "+doc.asXML().trim());
                        String bodyMsg=basecodexml(doc);
                        System.out.println("doc after encode: "+doc.asXML().trim());

                        String messageHeadExceptLen="CNNONTAX"+"1.0"+"2001";
                        String mac=String.format("%064", 123);

                        int totalLen=23+ bodyMsg.length()+64;
                        String lenInHead=String.format("%1$-8s", totalLen);
                        msg=lenInHead+messageHeadExceptLen+bodyMsg+mac;

                        if(msg!=null && msg.length()>0){
                            log.info("send content: "+msg);
                            for(int i=0;i<1;i++){
                                long startTime=System.currentTimeMillis();
                                log.info(String.format("test [%d]th times", i+1));
                                try {
                                    connectSocket(fArgs[1], Integer.parseInt(fArgs[2]), msg);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                long endTime=System.currentTimeMillis();
                                System.out.println("running time: "+(endTime-startTime+"ms"));
                                log.info("\n\n\n\n");
                            }
                        }
                    }
                }
            };
        }
    }

    private static String connectSocket(String ip, int port, String sendmsg) throws IOException {
        Socket sock=null;
        String recMsg=null;
        try {
            sock=new Socket(ip, port);
            log.info("Connected success");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataOutputStream dout=new DataOutputStream(sock.getOutputStream());
        DataInputStream din=new DataInputStream(sock.getInputStream());

        String msg=sendmsg;
        byte[] sendBytes=msg.getBytes();
        log.info(new Date().toString()+"  send len: "+ sendBytes.length);
        dout.write(sendBytes);
        sock.setSoTimeout(200000);

        byte[] bBody=new byte[1024*512];
        int j=din.read(bBody);
        recMsg=new String(bBody);
        log.info(new Date().toString() + "recMsg: "+recMsg);

        dout.close();
        din.close();
        sock.close();
        return recMsg;
    }

    private static String basecodexml(Document doc) {
        Element rootEmt=doc.getRootElement();
        System.out.println(rootEmt.getTextTrim());
        Element voucherCount=rootEmt.element("MSG").element("MOF").element("VoucherCount");

        int vCountValue=Integer.valueOf(voucherCount.getTextTrim());

        List<Element> voucherEles = rootEmt.element("MSG").element("MOF").
                    element("VoucherBody").elements("Voucher");

        for(Element voucherEle: voucherEles){
            String str=voucherEle.asXML().toString().trim();
            String body=str.substring(9, str.length()-10);

            String encodeBase64= null;
            try {
                encodeBase64 = Base64.getEncoder().encodeToString(str.getBytes("GBK"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("fore encode: [" + str +"]");
            System.out.println("after encode: ["+encodeBase64+"]");

            voucherEle.clearContent();
            String str1=voucherEle.asXML().toString();
            voucherEle.setText(encodeBase64);
        }
        String returnMsg=doc.asXML();
        returnMsg=returnMsg.trim();
        return returnMsg;
    }


}
