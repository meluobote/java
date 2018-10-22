package com.me.server.zip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class server {
	public static void main(String[] args) throws IOException {
		ServerSocket ss=new ServerSocket();
		ss.bind(new InetSocketAddress("127.0.0.1",18010));
		System.out.println(ss.getLocalSocketAddress().toString());
		
		Socket client=null;
		Thread th=null;
		while(true){
			client = ss.accept();
			System.out.println(client.getInetAddress().getHostAddress() + "  is connecting");
			th = new Thread(new Zip_Base64Handl(client));
			th.start();
		}
	}
}
