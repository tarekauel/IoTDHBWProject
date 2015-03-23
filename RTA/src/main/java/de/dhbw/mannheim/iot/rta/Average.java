package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EventBean;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Created by D059166 on 23.03.2015.
 */
@Slf4j
public class Average implements Algorithm {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        log.trace("avg=" + event.get("avg(timeStamp)"));
    }

    public ArrayList<String> getQueries() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("select avg(timeStamp) from de.dhbw.mannheim.iot.model.Model.win:time(1 sec)");
        return queries;
    }
}
