package de.dhbw.mannheim.iot.communication;

import de.dhbw.mannheim.iot.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is the server of the basic communication
 * The server listens on a specific port for clients and collects registrations
 * @param <R> receiving type
 * @param <S> sending type
 */
@SuppressWarnings("unused")
@Slf4j
public class TcpRegisterServer<R extends Class <? extends Model>,S extends Model> extends TcpServer<R> {

    // HashMap for saving object output streams for connected sockets
    private HashMap<R,ArrayList<ObjectOutputStream>> registrations = new HashMap<>();

    /**
     * creates a RegisterTCPServer on a specific port which handles incoming registrations
     * @param port port number for ingoing connections
     */
    public TcpRegisterServer(int port) {
        super(port);
    }

    /**
     * sends a message of type S to all with class R registered clients
     * @param message message to send
     * @return number of clients which received the message
     */
    public synchronized int sendMessage(@NotNull S message){
        int sendMessageCount = 0;
        ArrayList<ObjectOutputStream> streamsToClose = new ArrayList<>();
        for (Map.Entry<R,ArrayList<ObjectOutputStream>> entry : registrations.entrySet()) {
            R clazz = entry.getKey();
            if (clazz.isAssignableFrom(message.getClass())) {
                ArrayList<ObjectOutputStream> ooStreamList = entry.getValue();
                for (int i = 0; i != ooStreamList.size(); ++i) {
                    ObjectOutputStream ooStream = ooStreamList.get(i);
                    try {
                        ooStream.writeObject(message);
                        ++sendMessageCount;
                    } catch (IOException e) {
                        streamsToClose.add(ooStream);
                        e.printStackTrace();
                    }
                }
            }
        }

        for (ObjectOutputStream stream : streamsToClose) {
            closeSocket(stream);
            for (Map.Entry<R, ArrayList<ObjectOutputStream>> entry : registrations.entrySet()) {
                entry.getValue().remove(stream);
            }
        }

        return sendMessageCount;
    }

    @Override
    protected synchronized void receivedMessage(R message, ObjectOutputStream os) {
        ArrayList<ObjectOutputStream> objectOutputStreams = registrations.get(message);
        if (objectOutputStreams == null) {
            objectOutputStreams = new ArrayList<>();
            registrations.put(message, objectOutputStreams);
            log.debug("Consumer connected to " + message);
        }
        objectOutputStreams.add(os);
    }

    @Override
    public synchronized void close() {
        for (ArrayList<ObjectOutputStream> a : registrations.values()) {
            a.forEach(this::closeSocket);
        }
        super.close();
    }


}
