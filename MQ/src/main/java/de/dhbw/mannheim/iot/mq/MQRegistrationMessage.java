package de.dhbw.mannheim.iot.mq;

import de.dhbw.mannheim.iot.communication.Message;

/**
 * This class is a message class for communication between components
 */

public class MQRegistrationMessage extends Message
{
   private String registerFor="ERP";




    /** Default constructor. */
    public MQRegistrationMessage(long timeStamp,String registerFor)
    {   super(timeStamp);
        this.registerFor=registerFor;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return super.toString()+"\n"+registerFor;
    }
}
