package de.dhbw.mannheim.iot.model;

import de.caluga.morphium.annotations.Entity;
import de.caluga.morphium.annotations.Id;
import org.bson.types.ObjectId;

import java.io.Serializable;

/**
 * This interface is the abstract interface for all models which are sent between our components
 */
@Entity
public class Model implements Serializable {

    @Id
    private ObjectId objectId;
}
