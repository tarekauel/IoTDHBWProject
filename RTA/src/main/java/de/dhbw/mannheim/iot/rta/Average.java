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
        log.trace("avg=" + event.get("avg(value)"));
    }

    public ArrayList<String> getQueries() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("select avg(value) from de.dhbw.mannheim.iot.model.LongValue.win:time(30 sec)");
        return queries;
    }
}
