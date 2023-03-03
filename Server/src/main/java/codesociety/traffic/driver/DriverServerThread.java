package codesociety.traffic.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import codesociety.traffic.util.DatabaseIO;

public class DriverServerThread extends Thread {
    private Socket socket;
    private DatabaseIO db;

    public DriverServerThread(Socket socket, DatabaseIO db) {
        this.socket = socket;
        this.db = db;
    }

    public void run() {
        System.out.println("new connection");
        try {
            InputStream stream = socket.getInputStream();
            byte[] data = new byte[stream.available()];
            stream.read(data);
            if (data.length != 0) {
                db.writeDriverData(parseDriverData(data));
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }

    private DriverData parseDriverData(byte[] data) throws UnsupportedEncodingException {
        String bssid = new String(data, 0, 17, "UTF-8");
        String deviceName = new String(data, 17, data.length - 17, "UTF-8");
        return new DriverData(bssid, deviceName);
    }
}
