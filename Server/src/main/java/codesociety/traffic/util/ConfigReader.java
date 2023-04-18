package codesociety.traffic.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public RoomReader roomReader;

    public int webPort;
    public float driverWeight;
    public float nodeWeight;

    public int driverPort;

    public String dbURL;
    public String dbUser;
    public String dbPassword;

    public ConfigReader() {
        roomReader = new RoomReader();

        FileInputStream serverStream;
        Properties serverProp = new Properties();
        FileInputStream driverStream;
        Properties driverProp = new Properties();
        FileInputStream dbStream;
        Properties dbProp = new Properties();
        
        try {
            serverStream = new FileInputStream("server/config/server.config");
            serverProp.load(serverStream);
            driverStream = new FileInputStream("server/config/driver.config");
            driverProp.load(driverStream);
            dbStream = new FileInputStream("server/config/db.config");
            dbProp.load(dbStream);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        webPort = Integer.parseInt(serverProp.getProperty("port"));
        driverWeight = Float.parseFloat(serverProp.getProperty("driver_weight"));
        nodeWeight = Float.parseFloat(serverProp.getProperty("node_weight"));

        driverPort = Integer.parseInt(driverProp.getProperty("port"));
    
        dbURL = dbProp.getProperty("url");
        dbUser = dbProp.getProperty("user");
        dbPassword = dbProp.getProperty("password");
    }
}
