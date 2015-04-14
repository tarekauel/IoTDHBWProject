package de.dhbw.mannheim.iot.model;

import java.sql.Timestamp;

/**
 * Created by D059166 on 09.04.2015.
 */
public class Phototransistor extends Model {

    private final boolean VALUE;
    private final Timestamp TIMESTAMP;
    private final String NODE;

    public Phototransistor(Timestamp timestamp, String node,boolean value) {
        this.VALUE = value;
        this.TIMESTAMP = timestamp;
        this.NODE = node;
    }

    public boolean getValue() {
        return VALUE;
    }

    public Timestamp getTimestamp() {
        return TIMESTAMP;
    }

    public String getNode() {
        return NODE;
    }
}
