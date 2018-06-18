package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

/**
 * class that manage the tool card "Grozing Pliers"
 * @author Samuele Guida
 */

public class MessageGrozingPliers implements MessageCV, MessageVC {

    private static final long serialVersionUID = 5303406161628410297L;

    private String title;
    private Dice dice;
    private int row;
    private int column;

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

        controller.manageGrozing(title,dice,row,column);
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
     * method that set the Dice
     * @param dice a dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }
    /**
     * method that set the row
     * @param rowDest an integer
     */
    public void setRowDest(int rowDest) {
        this.row = rowDest;
    }
    /**
     * method that set the column
     * @param colDest an integer
     */
    public void setColDest(int colDest) {
        this.column = colDest;
    }

}
