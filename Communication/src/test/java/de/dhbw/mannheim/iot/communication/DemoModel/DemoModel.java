package de.dhbw.mannheim.iot.communication.DemoModel;

import de.dhbw.mannheim.iot.model.Model;

import java.util.Date;
import java.util.Objects;

/**
 * @author Tarek Auel
 * @since March 13, 2015.
 */
public class DemoModel extends Model {

    private final String payload;

    public DemoModel(String payload) {
        super(new Date().getTime());
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "DemoModel{" +
                "payload='" + payload + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof DemoModel && Objects.equals(o.toString(), toString());
    }
}
