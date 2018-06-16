package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

public class MessageFluxBrush implements MessageCV, MessageVC {

    private static final long serialVersionUID = -8911804813757217433L;


    private String title;
    private Dice dice;
    private Dice diceBefore;
    private int rowDest;
    private int columnDest;

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

        controller.manageFluxBrush(title,dice,rowDest,columnDest,diceBefore);

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

    public void setColumnDest(int columnDest) {
        this.columnDest = columnDest;
    }

    public void setDiceBefore(Dice diceBefore) {
        this.diceBefore = diceBefore;
    }
}
