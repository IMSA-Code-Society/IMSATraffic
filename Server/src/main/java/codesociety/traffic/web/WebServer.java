package codesociety.traffic.web;

import static spark.Spark.*;

import codesociety.traffic.node.NodeData;
import codesociety.traffic.util.ConfigReader;
import codesociety.traffic.util.DatabaseIO;
import codesociety.traffic.util.DeviceCounter;
import static codesociety.traffic.util.StringUtils.*;

public class WebServer {
    private ConfigReader config;
    private DatabaseIO db;
    private DeviceCounter counter;

    public WebServer(ConfigReader config, DatabaseIO db, DeviceCounter counter) {
        this.config = config;
        this.db = db;
        this.counter = counter;
    }

    public void start() {
        port(config.webPort);
        staticFiles.externalLocation("public");
        init();

        get("/traffic.json", (req, res) -> {
            String json = generateRoomJson(floatArrayToStringArray(counter.counts));
            res.type("application/json");
            return json;
        });

        get("/area/*", (req, res) -> {
            String area = req.splat()[0];
            float value = Float.parseFloat(req.queryParams("value"));
            db.writeNodeData(new NodeData(area, value));
            return "success";
        });

        get("/history.json", (req, res) -> {
            float[][] history = db.getHistory();
            String[] historyArrays = new String[8];
            for (int i = 0; i < 8; i++) {
                historyArrays[i] = stringifyFloatArray(history[i]);
            }
            String json = generateRoomJson(historyArrays);
            return json;
        });
    }
}
