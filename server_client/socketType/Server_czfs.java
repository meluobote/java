package socketType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SocketHandler;

public class Server_czfs {
    private static Logger log= LogManager.getLogManager().getLogger("a");
    public static final int PORT=7090;
    private static ServerSocket serverSocket=null;
    private static void startServer(){
        Thread th=null;
        Socket request=null;

        try {
            serverSocket=new ServerSocket(PORT);
            log.info("Server  CZFS:  port is "+PORT);

            while(true){
                request=serverSocket.accept();
                th=new CZFSThread(request);
                th.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Server_czfs.startServer();
    }
}
