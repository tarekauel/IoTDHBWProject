package de.dhbw.mannheim.iot.model;

import de.dhbw.mannheim.assemblylinesim.model.Report;
import de.dhbw.mannheim.erpsim.model.CustomerOrder;
import de.dhbw.mannheim.erpsim.model.MachineOrder;
import de.dhbw.mannheim.iot.model.machine.Phototransistor;
import de.dhbw.mannheim.iot.model.simple.LongValue;
import de.dhbw.mannheim.iot.model.simple.UuidValue;

import java.sql.Timestamp;

/**
 * Created by D059166 on 09.04.2015.
 */
public class ModelFactory {

    public static Model getModelInstance(Report report) {
        return new de.dhbw.mannheim.iot.model.machine.Report(report);
    }

    public static Model getModelInstance(CustomerOrder customerOrder) {
        return new de.dhbw.mannheim.iot.model.erp.CustomerOrder(customerOrder);
    }

    public static Model getModelInstance(MachineOrder machineOrder) {
        return new de.dhbw.mannheim.iot.model.machine.MachineOrder(machineOrder);
    }

    public static Model getModelInstance(long longValue) {
        return new LongValue(longValue);
    }

    public static Model getModelInstance(String stringValue) {
        return new UuidValue(stringValue);
    }

    public static Model getModelInstance(Timestamp timestamp, String node,boolean value) {
        return new Phototransistor(timestamp,node,value);
    }

}
