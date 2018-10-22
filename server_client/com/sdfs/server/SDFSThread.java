package com.sdfs.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class SDFSThread extends Thread {
    Logger log = LogManager.getLogger(SDFSThread.class);
    Socket client = null;

    public SDFSThread(Socket request) {
        this.client = request;
    }

    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new BufferedInputStream((client.getInputStream())));
            byte[] head = new byte[8];
            dis.read(head);
            int bodyLen = Integer.parseInt(new String(head));
            log.info("�����ܳ��ȣ� " + (bodyLen + 8));
            log.info("�����峤��: " + bodyLen);

            SAXReader sr = new SAXReader();
            Document doc = null;

            //���յı��ĵ�ǰ8���ֽ���10���Ƶĳ��ȣ��������ı��ĵĳ��ȣ���������8���ֽڵı����� �������㣬�磬00000123
            byte[] body = new byte[bodyLen];
            dis.read(body);
            String bodyMsg = new String(body);
            //SAXReader�Ľ�����ʽ������ֱ��sr.read(dis), ���Ƿ��ֲ��ܽ��������� ��ӡ������һ���ʺţ������ʲô�� ��StringReader�ͺ���
            StringReader sReader = new StringReader(bodyMsg);
            doc = sr.read(sReader);
            log.info("���ձ��������ݣ�" + doc.asXML().trim());
            Element root = doc.getRootElement();
            String code = root.element("HEAD").attribute("CODE").getText().trim();
            log.info("���յ���������: " + code);
            String filename = "";
            if (code.equals("3001")) {
                filename = "3001.txt";
            } else {
                filename = "3009.txt";
            }

            ClassLoader cl = SDFSThread.class.getClassLoader();
            InputStream fileIs = cl.getResourceAsStream("D:\\code\\version\\java\\server_client\\com\\sdfs\\server\\3001.txt");
            SAXReader respSr = new SAXReader();
            Document respDoc = null;
            respDoc = respSr.read(fileIs);
            String respBodyMsg = respDoc.asXML().trim();
            byte[] respBodyByte = respBodyMsg.getBytes();
            int respBodyLen = respBodyByte.length;
            String respHead = String.format("%08d", respBodyLen);
            String respMsg = respHead + respBodyMsg;

            DataOutputStream respDos = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
            respDos.write(respMsg.getBytes());
            respDos.flush();
            System.out.println("�ɹ�����: " + new Date().toString());
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
