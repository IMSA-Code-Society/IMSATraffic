package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import wifi.WifiLogger;

public class TsharkRunner implements Runnable {
    private ConfigReader config;
    private WifiLogger wifi;

    public TsharkRunner(ConfigReader config, WifiLogger wifi) {
        this.config = config;
        this.wifi = wifi;

        Thread thread = new Thread(this, "TsharkRunner");
        thread.start();
    }

    public void run() {
        ProcessBuilder builder = new ProcessBuilder(config.command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException err) {
            err.printStackTrace();
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
                String[] data = line.split("\\s+");

                String ip = "";
                if (data.length == 2) {
                    ip = data[1];
                }

                wifi.update(data[0], ip);
            }

            wifi.end();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
