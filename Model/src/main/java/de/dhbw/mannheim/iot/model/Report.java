package de.dhbw.mannheim.iot.model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class Report extends Model {

    private final String machineOrderId;

    private final Timestamp startTime;

    private final ArrayList<Timestamp> passedLightBarrier;

    private double speedShaperRPM;
    private double speedDrillerRPM;

    public Report(de.dhbw.mannheim.assemblylinesim.model.Report r) {
        machineOrderId = r.getMachineOrderId();
        startTime = r.getStartTime();
        passedLightBarrier = r.getPassedLightBarrier();
        speedShaperRPM = r.getSpeedShaperRPM();
        speedDrillerRPM = r.getSpeedDrillerRPM();
    }

    public Report(String machineOrderId, Timestamp startTime, ArrayList<Timestamp> passedLightBarrier, double speedShaperRPM, double speedDrillerRPM) {
        this.machineOrderId = machineOrderId;
        this.startTime = startTime;
        this.passedLightBarrier = passedLightBarrier;
        this.speedShaperRPM = speedShaperRPM;
        this.speedDrillerRPM = speedDrillerRPM;
    }

    public String getMachineOrderId() {
        return machineOrderId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public ArrayList<Timestamp> getPassedLightBarrier() {
        return passedLightBarrier;
    }

    public double getSpeedShaperRPM() {
        return speedShaperRPM;
    }

    public double getSpeedDrillerRPM() {
        return speedDrillerRPM;
    }
}
