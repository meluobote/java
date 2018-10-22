package com.me.server.zip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip_Base64Handl2 implements Runnable {
	Socket client;
	
	public Zip_Base64Handl2(Socket client) {
		super();
		this.client = client;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			InputStream is =client.getInputStream();
			ZipInputStream zis=new ZipInputStream(is);
			ZipEntry ze= zis.getNextEntry();
			
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			BufferedInputStream bis=new BufferedInputStream(zis);
			byte[] by=new byte[256];
			int len=0;
			while((len=bis.read(by))>0){
				baos.write(by, 0, len);
			}
			baos.flush();
			
			String recEncodeStr=new String(baos.toByteArray(),"GBK");
//			System.out.println(recEncodeStr);
			byte[] decodeBy=Base64.getDecoder().decode(baos.toByteArray());
			System.out.println(new String(decodeBy,"GBK"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
