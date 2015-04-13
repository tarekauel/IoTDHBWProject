package de.dhbw.mannheim.iot.model;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class MachineOrder extends Model {

    private final String id;

    private final String machine;

    private final double plannedSeconds;

    private final double speedShaperRPM;

    private final double speedDrillerRPM;

    public MachineOrder(de.dhbw.mannheim.erpsim.model.MachineOrder mo) {
        id = mo.getId();
        machine = mo.getMachine();
        plannedSeconds = mo.getPlannedSeconds();
        speedShaperRPM = mo.getSpeedShaperRPM();
        speedDrillerRPM = mo.getSpeedDrillerRPM();
    }

    public String getId() {
        return id;
    }

    public String getMachine() {
        return machine;
    }

    public double getPlannedSeconds() {
        return plannedSeconds;
    }

    public double getSpeedShaperRPM() {
        return speedShaperRPM;
    }

    public double getSpeedDrillerRPM() {
        return speedDrillerRPM;
    }
}
