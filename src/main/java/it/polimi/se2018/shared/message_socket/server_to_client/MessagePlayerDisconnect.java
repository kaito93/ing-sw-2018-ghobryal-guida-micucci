package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.shared.message_socket.MessageSystem;

/**
 * Class that manage the warning that a player has been disconnected from the game
 * @author Samuele Guida
 */
public class MessagePlayerDisconnect implements MessageSystem {

    private static final long serialVersionUID = 1948182074052958936L;
    private String message;
    private int index;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }
    /**
     * method that set a string message_socket
     * @param message a string
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * method that return the message_socket from server
     * @return a string
     */
    public String getMessage() {
        return message;
    }

    /**
     * method that set the new index of this player
     * @param index an integer
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * method that return the index of this player
     * @return an integer
     */
    public int getIndex() {
        return index;
    }
}

