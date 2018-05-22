package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageNewUsername implements MessageSystem {

    @Override
    public void accept(ConnectionClientSocket socket) {
        socket.requestNewUsername();
    }
}
