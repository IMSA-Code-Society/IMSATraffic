import util.ConfigReader;

class Node {
    public static void main(String args[]) {
        ConfigReader config = new ConfigReader();
        TrafficEstimator estimator = new TrafficEstimator(config);
        estimator.start();
    }
}