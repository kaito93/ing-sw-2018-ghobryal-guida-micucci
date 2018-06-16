package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

public class MessageFluxRemover implements MessageCV, MessageVC {

    private static final long serialVersionUID = -7097281702696167500L;

    private String title;
    private Dice dice;
    private int row;
    private int column;
    private boolean a = false;

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

        controller.manageFluxRemover(a,title,dice,row,column);

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

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isA() {
        return a;
    }

    public Dice getDice() {
        return dice;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
