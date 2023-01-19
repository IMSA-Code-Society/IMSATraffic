package codesociety.traffic;

import codesociety.traffic.network.WebServer;
import codesociety.traffic.util.ConfigReader;

public class Server 
{
    public static void main( String[] args )
    {
        ConfigReader config = new ConfigReader();
        WebServer server = new WebServer(config);
        server.start();
    }
}
