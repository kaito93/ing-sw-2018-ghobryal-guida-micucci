package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Dice;

import it.polimi.se2018.network.client.message.MessageTools.*;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Observable;
import it.polimi.se2018.util.Observer;
import it.polimi.se2018.view.View;


/**
 * Class ConnectionClient
 * @author Samuele Guida
 * Abstract class that manage the connection between client and server. Side Client
 */
public abstract class ConnectionClient extends Observable<MessageMV> implements Observer<MessageVC> {

    String ip;
    int port;
    View view;
    String username;

    /**
     * abstract method that create a connection with the server
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
     * abstract method that send the choise of use a tool card
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

    public abstract void sendPassMove ();

    public abstract void sendReconnect();

    public abstract void sendDisconnect();
}
