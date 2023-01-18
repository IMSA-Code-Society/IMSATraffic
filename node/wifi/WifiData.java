package wifi;

public class WifiData {
    public int macTotal;
    public int ipTotal;
    public double packetsPerSecond;
    public double httpPacketsPerSecond;

    public WifiData(int macTotal, int ipTotal, double packetsPerSecond, double httpPacketsPerSecond) {
        this.macTotal = macTotal;
        this.ipTotal = ipTotal;
        this.packetsPerSecond = packetsPerSecond;
        this.httpPacketsPerSecond = httpPacketsPerSecond;
    }
}
