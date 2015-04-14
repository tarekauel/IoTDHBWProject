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

    public boolean isVALUE() {
        return VALUE;
    }

    public Timestamp getTIMESTAMP() {
        return TIMESTAMP;
    }

    public String getNODE() {
        return NODE;
    }
}
