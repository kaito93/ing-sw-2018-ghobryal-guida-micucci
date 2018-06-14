package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessagePlayerDisconnect implements MessageSystem {

    String message;
    int index;

    @Override
    public void accept(ConnectionClientSocket socket) {
        socket.visit(this);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
