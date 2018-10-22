package com.me.server.zip;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class Client {
    private static final String DEFAULTZIPENTRYNAME = "default.xml";

    public static void main(String[] args) throws Exception {
        sendMessage();
    }

    public static void sendMessage() throws Exception {
        StringBuffer sendStr = new StringBuffer();
        sendStr.append("<?xml version=\'1.0\' encoding=\'GBK\'?>");
        sendStr.append("<YSXX>");
        sendStr.append("<LOGIN>");
        sendStr.append("<USERNAME>201001</USERNAME>");
        sendStr.append("<PASSWORD>123</PASSWORD>");
        sendStr.append("</LOGIN>");
        sendStr.append("<LIST>");
        sendStr.append("<JKS>");
        sendStr.append("<PJLX>11</PJLX>");
        sendStr.append("<PJHM>123456</PJHM>");
        sendStr.append("</JKS>");
        sendStr.append("</LIST>");
        sendStr.append("</YSXX>");

        System.out.println("���͵ı��£� " + sendStr);

        //����
        try {
            //���ӱ��µĵ�ַ
            String content = Base64.encode(sendStr.toString());
            InputStream is;
//			URL uploadServlet = new URL("http://jkldsjfls"); 
//			HttpURLConnection servletConnction= (HttpsURLConnection) uploadServlet.openConnection();

            //�������ӵĲ���
//			servletConnction.setRequestProperty("content-type", "text/html");
//			servletConnction.setRequestMethod("POST");
//			servletConnction.setDoOutput(true);
//			servletConnction.setDoInput(true);
//			servletConnction.setUseCaches(false);

            Socket s = new Socket("127.0.0.1", 18010);
            //�������� д��XML����
//			OutputStream output=servletConnction.getOutputStream();
            OutputStream output = s.getOutputStream();
            ZipUtil zu = new ZipUtil();
            zu.compress(output, content);

            output.flush();
            output.close();

            //��ȡ���ص�����
//			is = servletConnction.getInputStream();
//			System.out.println(is.available());
//			System.out.println(servletConnction.getResponseCode());
            is = s.getInputStream();

            Map map = zu.decompressToString(is);
            String unzipstr = (String) map.get(DEFAULTZIPENTRYNAME);
            String backstr = Base64.decode(unzipstr);

            System.out.println("���շ���ֵ��" + backstr);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//		Socket s
    }
}
