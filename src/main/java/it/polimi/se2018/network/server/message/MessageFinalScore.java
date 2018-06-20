package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manage the final score of all players at the end of the game
 * @author Samuele Guida
 */
public class MessageFinalScore implements MessageCV {

    private static final long serialVersionUID = 904971391781721581L;
    private List<Player> playersFinal = new ArrayList<>();

    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {

    }

    /**
     * method that set the players in the ArrayList
     * @param playersFinal the Arraylist of players
     */
    public void setPlayersFinal(List<Player> playersFinal) {
        this.playersFinal = playersFinal;
    }
}
