package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.model.MachineOrder;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.Phototransistor;
import de.dhbw.mannheim.iot.model.Report;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.*;


/**
 * Created by Michael on 14.04.2015.
 */
@Slf4j
public class CollectReport implements SimpleAlgorithm {

    private LinkedList<String> machineOrderIds = new LinkedList<>();
    private HashMap<String, ArrayList<Phototransistor>> phototransistors= new HashMap<>();

    @Override
    public void receive(Model model) {
        if(model instanceof MachineOrder){
            machineOrderIds.offer(((MachineOrder) model).getId());
        }
        if(model instanceof Phototransistor) {
            Phototransistor phototransistor = (Phototransistor) model;
            if (!phototransistors.containsKey(phototransistor.getNode())) {
                //First phototransistorState received
                ArrayList<Phototransistor> photostransistorState = new ArrayList<>();
                photostransistorState.add(phototransistor);
                phototransistors.put(phototransistor.getNode(), photostransistorState);
            } else {
                //Second phototransistorState received
                ArrayList<Phototransistor> photostransistorState = phototransistors.get(phototransistor.getNode());
                photostransistorState.add(phototransistor);
            }


            if (phototransistor.getNode().equals("SPSData.S7-1200.Inputs.Phototransistor_Conveyer_BeltSwap")){
                //is last light barrier
                //create ArrayList of passed light barriers
                ArrayList<Timestamp> passedLightBarriers = new ArrayList<>();

                //add only timestamps of parts coming out of the light barrier
                for(String key : phototransistors.keySet()) {
                    ArrayList<Phototransistor> photoTransistorStates =  phototransistors.get(key);
                    if (photoTransistorStates.get(1).getValue()) {
                        passedLightBarriers.add(photoTransistorStates.get(1).getTimestamp());
                        photoTransistorStates.remove(0);
                        photoTransistorStates.remove(1);
                    }else{
                        log.warn("State of "+key+" is not valid");
                    }
                }

                Timestamp startTimeStamp = passedLightBarriers.remove(0);

                //create speed variables
                double speedShaper = 0.0;
                double speedDriller = 0.0;
                String nextMachineOrderId = machineOrderIds.remove();
                //create report
                log.info("Report with machineOrderId: "+nextMachineOrderId+ " created");
                Report report = new Report(nextMachineOrderId, startTimeStamp, passedLightBarriers, speedShaper, speedDriller);
                Rta.getInstance().provideResult(report);
            }
        }

       }

    @Override
    public Class<? extends Model> getModelClass() {
        return Model.class;
    }
}
