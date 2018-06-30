package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;

import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * class that manage the tool card "Cork Backed Straightedge"
 * @author Samuele Guida
 */

public class MessageCorkBackedStraightedge extends MessageTool {
    private static final long serialVersionUID = 1035941991104856811L;

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

        controller.manageCork(title,dice,rowDest,columnDest);
    }
}
