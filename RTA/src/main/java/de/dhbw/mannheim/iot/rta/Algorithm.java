package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import java.util.ArrayList;

/**
 * Created by D059166 on 23.03.2015.
 */
public interface Algorithm extends UpdateListener {

    public void update(EventBean[] newEvents, EventBean[] oldEvents);

    public ArrayList<String> getQueries();

}
