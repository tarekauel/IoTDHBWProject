package de.dhbw.mannheim.iot.model;

import java.util.UUID;

/**
 * Created by D059166 on 09.04.2015.
 */
public class UuidValue implements Model {

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
