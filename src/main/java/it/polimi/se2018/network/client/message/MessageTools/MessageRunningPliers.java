package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

/**
 * class that manage the tool card "Running Pliers"
 * @author Samuele Guida
 */

public class MessageRunningPliers implements MessageCV, MessageVC {

    private static final long serialVersionUID = 7135700756609444452L;

    private String title;
    private Dice dice;
    private int rowDest;
    private int columnDest;

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

        controller.manageRunning(title,dice,rowDest,columnDest);

    }
    /**
     * method that set the title of a card
     * @param title a string
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * method that return the title of the card
     * @return a string
     */
    public String getTitle() {
        return title;
    }
    /**
     * method that set the row of the dice
     * @param rowDest an integer
     */
    public void setRowDest(int rowDest) {
        this.rowDest = rowDest;
    }
    /**
     * method that set the dice
     * @param dice arrayList of Dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }
    /**
     * method that set the final column of the dice
     * @param columnDest an integer
     */
    public void setColumnDest(int columnDest) {
        this.columnDest = columnDest;
    }

}
