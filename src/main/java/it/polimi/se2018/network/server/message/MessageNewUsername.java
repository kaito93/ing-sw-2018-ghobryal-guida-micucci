package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageNewUsername implements MessageSystem {

    private static final long serialVersionUID = -6694182992571968678L;

    @Override
    public void accept(ConnectionClientSocket socket) {
        socket.requestNewUsername();
    }
}
