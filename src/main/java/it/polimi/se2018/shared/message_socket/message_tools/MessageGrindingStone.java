package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * class that manage the tool card "Grinding Stone"
 * @author Samuele Guida
 */

public class MessageGrindingStone extends MessageTool  {

    private static final long serialVersionUID = 2826884578624514094L;


    private Dice diceBefore;


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

       controller.manageGrinding(title,dice,rowDest,columnDest,diceBefore);


    }
    /**
     * method that set the dice before it has been modified
     * @param diceBefore a dice
     */
    public void setDiceBefore(Dice diceBefore) {
        this.diceBefore = diceBefore;
    }
}
