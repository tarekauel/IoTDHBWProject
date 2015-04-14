package de.dhbw.mannheim.iot.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by D059166 on 14.04.2015.
 */
public abstract class Result extends Model {
    //Just a collection of subclasses
    private final Timestamp timestamp;

    public Result() {
        timestamp = new Timestamp(new Date().getTime());
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
