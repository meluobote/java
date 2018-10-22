package com.sdfs.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SDFSServer {
    private static Logger log = Logger.getLogger(SDFSServer.class);
    private static int port = 4705;
    private static ServerSocket serverSocket=null;
    private static void startSDFSServer(){
        Thread sdfsThread=null;
        Socket request=null;
        try {
            serverSocket=new ServerSocket(port);
            log.info("Welcom to SDFSServer!");
            log.info("port: "+port);
            while (true){
                request=serverSocket.accept();
                sdfsThread=new SDFSThread(request);
                sdfsThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SDFSServer.startSDFSServer();
    }
}
