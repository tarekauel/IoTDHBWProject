package de.dhbw.mannheim.iot.model.simple;

import de.dhbw.mannheim.iot.model.Model;

/**
 * Created by D059166 on 09.04.2015.
 */
public class LongValue extends Model {

    private long value;

    public LongValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
