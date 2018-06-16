package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessagePlayerDisconnect implements MessageSystem {

    private static final long serialVersionUID = 1948182074052958936L;
    private String message;
    private int index;

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
