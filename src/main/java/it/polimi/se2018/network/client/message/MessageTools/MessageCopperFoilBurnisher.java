package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

public class MessageCopperFoilBurnisher implements MessageCV, MessageVC {
    private static final long serialVersionUID = -2847811478201413002L;


    private String title;
    private Dice dice;
    private int rowDest;
    private int columnDest;
    private int rowMit;
    private int columnMit;

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

       controller.manageCopper(title,dice,rowDest,columnDest,rowMit,columnMit);

    }

    public void setRow(int row) {
        this.rowDest = row;
    }

    public void setColumn(int column) {
        this.columnDest = column;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColumnMit(int columnMit) {
        this.columnMit = columnMit;
    }

    public void setRowMit(int rowMit) {
        this.rowMit = rowMit;
    }

    public String getTitle() {
        return title;
    }
}
