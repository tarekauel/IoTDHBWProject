package de.dhbw.mannheim.iot.rta.algorithm;

import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.db.DBStarter;
import de.dhbw.mannheim.iot.db.Request;
import de.dhbw.mannheim.iot.model.result.DifferenceRuntimeResult;
import de.dhbw.mannheim.iot.model.machine.MachineOrder;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.model.machine.Report;
import de.dhbw.mannheim.iot.rta.Rta;
import de.dhbw.mannheim.iot.rta.SimpleAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

/**
 * @author Tarek Auel
 * @since April 13, 2015.
 */
@Slf4j
public class DifferenceRuntime implements SimpleAlgorithm {

    private HashMap<String, Report> cachedReports = new HashMap<>();

    private final int speedFactor = 10;

    TcpClient<List<MachineOrder>,Request> tcpClient = new TcpClient<>("localhost",DBStarter.INGOING_PORT,(List<MachineOrder>list)->{
        MachineOrder mo = list.get(0);
        Report r = cachedReports.get(mo.getId());
        long runtime = r.getPassedLightBarrier().get(r.getPassedLightBarrier().size() - 1).getTime() - r.getStartTime().getTime();
        long expectedRuntime = (long) (mo.getPlannedSeconds() / speedFactor);
        long difference = runtime - expectedRuntime;
        log.info(mo.getId() + ": Expected Runtime " +  expectedRuntime + "ms actual runtime: " + runtime + "ms difference: " + difference + "ms");
        Rta.getInstance().provideResult(new DifferenceRuntimeResult(mo.getId(), expectedRuntime, runtime, difference));
    });

    @Override
    public void receive(Model m) {
        Report report = (Report) m;
        cachedReports.put(report.getMachineOrderId(), report);
        HashMap<String, Object> props = new HashMap<>();
        props.put("id", report.getMachineOrderId());
        tcpClient.sendMessage(new Request(MachineOrder.class, props));
    }

    @Override
    public Class<? extends Model> getModelClass() {
        return Report.class;
    }
}
