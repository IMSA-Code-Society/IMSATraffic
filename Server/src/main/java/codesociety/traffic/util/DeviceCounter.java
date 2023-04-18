package codesociety.traffic.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class DeviceCounter extends Thread {
    private int INTERVAL = 5000;//5 * 60 * 1000;
    private int ROOM_COUNT = 8;
    private HashMap<String, Integer> BSSID_TABLE;

    private ConfigReader config;
    private RoomReader roomReader;
    private DatabaseIO db;

    public float[] counts = new float[ROOM_COUNT];

    public DeviceCounter(ConfigReader config, DatabaseIO db) {
        this.config = config;
        this.roomReader = config.roomReader;
        this.db = db;
        
        BSSID_TABLE = roomReader.getRooms();
    }

    public void run() {
        try {
            System.out.println("start");

            while (true) {
                Thread.sleep(INTERVAL);

                Hashtable<String, List<Device>> driverRooms = db.getDriverRooms(INTERVAL);
                float[] nodeRooms = db.getNodeRooms();
                counts = countDevices(driverRooms, nodeRooms);
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

    private float[] countDevices(Hashtable<String, List<Device>> driverRooms, float[] nodeCounts) {
        float[] counts = new float[ROOM_COUNT];
        int[] driverCounts = new int[ROOM_COUNT];
        Set<String> driverRoomKeys = driverRooms.keySet();

        for (String key : driverRoomKeys) {
            driverCounts[BSSID_TABLE.get(key)] = driverRooms.get(key).size();
        }

        for (int i = 0; i < ROOM_COUNT; i++) {
            float value = driverCounts[i] * config.driverWeight + nodeCounts[i] * config.nodeWeight;
            counts[i] += value * roomReader.weights[i];
        }

        return counts;
    }
}
