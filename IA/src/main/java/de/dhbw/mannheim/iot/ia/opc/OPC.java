package de.dhbw.mannheim.iot.ia.opc;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;
import de.dhbw.mannheim.iot.ia.InputAdapter;
import de.dhbw.mannheim.iot.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.opcfoundation.ua.builtintypes.DataValue;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.MonitoringMode;
import org.opcfoundation.ua.transport.security.SecurityMode;

import java.net.URISyntaxException;
import java.util.Locale;

/**
 * Created by Marc on 10.03.2015.
 */
@Slf4j
public abstract class OPC extends InputAdapter {

    private static void initializeClient(UaClient client) {
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setApplicationName(new LocalizedText("DHBW Client", Locale.GERMAN));

        appDescription.setApplicationUri("urn:localhost:UA:DHBWClient");
        appDescription.setProductUri("urn:prosysopc.com:UA:DHBWClient");
        appDescription.setApplicationType(ApplicationType.Client);

        final ApplicationIdentity identity = new ApplicationIdentity();
        identity.setApplicationDescription(appDescription);
        client.setApplicationIdentity(identity);
    }
    public final String NODE_NAME;
    private UaClient client;

    public OPC (String ipMessageQueue, int portMessageQueue, String opcUrl, String nodeName) {
        super(ipMessageQueue, portMessageQueue);
        NODE_NAME = nodeName;
        connectToMachine(opcUrl);
    }

    private void connectToMachine(String opcUrl) {
        try {
            client = new UaClient(opcUrl);
            client.setSecurityMode(SecurityMode.NONE);
            OPC.initializeClient(client);
            client.connect();
            log.info("Connected to machine at " + opcUrl);
            subscribe();
        } catch (URISyntaxException | ServiceException e) {
            log.error("Couldn't connect to machine: " + e.getMessage());
        }
    }

    private void subscribe() {
        NodeId target = new NodeId(getNodeNameSpaceIndex(), NODE_NAME);
        Subscription subscription = new Subscription();
        MonitoredDataItem item = new MonitoredDataItem(target, Attributes.Value, MonitoringMode.Reporting);

        item.setDataChangeListener((MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1) -> {
            sendToMessageQueue(transform(dataValue));
        });

        log.info("Subscribed to node " + NODE_NAME);

        try {
            subscription.addItem(item);
            client.addSubscription(subscription);
        } catch (ServiceException | StatusException e) {
            log.error("Couldn't subscribe to node: " + e.getMessage());
        }
    }

    protected abstract int getNodeNameSpaceIndex();

    protected abstract Model transform(DataValue dataValue);

}
