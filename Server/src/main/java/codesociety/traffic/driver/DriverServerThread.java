package codesociety.traffic.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import codesociety.traffic.util.DatabaseIO;

public class DriverServerThread extends Thread {
    private int PACKET_SIZE = 1024;

    private Socket socket;
    private DatabaseIO db;

    public DriverServerThread(Socket socket, DatabaseIO db) {
        this.socket = socket;
        this.db = db;
    }

    public void run() {
        System.out.println("new connection");
        try {
            char[] buffer = new char[PACKET_SIZE];
            int bytesRead = 0;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((bytesRead = in.read(buffer)) != -1) {
                db.writeDriverData(parseDriverData(buffer));
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    private DriverData parseDriverData(char[] buffer) throws UnsupportedEncodingException {
        String data = new String(buffer);
        String bssid = data.substring(0, 17);
        String deviceName = data.substring(17, data.length() - 17);
        return new DriverData(bssid, deviceName);
    }
}
