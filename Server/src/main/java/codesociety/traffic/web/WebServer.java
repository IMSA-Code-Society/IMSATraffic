package codesociety.traffic.web;

import static spark.Spark.*;

import codesociety.traffic.util.ConfigReader;
import codesociety.traffic.util.DeviceCounter;

public class WebServer {
    private String[] roomLabels = new String[] {"in2", "oldCaf", "irc", "tvPit", "ssa", "loft", "msa", "studion"};

    private ConfigReader config;
    private DeviceCounter counter;

    public WebServer(ConfigReader config, DeviceCounter counter) {
        this.config = config;
        this.counter = counter;
    }

    public void start() {
        port(config.webPort);
        staticFiles.externalLocation("public");
        init();

        get("/traffic.json", (req, res) -> {
            String json = "{\n";
            for (int i = 0; i < roomLabels.length; i++) {
                json += "\t\"" + roomLabels[i] + "\": " + counter.counts[i] + ",\n";
            }
            json = json.replaceFirst(".$","");
            json += "}";
            res.type("application/json");
            return json;
        });
    }
}
