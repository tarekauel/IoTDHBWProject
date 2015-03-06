package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.communication.Message;

/**
 * This class is a message class for communication between components
 */

public class RTAResultMessage extends Message
{
    private String results="";


    /** Default constructor. */
    public RTAResultMessage(long timeStamp, String results)
    {   super(timeStamp);
        this.results=results;
    }

    /** Get a String representation of this class. */
    public String toString()
    {
        return super.toString()+"\n"+ results;
    }
}
