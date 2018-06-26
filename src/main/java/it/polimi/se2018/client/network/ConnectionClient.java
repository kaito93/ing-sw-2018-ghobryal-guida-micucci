package it.polimi.se2018.client.network;

import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.client.view.View;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Class ConnectionClient
 * @author Samuele Guida
 * Abstract class that manage the network between client and server. Side Client
 */
public interface ConnectionClient extends Remote {

    /**
     * abstract method that create a network with the server
     */
    void run() throws RemoteException;

    /**
     * abstract method that send the choice of set a dice
     * @param dice the dice chosen
     * @param column the column where set the dice
     * @param row the row where set the dice
     */
    void sendPosDice (Dice dice, int column, int row) throws RemoteException;

    /**
     * abstract method that send the choice of use a tool card
     * @param titleCardTool the title of the tool card
     */
    void sendUseTool(String titleCardTool) throws RemoteException;

    /**
     * method that set the username of this player
     * @param username username's player
     */
    void setUsername(String username) throws RemoteException;

    /**
     * abstact method that send a empty move
     */
    void sendPassMove () throws RemoteException;

    /**
     * abstract method that send a request to reconnect to the game
     */
    void sendReconnect() throws RemoteException;

    /**
     * abstact method that send a message_socket of inactivity
     */
    void sendDisconnect() throws RemoteException;

    String getUsername() throws RemoteException;

    View getView() throws RemoteException;
}
