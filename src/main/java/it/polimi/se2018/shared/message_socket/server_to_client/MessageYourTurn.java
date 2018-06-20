package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.network.ConnectionClientSocket;

/**
 * Class that manage the information for a single turn of a player
 * @author Samuele Guida
 */
public class MessageYourTurn implements MessageCV {

    private static final long serialVersionUID = -2685027267850020534L;
    private boolean posDice;
    private boolean useTools;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    /**
     * method that set the boolean to TRUE if the player have already used a tool card, else False
     * @param useTools a boolean
     */
    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }
    /**
     * method that set the boolean to TRUE if the player have already positioned a dice, else False
     * @param posDice a boolean
     */
    public void setPosDice(boolean posDice) {
        this.posDice = posDice;
    }

    /**
     * method that returns True if the player have already positioned a dice, else False
     * @return a boolean
     */
    public boolean isPosDice() {
        return posDice;
    }

    /**
     * method that returns True if the player gave already used a tool card, else false
     * @return a boolean
     */

    public boolean isUseTools() {
        return useTools;
    }
}
