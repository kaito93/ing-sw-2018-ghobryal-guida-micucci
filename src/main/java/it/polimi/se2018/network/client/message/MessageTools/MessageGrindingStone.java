package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

public class MessageGrindingStone implements MessageCV, MessageVC {

    private static final long serialVersionUID = 2826884578624514094L;

    private String title;
    private Dice dice;
    private Dice diceBefore;
    private int row;
    private int column;


    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

       controller.manageGrinding(title,dice,row,column,diceBefore);


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

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setDiceBefore(Dice diceBefore) {
        this.diceBefore = diceBefore;
    }
}
