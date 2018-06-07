package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.ArrayList;

public class MessageFinalScore implements MessageCV {

    private static final long serialVersionUID = 904971391781721581L;
    ArrayList<Player> playersFinal = new ArrayList<>();

    @Override
    public void accept(ConnectionClientSocket client) {

    }

    public void setPlayersFinal(ArrayList<Player> playersFinal) {
        this.playersFinal = playersFinal;
    }

    public ArrayList<Player> getPlayersFinal() {
        return playersFinal;
    }
}
