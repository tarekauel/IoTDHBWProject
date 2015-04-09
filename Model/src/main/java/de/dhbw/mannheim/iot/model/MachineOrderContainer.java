package de.dhbw.mannheim.iot.model;

import de.dhbw.mannheim.erpsim.model.MachineOrder;

/**
 * Created by D059166 on 09.04.2015.
 */
public class MachineOrderContainer implements Model {

    private MachineOrder machineOrder;

    public MachineOrderContainer(MachineOrder machineOrder) {
        this.machineOrder = machineOrder;
    }

    public MachineOrder getMachineOrder() {
        return machineOrder;
    }

}
