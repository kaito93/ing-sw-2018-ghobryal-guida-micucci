package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

public class MessageGrozingPliers implements MessageCV, MessageVC {

    private static final long serialVersionUID = 5303406161628410297L;

    private String title;
    private Dice dice;
    private int rowDest;
    private int colDest;


    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

        controller.manageGrozing(title,dice,rowDest,colDest);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setRowDest(int rowDest) {
        this.rowDest = rowDest;
    }

    public void setColDest(int colDest) {
        this.colDest = colDest;
    }

}
