package it.polimi.se2018.server.network;


import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * abstract class that manage the connections between Server and Client server side
 * @author Samuele Guida
 */
public interface ConnectionServer extends Remote,Serializable {


    /**
     * abstract method used for send an object from Server to Client
     * @param message an object
     */
    void send (Object message) throws RemoteException;

    /**
     * method that set the username of a player
     * @param username a string
     */
    void setUsername(String username) throws RemoteException;

    /**
     * method that returns the username of this connections
     * @return a string
     */
    String getUsername() throws RemoteException;

    /**
     * abstract method that returns a specific inputstream
     * @return an object
     */
    ObjectInputStream getInput() throws RemoteException;

    /**
     * method that set the maps to send to the players
     * @param player a player
     */
    void sendMap(Player player) throws RemoteException;

    /**
     * abstract method that send the maps set before
     * @param maps an arraylist of maps
     * @param player a player
     */
    void sendMapConn(List<Map> maps, Player player) throws RemoteException;

    /**
     * abstract method that send the private information of a player
     * @param card private objective card
     */
    void sendPrivateInformation(PrivateObjectiveCard card) throws RemoteException;

    /**
     * abstract method that send the public information of the game
     * @param cards arraylist of public objective cards
     * @param tools arraylist of tool cards
     */
    void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools) throws RemoteException;

    /**
     * abstract method that send a warning to other players that a player has left the game for disconnection
     * @param text a string
     * @param index new index of player in the array
     */
    void sendLostConnection (String text,int index) throws RemoteException;

    /**
     * abstract method that listen request of reconnection of a disconnected player
     */
    void tryReconnect() throws RemoteException;

    /**
     * abstract method that send the final message_socket to a player
     * @param players a player
     */
    void sendFinalPlayers(List<Player> players) throws RemoteException;

    /**
     * abstract methot that send the information of a turn for a player
     * @param dice boolean TRUE if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else false
     */
    void sendIsYourTurn (boolean dice, boolean tool) throws RemoteException;

    /**
     * abstract method that send a error to a player at the choose of username
     */
    void sendErrorUser() throws RemoteException;

    /**
     * abstract method that send update of the client with a new news
     * @param maps an arraylist of maps of all players in game
     * @param users an arraylist of string of username of all players in game
     * @param message a generic message_socket
     * @param tools an arraylist of boolean that manage the use of tool cards
     * @param roundSchemeMap a matrix of the round scheme map
     * @param stock a matrix of dices
     * @param favor an arraylist of integer
     */
    void sendUpdate(List<Map> maps, List<String> users, String message, List<Boolean> tools,
                    RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favor) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "Copper Foil Burnisher"
     * @param title the title of this card
     */
    void manageCopper(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Cork Backed Straiightedge"
     * @param title the title of this card
     */
    void manageCork(String title) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "CEglomise Brush"
     * @param title the title of this card
     */
    void manageEglomise(String title) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "Flux Brush"
     * @param title the title of this card
     */
    void manageFluxBrush(String title) throws RemoteException;

    /**
     * abstract method that manage the first send for the tool card "Flux Remover"
     * @param title the title of this card
     */
    void manageFluxRemover(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Grinding Stone"
     * @param title the title of this card
     */
     void manageGrinding(String title) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "Grozing Pliers"
     * @param title the title of this card
     */
    void manageGrozing(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Lathekin"
     * @param title the title of this card
     */
    void manageLathekin(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Lens Cutter"
     * @param title the title of this card
     */
    void manageLens(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Running Pliers"
     * @param title the title of this card
     */
    void manageRunning(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Tap Wheel
     * @param title the title of this card
     */
    void manageTap(String title) throws RemoteException;

    /**
     * abstract method that send a generic error to the client
     * @param error a string
     */
    void manageError(String error) throws RemoteException;
    /**
     * abstract method that manage the second send for the tool card "Flux Remover"
     * @param title the title of this card
     */
    void manageFluxRemover2(Dice dice, String title) throws RemoteException;

    /**
     * abstract method that send a communication of victory with disconnection of others players
     */
    void sendVictoryAbbandon() throws RemoteException;

    /**
     * abstract method that close the network between server and client
     */
    void closeConnection() throws RemoteException;

    /**
     * method that returns if the network is active
     * @return a boolean
     */
    boolean isConnected() throws RemoteException;

    /**
     * abstract method that send a news: a player has been reconnected
     * @param text a string of text
     */
   void sendGainConnection(String text) throws RemoteException;

    /**
     * abstract method that send a confirm of reconnection
     * @param text a string
     * @param index an integer for manage the new index of this player
     */
    void sendAcceptReconnection(String text, int index) throws RemoteException;

    void setDisconnected() throws RemoteException;

    void setvView(VirtualView vView) throws RemoteException;

    ConnectionServer cloneObj() throws RemoteException;

    void setClientRMI(ConnectionClient stub, String username) throws RemoteException;

    boolean receiveMessage() throws RemoteException;

    void tapSet(String title, Dice roundSchemeDice, List<Dice> dicesToMove, int row1Mit, int row2Mit,
                int column1Mit, int column2Mit, int row1Dest, int row2Dest, int column1Dest, int column2Dest,
                int roundSchemeDicePos) throws RemoteException;

    void coppperSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) throws RemoteException;

    void corkSet(String title, Dice dice, int rowDest, int columnDest) throws RemoteException;

    void eglomiseSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) throws RemoteException;

    void fluxBrushSet(String title, Dice dice, int rowDest, int cloumnDest, Dice diceBefore) throws RemoteException;

    void fluxRemoverSet(String title, Dice dice, int row, int column) throws RemoteException;

    void grindingSet(String title, Dice dice, int row, int column, Dice diceBefore) throws RemoteException;

    void grozingSet(String title, Dice dice, int rowDest, int colDest) throws RemoteException;

    void lathekinSet(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest, int col1Dest,
                     List<Dice> dicesToMove, int row2Dest, int col2Dest) throws RemoteException;

    void lensSet(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound) throws RemoteException;

    void runningSet(String title, Dice dice, int rowDest, int colDest) throws RemoteException;

    void posDice(Dice dice, int row, int column) throws RemoteException;

    void useTool(String title) throws RemoteException;

    void passTurn() throws RemoteException;


}
