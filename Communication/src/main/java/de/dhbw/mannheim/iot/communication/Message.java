package de.dhbw.mannheim.iot.communication;

import java.io.Serializable;

/**
 * This class is the abstract class for all messages which are sent between our components
 */

public class Message implements Serializable
{
    // serial version UID was generated with serialzer command

    private long timeStamp;




    /** Default constructor. */
    public Message(long timeStamp)
    {
        this.timeStamp=timeStamp;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return timeStamp+"";
    }
}
