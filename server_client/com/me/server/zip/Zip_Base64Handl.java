package com.me.server.zip;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

public class Zip_Base64Handl implements Runnable {
	
	Socket client;
	private static final String DEFAULTZIPENTRYNAME = "default.xml";
	
	public Zip_Base64Handl(Socket client) {
		super();
		this.client = client;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ZipUtil zu=new ZipUtil();
		
		InputStream is=null;
		ZipInputStream zis=null;
		BufferedInputStream bis = null;
		try {
			is = client.getInputStream();
			
			Map map=zu.decompressToString(is);
			String unzipstr=(String) map.get(DEFAULTZIPENTRYNAME);
			String receiveStr = Base64.decode(unzipstr);
			
			System.out.println(" ’µΩ«Î«Û£∫"+receiveStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class MyHandler implements ElementHandler{

	@Override
	public void onEnd(ElementPath arg0) {
		// TODO Auto-generated method stub
		Element e=arg0.getCurrent();
		if(e.getName().equals("MsgNo")){
			String msgnoText=e.getTextTrim();
			System.out.println(msgnoText);
			if(msgnoText.equals("2601")){
				
			}else{
				
			}
		}
		e.detach();
	}

	@Override
	public void onStart(ElementPath arg0) {
		// TODO Auto-generated method stub
		
	}
	
}