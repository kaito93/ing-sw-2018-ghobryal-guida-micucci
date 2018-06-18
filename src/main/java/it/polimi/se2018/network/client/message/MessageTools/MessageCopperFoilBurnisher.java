package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

/**
 * class that manage the tool card "Copper Foil Burnisher"
 * @author Samuele Guida
 */

public class MessageCopperFoilBurnisher implements MessageCV, MessageVC {
    private static final long serialVersionUID = -2847811478201413002L;


    private String title;
    private Dice dice;
    private int rowDest;
    private int columnDest;
    private int rowMit;
    private int columnMit;

    /**
     * method that accept this message client side
     * @param client connection socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that accept this message server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {

       controller.manageCopper(title,dice,rowDest,columnDest,rowMit,columnMit);

    }

    /**
     * method that set the row
     * @param row an integer
     */
    public void setRow(int row) {
        this.rowDest = row;
    }

    /**
     * method that set the column
     * @param column an integer
     */
    public void setColumn(int column) {
        this.columnDest = column;
    }

    /**
     * method that set the Dice
     * @param dice a dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * method that set the title of a card
     * @param title a string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * method that set the initial column
     * @param columnMit an integer
     */
    public void setColumnMit(int columnMit) {
        this.columnMit = columnMit;
    }

    /**
     * method that set the initial row
     * @param rowMit an integer
     */
    public void setRowMit(int rowMit) {
        this.rowMit = rowMit;
    }

    /**
     * method that return the title of the card
     * @return a string
     */
    public String getTitle() {
        return title;
    }
}
