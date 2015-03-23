package de.dhbw.mannheim.iot.rta;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import de.dhbw.mannheim.iot.communication.TcpClient;
import de.dhbw.mannheim.iot.model.Model;

/**
 * Created by everybody on 23.03.2015.
 */
public class Rta {

    public static void main(String[] args) {
        Rta.getInstance().registerAlgorithm(new Average());
    }

    private static Rta instance = new Rta("", 0);

    private EPServiceProvider epService;
    private TcpClient<Model, Class<? extends Model>> tcpClient;

    public static Rta getInstance() {
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
