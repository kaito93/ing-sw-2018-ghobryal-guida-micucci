package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.client.network.ConnectionClientSocket;


/**
 * class that manage the tool card "Lens Cutter"
 * @author Samuele Guida
 */

public class MessageLensCutter extends MessageTool{

    private static final long serialVersionUID = -467044401650037099L;

    private Dice diceStock;
    private int numberRound;
    private Dice diceRound;

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

        controller.manageLens(title,diceStock,numberRound,rowDest,columnDest,diceRound);


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

}
