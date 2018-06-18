package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

/**
 * class that manage the tool card "Grinding Stone"
 * @author Samuele Guida
 */

public class MessageGrindingStone implements MessageCV, MessageVC {

    private static final long serialVersionUID = 2826884578624514094L;

    private String title;
    private Dice dice;
    private Dice diceBefore;
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

       controller.manageGrinding(title,dice,row,column,diceBefore);


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
     * method that set the column
     * @param column an integer
     */
    public void setColumn(int column) {
        this.column = column;
    }
    /**
     * method that set the row
     * @param row an integer
     */
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * method that set the dice before it has been modified
     * @param diceBefore a dice
     */
    public void setDiceBefore(Dice diceBefore) {
        this.diceBefore = diceBefore;
    }
}
