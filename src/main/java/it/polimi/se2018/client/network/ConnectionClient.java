package it.polimi.se2018.client.network;

import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.client.view.View;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Class ConnectionClient
 * Abstract class that manage the network between client and server. Side Client
 * @author Samuele Guida, Anton Ghobryal
 */
public interface ConnectionClient extends Remote, Serializable {

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

    int receiveMapConn(List<Cell[][]> cells, List<String> names, List<Integer> fav) throws RemoteException;

    void viewPrivateCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException;

    void viewPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTool, List<String> descriptionTool, List<Integer> publicScore) throws RemoteException;

    void  viewScore(List<Integer> finalScore) throws RemoteException;

    void isTurn(boolean dice, boolean tool) throws RemoteException;

    void requestNewUsernameRMI() throws RemoteException;

    void receiveUpdate(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                       RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors) throws RemoteException;

    void tapWheel(String title) throws RemoteException;

    void copperFoil(String title) throws RemoteException;

    void corkbacked(String title) throws RemoteException;

    void eglomise(String title) throws RemoteException;

    void fluxBrush(String title) throws RemoteException;

    Dice fluxRemover() throws RemoteException;

    void fluxRemover2(String title, Dice dice) throws RemoteException;

    void grinding(String title) throws RemoteException;

    void grozing(String title) throws RemoteException;

    void lathekin(String title) throws RemoteException;

    void lens(String title) throws RemoteException;

    void running(String title) throws RemoteException;

    void handleError(String error) throws RemoteException;

    void receiveLostConnection(String text, int index) throws RemoteException;
}
