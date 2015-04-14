package de.dhbw.mannheim.iot.model;

/**
 * @author Tarek Auel
 * @since April 14, 2015.
 */
public class AverageRuntimeResult extends Result {

    private double actualRuntime;
    private double difference;
    private double expectedRuntime;

    public AverageRuntimeResult(double actualRuntime, double difference, double expectedRuntime) {
        this.actualRuntime = actualRuntime;
        this.difference = difference;
        this.expectedRuntime = expectedRuntime;
    }

    public double getActualRuntime() {
        return actualRuntime;
    }

    public double getDifference() {
        return difference;
    }

    public double getExpectedRuntime() {
        return expectedRuntime;
    }
}
