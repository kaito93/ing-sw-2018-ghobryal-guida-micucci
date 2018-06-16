package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageFinalGame implements MessageSystem{

    private static final long serialVersionUID = 3911166895507190888L;
    private String message;

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
