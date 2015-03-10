package de.dhbw.mannheim.iot.ia;

import de.dhbw.mannheim.iot.model.Model;
import org.opcfoundation.ua.builtintypes.DataValue;

/**
 * Created by Marc on 10.03.2015.
 */
public class Random1 extends OPC {

    public static void main(String[] args) {
        new Random1("", 0, "");
    }

    public static final String NODE_NAME = "Random1";

    public Random1(String ipMessageQueue, int portMessageQueue, String opcUrl) {
        super(ipMessageQueue, portMessageQueue, opcUrl);
    }

    protected String getNodeName() {
        return Random1.NODE_NAME;
    }

    protected Model transform(DataValue dataValue) {
       return new Model(dataValue.getSourceTimestamp().getTimeInMillis());
    }

}
