package de.dhbw.mannheim.iot.model;

/**
 * Created by D059166 on 14.04.2015.
 */
public class DifferenceRuntimeResult extends Result {

    private final String machineOrderId;
    private final long expectedRuntime;
    private final long actualRuntime;
    private final long difference;

    public DifferenceRuntimeResult(String machineOrderId, long expectedRuntime, long actualRuntime, long difference) {
        super();
        this.machineOrderId = machineOrderId;
        this.expectedRuntime = expectedRuntime;
        this.actualRuntime = actualRuntime;
        this.difference = difference;
    }

    public long getExpectedRuntime() {
        return expectedRuntime;
    }

    public long getActualRuntime() {
        return actualRuntime;
    }

    public long getDifference() {
        return difference;
    }
}
