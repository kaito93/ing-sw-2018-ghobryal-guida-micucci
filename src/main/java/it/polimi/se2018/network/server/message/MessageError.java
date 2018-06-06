package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageError implements MessageSystem {

    String errorMessage;

    @Override
    public void accept(ConnectionClientSocket socket) {
        socket.visit(this);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}