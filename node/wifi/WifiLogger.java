package wifi;

import java.util.ArrayList;
import java.util.List;

import util.ConfigReader;

public class WifiLogger {
    private double timer;
    private String localIp;

    private WifiObserver observer;

    private List<String> macList = new ArrayList<String>();
    private List<String> ipList = new ArrayList<String>();
    private int packetCount = 0;
    private int httpPacketCount = 0;

    public WifiLogger(ConfigReader config) {
        this.timer = config.timer;
        this.localIp = config.localIp;
    }

    public void subscribe(WifiObserver observer) {
        this.observer = observer;
    }

    public void update(String mac, String ip) {
        packetCount++;

        if (!macList.contains(mac)) {
            macList.add(mac);
        }
        
        if (ip != "") {
            httpPacketCount++;
            if (!ipList.contains(ip) && ip.contains(localIp)) {
                ipList.add(ip);
            }
        }
    }

    public void end() {
        observer.update(new WifiData(macList.size(), ipList.size(), packetCount / timer, httpPacketCount / timer));
    }
}
