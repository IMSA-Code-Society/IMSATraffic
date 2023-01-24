import client.NodeClient;
import util.ConfigReader;
import util.TsharkRunner;
import wifi.WifiLogger;
import wifi.WifiData;

public class TrafficEstimator {
    private ConfigReader config;

    public TrafficEstimator(ConfigReader config) {
        this.config = config;
    }

    public void start() {
        NodeClient client = new NodeClient(config.ip, config.port);
        WifiLogger wifi = new WifiLogger(config);

        TsharkRunner tshark = new TsharkRunner(config, wifi);

        System.out.println("Traffic estimator started");

        wifi.subscribe((WifiData data) -> {
            System.out.println(data.macTotal);
            System.out.println(data.ipTotal);
            System.out.println(data.packetsPerSecond);
            System.out.println(data.httpPacketsPerSecond);
        });
    }
}
