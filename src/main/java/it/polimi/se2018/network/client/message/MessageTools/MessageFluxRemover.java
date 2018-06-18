package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;

import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

/**
 * class that manage the tool card "Flux Remover"
 * @author Samuele Guida
 */

public class MessageFluxRemover implements MessageCV, MessageVC {

    private static final long serialVersionUID = -7097281702696167500L;

    private String title;
    private Dice dice;
    private int row;
    private int column;
    private boolean a = false;


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

        controller.manageFluxRemover(a,title,dice,row,column);

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
     * method that set A true if this tool card has called before
     * @param a a boolean
     */
    public void setA(boolean a) {
        this.a = a;
    }

    /**
     * methot that return true if this tool card has called before
     * @return a boolean
     */
    public boolean isA() {
        return a;
    }

    /**
     * methot that return the dice
     * @return a dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * method that set the row
     * @param row an integer
     */
    public void setRow(int row) {
        this.row = row;
    }
    /**
     * method that set the column
     * @param column an integer
     */
    public void setColumn(int column) {
        this.column = column;
    }
}
