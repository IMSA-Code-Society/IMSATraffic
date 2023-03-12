package codesociety.traffic;

import codesociety.traffic.driver.DriverServer;
import codesociety.traffic.util.ConfigReader;
import codesociety.traffic.util.DatabaseIO;
import codesociety.traffic.web.WebServer;

public class Server 
{
    public static void main( String[] args )
    {
        System.out.println("Starting server...");

        DatabaseIO db = new DatabaseIO();

        ConfigReader config = new ConfigReader();
        WebServer webServer = new WebServer(config);
        webServer.start();

        System.out.println(config.driverPort);
        DriverServer driverServer = new DriverServer(config.driverPort, db);
        driverServer.start();
    }
}
