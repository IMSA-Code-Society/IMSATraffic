package codesociety.traffic.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public int webPort;

    public int driverPort;

    public String dbURL;
    public String dbUser;
    public String dbPassword;

    public ConfigReader() {
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

        this.webPort = Integer.parseInt(serverProp.getProperty("port"));

        this.driverPort = Integer.parseInt(driverProp.getProperty("port"));
        
        this.dbURL = driverProp.getProperty("url");
        this.dbUser = driverProp.getProperty("user");
        this.dbPassword = driverProp.getProperty("password");
    }
}
