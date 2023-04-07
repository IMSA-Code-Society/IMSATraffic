package codesociety.traffic.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import codesociety.traffic.driver.DriverData;

public class DatabaseIO {
    private Hashtable<String, List<Device>> rooms = new Hashtable<>();
    private Connection conn;

    public DatabaseIO(ConfigReader config) {
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
        List<Device> deviceList = rooms.get(data.bssid);

        if (deviceList == null) {
            rooms.put(data.bssid, new ArrayList<>());
        }

        for (int i = 0; i < deviceList.size(); i++) {
            if (deviceList.get(i).id == data.deviceName) {
                return;
            }
        }

        deviceList.add(new Device(data.deviceName, System.currentTimeMillis()));
        rooms.put(data.bssid, deviceList);
    }

    public void executeQuery(String statement) {
        try {
            Statement st = conn.createStatement();
            st.executeQuery(statement);
            st.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Hashtable<String, List<Device>> getRooms() {
        return rooms;
    }
}
