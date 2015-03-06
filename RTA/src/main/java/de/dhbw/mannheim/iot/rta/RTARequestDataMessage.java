package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.communication.Message;

/**
 * This class is a message class for communication between components
 */

public class RTARequestDataMessage extends Message
{
    private String request="";


    /** Default constructor. */
    public RTARequestDataMessage(long timeStamp, String results)
    {   super(timeStamp);
        this.request=results;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return super.toString()+"\n"+ request;
    }
}
