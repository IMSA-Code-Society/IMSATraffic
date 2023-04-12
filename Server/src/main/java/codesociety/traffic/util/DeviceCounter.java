package codesociety.traffic.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class DeviceCounter extends Thread {
    private int INTERVAL = 5000;//5 * 60 * 1000;
    private int ROOM_COUNT = 8;
    private HashMap<String, Integer> BSSID_TABLE;

    private DatabaseIO db;

    public int[] counts = new int[8];

    public DeviceCounter(DatabaseIO db) {
        this.db = db;
        
        RoomReader roomReader = new RoomReader();
        BSSID_TABLE = roomReader.getRooms();
    }

    public void run() {
        try {
            System.out.println("start");

            while (true) {
                Thread.sleep(INTERVAL);

                Hashtable<String, List<Device>> rooms = db.getRooms(INTERVAL);
                counts = countDevices(rooms);
                String countsString = "";

                for (int i = 0; i < counts.length - 1; i++) {
                    countsString += counts[i] + ", ";
                }
                countsString += counts[counts.length - 1];

                System.out.println(countsString);
                db.executeQuery("INSERT INTO traffic VALUES (current_timestamp, " + countsString + ")");

            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private int[] countDevices(Hashtable<String, List<Device>> rooms) {
        int[] counts = new int[ROOM_COUNT];
        Set<String> roomKeys = rooms.keySet();

        for (String key : roomKeys) {
            counts[BSSID_TABLE.get(key)] = rooms.get(key).size();
        }

        return counts;
    }
}
