package codesociety.traffic.driver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DriverServer {
    private int port;

    public DriverServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new DriverServerThread(socket).start();
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}
