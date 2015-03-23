package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;
import de.dhbw.mannheim.iot.mq.MessageQueue;

/**
 * Created by everybody on 23.03.2015.
 */
public class Rta {

    public static void main(String[] args) {
        Rta.getInstance(MessageQueue.OUTGOING_PORT).registerAlgorithm(new Average());
    }

    private static Rta instance;

    private EPServiceProvider epService;
    private TcpClient<Model, Class<? extends Model>> tcpClient;

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
        tcpClient = new TcpClient<>(ipMessageQueue, portMessageQueue, (Model m) -> {
           routeToEsper(m);
        });
        tcpClient.sendMessage(Model.class);
    }

    private void routeToEsper(Model model) {
        epService.getEPRuntime().sendEvent(model);
    }

    public void registerAlgorithm(Algorithm algorithm) {
        EPStatement statement = epService.getEPAdministrator().createEPL(algorithm.getQueries().get(0));
        statement.addListener(algorithm);
    }

}
