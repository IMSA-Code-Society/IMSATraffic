package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigReader {
    // Client configuration
    public String ip;
    public int port;

    // Traffic configuration
    public List<String> command;
    public double timer;
    public String localIp;
    public String routerIp;

    public ConfigReader() {
        FileInputStream clientStream;
        Properties clientProp = new Properties();

        FileInputStream trafficStream;
        Properties trafficProp = new Properties();
        
        try {
            clientStream = new FileInputStream("node/config/client.config");
            clientProp.load(clientStream);
            trafficStream = new FileInputStream("node/config/traffic.config");
            trafficProp.load(trafficStream);
        } catch (FileNotFoundException err) {
            err.printStackTrace();
        } catch (IOException err) {
            err.printStackTrace();
        }

        this.ip = clientProp.getProperty("ip");
        this.port = Integer.parseInt(clientProp.getProperty("port"));
        
        this.command = Arrays.asList(trafficProp.getProperty("command").split("\\s+"));
        this.timer = Double.parseDouble(trafficProp.getProperty("timer"));
        this.localIp = trafficProp.getProperty("localIp");
        this.routerIp = trafficProp.getProperty("routerIp");
    }
}
