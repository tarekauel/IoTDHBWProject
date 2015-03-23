package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EventBean;

import java.util.ArrayList;

/**
 * Created by D059166 on 23.03.2015.
 */
public class Average implements Algorithm {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        System.out.println("avg=" + event.get("avg(timeStamp)"));
    }

    public ArrayList<String> getQueries() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("select avg(timeStamp) from de.dhbw.mannheim.iot.model.Model.win:time(30 sec)");
        return queries;
    }
}
