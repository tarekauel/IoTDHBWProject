package de.dhbw.mannheim.iot.model.machine;

import de.dhbw.mannheim.iot.model.Model;

import java.sql.Timestamp;

/**
 * Created by D059166 on 09.04.2015.
 */
public class Phototransistor extends Model {

    private final boolean value;
    private final Timestamp timestamp;
    private final String node;

    public Phototransistor(Timestamp timestamp, String node,boolean value) {
        this.value = value;
        this.timestamp = timestamp;
        this.node = node;
    }

    public boolean getValue() {
        return value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getNode() {
        return node;
    }
}
