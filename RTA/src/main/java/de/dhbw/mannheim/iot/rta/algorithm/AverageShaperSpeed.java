package de.dhbw.mannheim.iot.rta.algorithm;

import com.espertech.esper.client.EventBean;
import de.dhbw.mannheim.iot.model.result.AverageShaperSpeedResult;
import de.dhbw.mannheim.iot.model.machine.Report;
import de.dhbw.mannheim.iot.rta.Algorithm;
import de.dhbw.mannheim.iot.rta.Rta;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
@Slf4j
public class AverageShaperSpeed implements Algorithm {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        double average = (Double) event.get("avg(speedShaperRPM)");
        log.info("avg=" + average);
        Rta.getInstance().provideResult(new AverageShaperSpeedResult(average));
    }

    public ArrayList<String> getQueries() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("select avg(speedShaperRPM) from " + Report.class.getCanonicalName() + ".win:time(30 sec)");
        return queries;
    }

}
