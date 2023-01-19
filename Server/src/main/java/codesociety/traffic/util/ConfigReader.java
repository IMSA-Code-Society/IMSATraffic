package codesociety.traffic.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public int port;

    public ConfigReader() {
        FileInputStream serverStream;
        Properties serverProp = new Properties();
        
        try {
            serverStream = new FileInputStream("server/config/server.config");
            serverProp.load(serverStream);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        this.port = Integer.parseInt(serverProp.getProperty("port"));
    }
}
