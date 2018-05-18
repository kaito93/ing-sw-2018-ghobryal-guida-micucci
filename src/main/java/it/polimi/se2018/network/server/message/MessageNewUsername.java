package it.polimi.se2018.network.server.message;

import com.sun.xml.internal.ws.wsdl.writer.document.Message;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageNewUsername implements MessageSystem {

    @Override
    public void accept(ConnectionClientSocket socket) {
        socket.requestNewUsername();
    }
}
