package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

/**
 * class that manage the tool card "Lens Cutter"
 * @author Samuele Guida
 */

public class MessageLensCutter implements MessageCV, MessageVC {

    private static final long serialVersionUID = -467044401650037099L;

    private String title;
    private Dice diceStock;
    private int numberRound;
    private Dice diceRound;
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

        controller.manageLens(title,diceStock,numberRound,row,column,diceRound);


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
     * method that set the dice from Round Scheme
     * @param diceRound a dice
     */
    public void setDiceRound(Dice diceRound) {
        this.diceRound = diceRound;
    }

    /**
     * method that set the dice from the stock
     * @param diceStock a dice
     */
    public void setDiceStock(Dice diceStock) {
        this.diceStock = diceStock;
    }

    /**
     * method that set the number of round where the dice choose is been
     * @param numberRound an integer
     */
    public void setNumberRound(int numberRound) {
        this.numberRound = numberRound;
    }
    /**
     * method that set the final column of the dice
     * @param column an integer
     */
    public void setColumn(int column) {
        this.column = column;
    }
    /**
     * method that set the row of the dice
     * @param row an integer
     */
    public void setRow(int row) {
        this.row = row;
    }
}
