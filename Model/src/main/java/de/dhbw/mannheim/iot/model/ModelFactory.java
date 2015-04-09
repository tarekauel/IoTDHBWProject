package de.dhbw.mannheim.iot.model;

import de.dhbw.mannheim.assemblylinesim.model.Report;
import de.dhbw.mannheim.erpsim.model.CustomerOrder;
import de.dhbw.mannheim.erpsim.model.MachineOrder;

/**
 * Created by D059166 on 09.04.2015.
 */
public class ModelFactory {

    public static Model getModelInstance(Report report) {
        return new ReportContainer(report);
    }

    public static Model getModelInstance(CustomerOrder customerOrder) {
        return new CustomerOrderContainer(customerOrder);
    }

    public static Model getModelInstance(MachineOrder machineOrder) {
        return new MachineOrderContainer(machineOrder);
    }

    public static Model getModelInstance(long longValue) {
        return new LongValue(longValue);
    }

    public static Model getModelInstance(String stringValue) {
        return new UuidValue(stringValue);
    }

}
