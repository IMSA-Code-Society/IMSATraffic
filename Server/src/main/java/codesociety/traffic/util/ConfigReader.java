package codesociety.traffic.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public int webPort;

    public int driverPort;

    public ConfigReader() {
        FileInputStream serverStream;
        Properties serverProp = new Properties();
        FileInputStream driverStream;
        Properties driverProp = new Properties();
        
        try {
            serverStream = new FileInputStream("server/config/server.config");
            serverProp.load(serverStream);
            driverStream = new FileInputStream("server/config/driver.config");
            driverProp.load(driverStream);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        this.webPort = Integer.parseInt(serverProp.getProperty("port"));
        this.driverPort = Integer.parseInt(driverProp.getProperty("port"));
    }
}
