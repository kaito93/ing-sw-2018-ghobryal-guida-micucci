package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClient;

import java.util.ArrayList;

public class MessageFinalScore implements MessageCV {

    ArrayList<Player> playersFinal = new ArrayList<>();

    @Override
    public void accept(ConnectionClient client) {

    }

    public void setPlayersFinal(ArrayList<Player> playersFinal) {
        this.playersFinal = playersFinal;
    }

    public ArrayList<Player> getPlayersFinal() {
        return playersFinal;
    }
}
