package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EventBean;
import com.google.gson.Gson;
import de.dhbw.mannheim.iot.model.AverageRuntimeResult;
import de.dhbw.mannheim.iot.model.DifferenceRuntimeResult;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author Tarek Auel
 * @since April 14, 2015.
 */
@Slf4j
public class AverageRuntime implements Algorithm {

    Gson gson = new Gson();

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        double actualRuntime = (Double) event.get("avg(actualRuntime)");
        double difference = (Double) event.get("avg(difference)");
        double expectedRuntime = (Double) event.get("avg(expectedRuntime)");
        AverageRuntimeResult average = new AverageRuntimeResult(actualRuntime,difference,expectedRuntime);
        log.trace("Result: " + gson.toJson(average));
        Rta.getInstance().provideResult(average);
    }

    public ArrayList<String> getQueries() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("select avg(actualRuntime), avg(difference), avg(expectedRuntime) from " + DifferenceRuntimeResult.class.getCanonicalName() + ".win:time(30 sec)");
        return queries;
    }
}
