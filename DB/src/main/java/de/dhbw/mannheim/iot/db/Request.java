package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.model.Model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
public class Request implements Serializable {

    private final Class<? extends Model> requestedClass;

    private final Map<String,Object> properties;

    public Request(Class<? extends Model> requestedClass, Map<String, Object> properties) {
        this.requestedClass = requestedClass;
        this.properties = properties;
    }

    public Class<? extends Model> getRequestedClass() {
        return requestedClass;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
