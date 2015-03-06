package de.dhbw.mannheim.iot.mq;

import de.dhbw.mannheim.iot.communication.Message;

/**
 * This class is the abstract class for all messages which are sent between our components
 */

public class MQRegistrationMessage extends Message
{
    // serial version UID was generated with serialzer command

    private long timeStamp;
    private String registerFor="ERP";




    /** Default constructor. */
    public MQRegistrationMessage(long timeStamp,String registerFor)
    {   super(timeStamp);
        this.registerFor=registerFor;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return timeStamp+"\n"+registerFor;
    }
}
