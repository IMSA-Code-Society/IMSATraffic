package codesociety.traffic;

import codesociety.traffic.driver.DriverServer;
import codesociety.traffic.util.ConfigReader;
import codesociety.traffic.util.DatabaseIO;
import codesociety.traffic.util.DeviceCounter;
import codesociety.traffic.web.WebServer;

public class Server 
{
    public static void main( String[] args )
    {
        System.out.println("Starting server...");

        ConfigReader config = new ConfigReader();
        DatabaseIO db = new DatabaseIO(config);

        DriverServer driverServer = new DriverServer(config.driverPort, db);
        driverServer.start();

        DeviceCounter deviceCounter = new DeviceCounter(config, db);
        deviceCounter.start();

        WebServer webServer = new WebServer(config, db, deviceCounter);
        webServer.start();
    }
}
