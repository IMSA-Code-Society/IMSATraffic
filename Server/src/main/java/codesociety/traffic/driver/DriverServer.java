package codesociety.traffic.driver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import codesociety.traffic.util.DatabaseIO;

public class DriverServer extends Thread {
    private int port;
    private DatabaseIO db;

    public DriverServer(int port, DatabaseIO db) {
        this.port = port;
        this.db = db;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new DriverServerThread(socket, db).start();
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}
