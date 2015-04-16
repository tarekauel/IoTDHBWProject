package de.dhbw.mannheim.iot.model.simple;

import de.dhbw.mannheim.iot.model.Model;

import java.util.UUID;

/**
 * Created by D059166 on 09.04.2015.
 */
public class UuidValue extends Model {

    private UUID uuid;

    public UuidValue(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }

}
