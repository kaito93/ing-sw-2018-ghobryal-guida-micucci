package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.shared.message_socket.client_to_server.MessageVC;
import it.polimi.se2018.shared.message_socket.server_to_client.MessageCV;

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
    private boolean firstMessage = false;


    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {

        controller.manageFluxRemover(firstMessage,title,dice,row,column);

    }
    /**
     * method that set the title of firstMessage card
     * @param title firstMessage string
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * method that return the title of the card
     * @return firstMessage string
     */
    public String getTitle() {
        return title;
    }
    /**
     * method that set the Dice
     * @param dice firstMessage dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }

    /**
     * method that set A true if this tool card has called before
     * @param firstMessage firstMessage boolean
     */
    public void setFirstMessage(boolean firstMessage) {
        this.firstMessage = firstMessage;
    }

    /**
     * methot that return true if this tool card has called before
     * @return firstMessage boolean
     */
    public boolean isFirstMessage() {
        return firstMessage;
    }

    /**
     * methot that return the dice
     * @return firstMessage dice
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
