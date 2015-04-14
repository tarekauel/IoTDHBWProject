package de.dhbw.mannheim.iot.ia.opc;

import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.ModelFactory;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;

/**
 * Created by Marc on 10.03.2015.
 */
@Slf4j
public class Random1 extends OPC {

    public static void main(String[] args) {
        new Random1("localhost", MessageQueue.INGOING_PORT, "opc.tcp://MacBookPro.local:53530/OPCUA/SimulationServer");
    }

    public static final String NODE_NAME = "Random1";
    public static final int NAME_SPACE_INDEX = 5;

    public Random1(String ipMessageQueue, int portMessageQueue, String opcUrl) {
        super(ipMessageQueue, portMessageQueue, opcUrl);
    }

    protected String getNodeName() {
        return Random1.NODE_NAME;
    }

    protected int getNodeNameSpaceIndex() {
        return Random1.NAME_SPACE_INDEX;
    }

    protected Model transform(DataValue dataValue) {
       log.trace("Transforming to Model: " + ((long) dataValue.getValue().doubleValue() * 100));
       return ModelFactory.getModelInstance((long) dataValue.getValue().doubleValue() * 100);
    }

}
