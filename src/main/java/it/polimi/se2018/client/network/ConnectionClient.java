package it.polimi.se2018.client.network;

import it.polimi.se2018.shared.message_socket.server_to_client.MessageMV;
import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.shared.message_socket.client_to_server.MessageVC;
import it.polimi.se2018.shared.Observable;
import it.polimi.se2018.shared.Observer;
import it.polimi.se2018.client.view.View;


/**
 * Class ConnectionClient
 * @author Samuele Guida
 * Abstract class that manage the network between client and server. Side Client
 */
public abstract class ConnectionClient extends Observable<MessageMV> implements Observer<MessageVC> {

    protected String ip;
    protected int port;
    protected View view;
    protected String username;

    /**
     * abstract method that create a network with the server
     */
    public abstract void run();

    /**
     * abstract method that send the choice of set a dice
     * @param dice the dice chosen
     * @param column the column where set the dice
     * @param row the row where set the dice
     */
    public abstract void sendPosDice (Dice dice, int column, int row);

    /**
     * abstract method that send the choice of use a tool card
     * @param titleCardTool the title of the tool card
     */
    public abstract void sendUseTool(String titleCardTool);

    /**
     * method that set the username of this player
     * @param username username's player
     */
    public void setUsername(String username){
        this.username= username;
    }

    /**
     * abstact method that send a empty move
     */
    public abstract void sendPassMove ();

    /**
     * abstract method that send a request to reconnect to the game
     */
    public abstract void sendReconnect();

    /**
     * abstact method that send a message_socket of inactivity
     */
    public abstract void sendDisconnect();
}
