package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageFinalGame implements MessageSystem{

    String message;

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
