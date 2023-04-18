package codesociety.traffic.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class RoomReader {
    /*
     * Rooms:
     * in2: 0
     * oldCaf: 1
     * irc: 2
     * tvPit: 3
     * ssa: 4
     * loft: 5
     * msa: 6
     * studion: 7
     */
    
    private String[] roomLabelsArray = new String[] {"in2", "oldCaf", "irc", "tvPit", "ssa", "loft", "msa", "studion"};
    public List<String> roomLabels = new ArrayList<>(Arrays.asList(roomLabelsArray));
    public float[] weights = new float[] {1, 1, 1, 1, 1, 1, 1, 1};

    private HashMap<String, Integer> rooms = new HashMap<>();
    private BufferedReader in;
    private StringTokenizer st;

    public RoomReader() {
        try {
            in = new BufferedReader(new FileReader("server/config/rooms.in"));
        } catch (IOException e) {
            System.out.println(e);
        }

        String bssid;
        int room;
        while ((bssid = next()) != null && (room = nextInt()) != -1) {
            rooms.put(bssid, room);
        }
    }

    public HashMap<String, Integer> getRooms() {
        return rooms;
    }

    private String next() {
        try {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(in.readLine());
            }
            return st.nextToken();
        } catch (Exception e) {}

        return "-1";
    }

    private int nextInt() {
        return Integer.parseInt(next());
    }
}
