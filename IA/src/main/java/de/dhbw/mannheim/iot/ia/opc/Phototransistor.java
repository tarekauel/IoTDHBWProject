package de.dhbw.mannheim.iot.ia.opc;

import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.ModelFactory;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by Marc on 10.03.2015.
 */
@Slf4j
public class Phototransistor extends OPC {

    public static void main(String[] args) {
        new Phototransistor("localhost", MessageQueue.INGOING_PORT, "opc.tcp://192.168.0.102:49320","SPSData.S7-1200.Inputs.Phototransistor_Slider1");
        new Phototransistor("localhost", MessageQueue.INGOING_PORT, "opc.tcp://192.168.0.102:49320","SPSData.S7-1200.Inputs.Phototransistor_MillingMachine");
        new Phototransistor("localhost", MessageQueue.INGOING_PORT, "opc.tcp://192.168.0.102:49320","SPSData.S7-1200.Inputs.Phototransistor_LoadingStation");
        new Phototransistor("localhost", MessageQueue.INGOING_PORT, "opc.tcp://192.168.0.102:49320","SPSData.S7-1200.Inputs.Phototransistor_DrillingMachine");
        new Phototransistor("localhost", MessageQueue.INGOING_PORT, "opc.tcp://192.168.0.102:49320","SPSData.S7-1200.Inputs.Phototransistor_Conveyer_BeltSwap");
    }

    public final String NODE_NAME ;
    public static final int NAME_SPACE_INDEX = 2;

    public Phototransistor(String ipMessageQueue, int portMessageQueue, String opcUrl, String nodeName) {
        super(ipMessageQueue, portMessageQueue, opcUrl);
        NODE_NAME = nodeName;
        log.trace("Starting Phototransistor OPC Observer for Node:" + nodeName);
    }

    protected String getNodeName() {
    return NODE_NAME;
    }

    protected int getNodeNameSpaceIndex() {
        return Phototransistor.NAME_SPACE_INDEX;
    }

    protected Model transform(DataValue dataValue) {
        Timestamp ts = new Timestamp(Calendar.getInstance().getTime().getTime());
        boolean value = dataValue.getValue().booleanValue();

        log.trace("Transforming to Model: " + ts + " - " + NODE_NAME + " - " + value);
       return ModelFactory.getModelInstance(ts,NODE_NAME,value);
    }

}
