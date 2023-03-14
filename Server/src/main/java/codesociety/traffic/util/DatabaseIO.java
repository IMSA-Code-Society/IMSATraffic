package codesociety.traffic.util;

import codesociety.traffic.driver.DriverData;

public class DatabaseIO {
    public DatabaseIO() {

    }

    public void writeDriverData(DriverData data) {
        System.out.println(data.bssid);
        System.out.println(data.deviceName);

        
    }
}
