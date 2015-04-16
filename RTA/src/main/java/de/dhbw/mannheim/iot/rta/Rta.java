package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.communication.TcpRegisterServer;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;
import de.dhbw.mannheim.iot.rta.algorithm.AverageRuntime;
import de.dhbw.mannheim.iot.rta.algorithm.AverageShaperSpeed;
import de.dhbw.mannheim.iot.rta.algorithm.CollectReport;
import de.dhbw.mannheim.iot.rta.algorithm.DifferenceRuntime;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by everybody on 23.03.2015.
 */
public class Rta {

    public static void main(String[] args) {
        Rta.getInstance(MessageQueue.OUTGOING_PORT).registerAlgorithm(new AverageShaperSpeed());
        Rta.getInstance().registerAlgorithm(new CollectReport());
        Rta.getInstance().registerAlgorithm(new DifferenceRuntime());
        Rta.getInstance().registerAlgorithm(new AverageRuntime());
    }

    private static Rta instance;

    public static int OUTGOING_PORT = 3030;

    private EPServiceProvider epService;
    private TcpClient<Model, Class<? extends Model>> tcpClient;
    private TcpRegisterServer<Class<? extends Model>, Model> resultProvider;

    private ArrayList<SimpleAlgorithm> simpleAlgorithms = new ArrayList<>();

    public static Rta getInstance() {
        if (instance == null) throw new IllegalArgumentException("Instance is not built");
        return instance;
    }

    public synchronized static Rta getInstance(int port) {
        if (instance != null) throw new IllegalArgumentException("Instance is already built");
        instance = new Rta("localhost", port);
        return instance;
    }

    public Rta(String ipMessageQueue, int portMessageQueue) {
        epService = EPServiceProviderManager.getDefaultProvider();
        resultProvider = new TcpRegisterServer<>(OUTGOING_PORT);
        tcpClient = new TcpClient<>(ipMessageQueue, portMessageQueue, (Model m) -> {
            routeToEsper(m);
            for (SimpleAlgorithm s : simpleAlgorithms) {
                if (Objects.equals(s.getModelClass(),m.getClass())) {
                    s.receive(m);
                }
            }
        });
        tcpClient.sendMessage(Model.class);
    }

    private void routeToEsper(Model model) {
        epService.getEPRuntime().sendEvent(model);
    }

    public void provideResult(Model model) {
        resultProvider.sendMessage(model);
    }

    public void registerAlgorithm(Algorithm algorithm) {
        EPStatement statement = epService.getEPAdministrator().createEPL(algorithm.getQueries().get(0));
        statement.addListener(algorithm);
    }

    public void registerAlgorithm(SimpleAlgorithm algorithm) {
        simpleAlgorithms.add(algorithm);
    }

}
