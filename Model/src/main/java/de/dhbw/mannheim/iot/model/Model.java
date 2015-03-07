package de.dhbw.mannheim.iot.model;

import java.io.Serializable;

/**
 * This class is the abstract class for all models which are sent between our components
 */

public class Model implements Serializable
{
    // serial version UID was generated with serializer command

    private long timeStamp;




    /** Default constructor. */
    public Model(long timeStamp)
    {
        this.timeStamp=timeStamp;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return timeStamp+"";
    }
}
