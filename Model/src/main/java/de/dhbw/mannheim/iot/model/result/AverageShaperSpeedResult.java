package de.dhbw.mannheim.iot.model.result;

/**
 * Created by D059166 on 14.04.2015.
 */
public class AverageShaperSpeedResult extends Result {

    private final double average;

    public AverageShaperSpeedResult(double average) {
        super();
        this.average = average;
    }

    public double getAverage() {
        return average;
    }
}
