package de.dhbw.mannheim.iot.rta;

import de.dhbw.mannheim.iot.model.MachineOrder;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.Phototransistor;
import de.dhbw.mannheim.iot.model.Report;
import jdk.nashorn.internal.objects.NativeArray;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by Michael on 14.04.2015.
 */
public class CollectReport implements SimpleAlgorithm{

    private String nextMachineOrderId="";
    private int receivedPhotoTransistorsCnt=0;
    private HashMap<String, Phototransistor[]> phototransistors= new HashMap<>();

    @Override
    public void receive(Model model) {
        receivedPhotoTransistorsCnt++;
        if(model instanceof MachineOrder){
            nextMachineOrderId = ((MachineOrder) model).getId();
        }
        if(model instanceof Phototransistor){
            Phototransistor phototransistor= (Phototransistor)model;
            if(!phototransistors.containsKey(phototransistor.getNODE())){
                //First phototransistorState received
                Phototransistor[] photostransistorState= new Phototransistor[2];
                photostransistorState[0]= phototransistor;
                phototransistors.put(phototransistor.getNODE(),photostransistorState);
            }else{
                //Second phototransistorState received
                Phototransistor[] photostransistorState = phototransistors.get(phototransistor.getNODE());
                photostransistorState[1]=phototransistor;
            }
        }

        if(receivedPhotoTransistorsCnt>=10){
            //create ArrayList of passed light barriers
            ArrayList<Timestamp> passedLightBarriers= new ArrayList<>();

            //add only timestamps of parts coming out of the light barrier
            phototransistors.forEach((key,value) -> passedLightBarriers.add(value[1].getTIMESTAMP()));
            Timestamp startTimeStamp= passedLightBarriers.remove(0);

            //create speed variables
            double speedShaper =0.0;
            double speedDriller=0.0;

            //create report
            Report  report = new Report(nextMachineOrderId,startTimeStamp,passedLightBarriers,speedShaper,speedDriller);
        }

       }

    @Override
    public Class<? extends Model> getModelClass() {
        return Model.class;
    }
}
