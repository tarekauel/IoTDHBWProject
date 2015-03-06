package de.dhbw.mannheim.iot.db;

import de.dhbw.mannheim.iot.communication.Message;

/**
 * This class is the abstract class for all messages which are sent between our components
 */

public class DBGetDataMessage extends Message
{
    // serial version UID was generated with serialzer command

    private long timeStamp;
    private String requestedData="ERP data";




    /** Default constructor. */
    public DBGetDataMessage(long timeStamp, String requestedData)
    {   super(timeStamp);
        this.requestedData=requestedData;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return timeStamp+"\n"+requestedData;
    }
}
