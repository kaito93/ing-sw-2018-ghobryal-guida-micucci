package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageError implements MessageSystem {

    private static final long serialVersionUID = 2509917442158095155L;
    private String errorMessage;

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
