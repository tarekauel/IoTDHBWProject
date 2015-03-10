package de.dhbw.mannheim.iot.ia;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.MonitoredDataItem;
import com.prosysopc.ua.client.Subscription;
import com.prosysopc.ua.client.UaClient;
import de.dhbw.mannheim.iot.model.Model;
import org.apache.log4j.Logger;
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
public abstract class OPC extends InputAdapter {

    private static final Logger logger = Logger.getLogger(OPC.class);

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

    private UaClient client;

    public OPC (String ipMessageQueue, int portMessageQueue, String opcUrl) {
        super(ipMessageQueue, portMessageQueue);
        connectToMachine(opcUrl);
    }

    private void connectToMachine(String opcUrl) {
        try {
            client = new UaClient(opcUrl);
            //TODO do we care about security?
            client.setSecurityMode(SecurityMode.NONE);
            OPC.initializeClient(client);
            client.connect();
            subscribe();
        } catch (URISyntaxException | ServiceException e) {
            logger.error(e.getMessage());
        }
    }

    private void subscribe() {
        NodeId target = new NodeId(getNodeNameSpaceIndex(), getNodeName());
        Subscription subscription = new Subscription();
        MonitoredDataItem item = new MonitoredDataItem(target, Attributes.Value, MonitoringMode.Reporting);

        item.setDataChangeListener((MonitoredDataItem monitoredDataItem, DataValue dataValue, DataValue dataValue1) -> {
            sendToMessageQueue(transform(dataValue));
        });

        try {
            subscription.addItem(item);
            client.addSubscription(subscription);
        } catch (ServiceException | StatusException e) {
            logger.error(e.getMessage());
        }
    }

    protected abstract String getNodeName();

    protected abstract int getNodeNameSpaceIndex();

    protected abstract Model transform(DataValue dataValue);

}
