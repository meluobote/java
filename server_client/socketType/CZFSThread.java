package socketType;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CZFSThread extends Thread {
    private static Logger log= LogManager.getLogManager().getLogger("b");
    private Socket clientSock;
    public static int MAC_LENGTH=64;

    public CZFSThread(Socket request) {
        this.clientSock=request;
    }

    @Override
    public void run() {
        log.info("=====================handle end=====================");
        DataInputStream dis=null;
        DataOutputStream dout=null;

        try {
            dis=new DataInputStream(new BufferedInputStream(clientSock.getInputStream()));

            byte[] bhead=new byte[8];
            dis.read(bhead);

            String length=new String(bhead);
            length=length.trim();
            int bodylen=Integer.valueOf(length)-MAC_LENGTH-23;
            log.info("total len: "+length);
            log.info("body len: "+bodylen);

            byte[] bh=new byte[15];
            int j=dis.read(bh);
            String mehead=new String(bh);
            if(j!=15){
                log.warning("msg head is wrong");
            }

            byte[] bBody=new byte[bodylen];
            j=dis.read(bBody);
            if(j!=bodylen){
                log.warning("receive body fail");
            }
            String recMsg=new String(bBody);
            log.info("receive body: "+recMsg);

            byte[] bMac=new byte[MAC_LENGTH];
            j=dis.read(bMac);
            String recmac=new String(bMac);
            log.info("MAC: "+recmac);

            String recbody=getbody(recMsg, "Voucher");
            byte[] bfinalBody= Base64.getDecoder().decode(recbody);
            String finalBody=new String(bfinalBody);
            log.info("body after decode: "+finalBody);

            //find MsgNo
            StringReader sr=new StringReader(recMsg);
            SAXReader saxReader=new SAXReader();
            Document doc= null;
            try {
                doc = saxReader.read(sr);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element root=doc.getRootElement();
            String requestMsgNo=root.element("HEAD").element("MsgNo").getTextTrim();
            log.info("MsgNo is "+requestMsgNo);
            String filename=null;
            if(requestMsgNo.equals("2601")){
                StringReader codesr=new StringReader(finalBody);
                SAXReader codesaxr=new SAXReader();
                try {
                    Document codeDoc=codesaxr.read(codesr);
                    Element coderoot= codeDoc.getRootElement();
                    String code=coderoot.element("PayCode").getTextTrim();
                    log.info("Paycode: "+code);
                    if(code.startsWith("P")){
                        filename="5601_pl.txt";
                    }else{
                        filename="5601_db.txt";
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }else{
                filename="0002.txt";
            }

            ClassLoader cl=CZFSThread.class.getClassLoader();
            InputStream is=cl.getResourceAsStream("server_client/socketType/"+filename);
            if(is!=null){
                SAXReader respReader=new SAXReader();
                Document respDoc=null;
                try {
                    doc= respReader.read(is);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                String msg=doc.asXML();
                log.info("return msg before encode: "+msg);
                msg=basecodexml(doc);
                log.info("return msg after encode: "+msg);

                byte[] sendBytes=msg.getBytes();
                String respTotalLen=String.valueOf(sendBytes.length+MAC_LENGTH);
                System.out.println("resp content body len: "+sendBytes.length);
                System.out.println("resp content total len: "+respTotalLen);

                String conntent_head="CNNONTAX"+"1.0"+"2001";
                String message= conntent_head+msg;

                String mac=String.format("%064d", 123);
                log.info("resp mac: "+mac);

                message=message+mac;
                int len=message.getBytes().length;
                len+=8;
                String lenmessage1=String.format("%1$-8s", len);

                message = lenmessage1+message;
                dout.write(message.getBytes());
                dout.flush();
                log.info("=====================handle end=====================");
            }




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getbody(String msg, String body) {
        String bodymsg=null, mark1=null, mark2=null;
        int beginIndex, endIndex;
        mark1="<"+body+">";
        mark2="</"+body+">";
        beginIndex=msg.indexOf(mark1)+body.length()+2;
        endIndex=msg.indexOf(mark2);
        bodymsg=msg.substring(beginIndex, endIndex);
        return bodymsg;
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
