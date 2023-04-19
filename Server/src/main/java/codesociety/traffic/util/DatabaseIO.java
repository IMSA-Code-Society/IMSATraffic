package codesociety.traffic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import codesociety.traffic.driver.DriverData;
import codesociety.traffic.node.NodeData;

public class DatabaseIO {
    private RoomReader roomConfig;

    private Hashtable<String, List<Device>> driverRooms = new Hashtable<>();
    private float[] nodeRooms = new float[8];
    private Connection conn;

    public DatabaseIO(ConfigReader config) {
        this.roomConfig = config.roomReader;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        props.setProperty("user", config.dbUser);
        props.setProperty("password", config.dbPassword);
        props.setProperty("ssl", "false");

        try {
            conn = DriverManager.getConnection(config.dbURL, props);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void writeDriverData(DriverData data) {
        List<Device> deviceList = driverRooms.get(data.bssid);

        if (deviceList == null) {
            driverRooms.put(data.bssid, new ArrayList<>());
            deviceList = driverRooms.get(data.bssid);
        }

        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i).id.equals(data.deviceName)) {
                deviceList.set(i, new Device(data.deviceName, System.currentTimeMillis()));
                driverRooms.put(data.bssid, deviceList);
                return;
            }
        }

        deviceList.add(new Device(data.deviceName, System.currentTimeMillis()));
        driverRooms.put(data.bssid, deviceList);
    }

    public void writeNodeData(NodeData data) {
        nodeRooms[roomConfig.roomLabels.indexOf(data.room)] = data.value;
    }

    public void executeQuery(String statement) {
        try {
            Statement st = conn.createStatement();
            st.executeQuery(statement);
            st.close();
        } catch (SQLException e) {}
    }

    public Hashtable<String, List<Device>> getDriverRooms(int interval) {
        driverRooms = cleanRooms(interval);
        return driverRooms;
    }

    public float[][] getHistory() {
        float[][] history = new float[8][10];
        
        ResultSet rs;

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM traffic LIMIT 10");

            int i = 0;
            while (rs.next()) {
                String ts = rs.getString("ts"); 
                for (int j = 0; j < 8; j++) {
                    double v = rs.getDouble(roomConfig.roomLabels.get(j));
                    history[j][i] = (float)v;
                }
                i++;
            }

            st.close();
        } catch (Exception e) {}

        return history;
    }

    public float[] getNodeRooms() {
        return nodeRooms;
    }

    private Hashtable<String, List<Device>> cleanRooms(int interval) {
        Hashtable<String, List<Device>> cleanedRooms = driverRooms;
        Set<String> roomKeys = driverRooms.keySet();

        for (String key : roomKeys) {
            List<Device> devices = driverRooms.get(key);
            List<Device> old = new ArrayList<>();

            for (Device device : devices) {
                if (System.currentTimeMillis() - device.timestamp > interval) {
                    old.add(device);
                }
            }

            devices.removeAll(old);
            cleanedRooms.put(key, devices);
        }

        return cleanedRooms;
    }
}
