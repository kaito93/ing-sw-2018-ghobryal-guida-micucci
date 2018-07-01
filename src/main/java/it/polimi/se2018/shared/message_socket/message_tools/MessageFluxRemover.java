package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * class that manage the tool card "Flux Remover"
 * @author Samuele Guida
 */

public class MessageFluxRemover extends MessageTool {

    private static final long serialVersionUID = -7097281702696167500L;


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

        if (!firstMessage)
            controller.manageFluxRemover(title,dice,rowDest,columnDest);
        else
            controller.manageFluxRemoverExtractDice(title,dice);
    }

    /**
     * method that set A true if this tool card has called before
     * @param firstMessage firstMessage boolean
     */
    public void setFirstMessage(boolean firstMessage) {
        this.firstMessage = firstMessage;
    }

    /**
     * method that return true if this tool card has been called before
     * @return firstMessage boolean
     */
    public boolean isFirstMessage() {
        return firstMessage;
    }

    /**
     * method that return the dice
     * @return firstMessage dice
     */
    public Dice getDice() {
        return dice;
    }

}
