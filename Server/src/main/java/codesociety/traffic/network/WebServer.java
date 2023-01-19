package codesociety.traffic.network;

import static spark.Spark.*;

import codesociety.traffic.util.ConfigReader;

public class WebServer {
    private ConfigReader config;

    public WebServer(ConfigReader config) {
        this.config = config;
    }

    public void start() {
        port(config.port);
        get("/hello", (req, res) -> "Hello World");
    }
}
