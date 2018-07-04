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
 * @author Samuele Guida, Anton Ghobryal
 */
public interface ConnectionServer extends Remote,Serializable {


    /**
     * abstract method used for send an object from Server to Client
     * @param message an object
     * @throws RemoteException if the stub is no longer online
     */
    void send (Object message) throws RemoteException;

    /**
     * method that set the username of a player
     * @param username a string
     * @throws RemoteException if the stub is no longer online
     */
    void setUsername(String username) throws RemoteException;

    /**
     * method that returns the username of this connections
     * @return a string
     * @throws RemoteException if the stub is no longer online
     */
    String getUsername() throws RemoteException;

    /**
     * abstract method that returns a specific inputstream
     * @return an object
     * @throws RemoteException if the stub is no longer online
     */
    ObjectInputStream getInput() throws RemoteException;

    /**
     * method that set the maps to send to the players
     * @param player a player
     * @throws RemoteException if the stub is no longer online
     */
    void sendMap(Player player) throws RemoteException;

    /**
     * abstract method that send the maps set before
     * @param maps an arraylist of maps
     * @param player a player
     * @throws RemoteException if the stub is no longer online
     */
    void sendMapConn(List<Map> maps, Player player) throws RemoteException;

    /**
     * abstract method that send the private information of a player
     * @param card private objective card
     * @throws RemoteException if the stub is no longer online
     */
    void sendPrivateInformation(PrivateObjectiveCard card) throws RemoteException;

    /**
     * abstract method that send the public information of the game
     * @param cards arraylist of public objective cards
     * @param tools arraylist of tool cards
     * @throws RemoteException if the stub is no longer online
     */
    void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools) throws RemoteException;

    /**
     * abstract method that send a warning to other players that a player has left the game for disconnection
     * @param text a string
     * @param index new index of player in the array
     * @throws RemoteException if the stub is no longer online
     */
    void sendLostConnection (String text,int index) throws RemoteException;

    /**
     * abstract method that listen request of reconnection of a disconnected player
     * @throws RemoteException if the stub is no longer online
     */
    void tryReconnect() throws RemoteException;

    /**
     * abstract method that send the final message_socket to a player
     * @param players a player
     * @throws RemoteException if the stub is no longer online
     */
    void sendFinalPlayers(List<Player> players) throws RemoteException;

    /**
     * abstract methot that send the information of a turn for a player
     * @param dice boolean TRUE if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else false
     * @throws RemoteException if the stub is no longer online
     */
    void sendIsYourTurn (boolean dice, boolean tool) throws RemoteException;

    /**
     * abstract method that send a error to a player at the choose of username
     * @throws RemoteException if the stub is no longer online
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
     * @throws RemoteException if the stub is no longer online
     */
    void sendUpdate(List<Map> maps, List<String> users, String message, List<Boolean> tools,
                    RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favor) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "Copper Foil Burnisher"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageCopper(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Cork Backed Straiightedge"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageCork(String title) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "CEglomise Brush"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageEglomise(String title) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "Flux Brush"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageFluxBrush(String title) throws RemoteException;

    /**
     * abstract method that manage the first send for the tool card "Flux Remover"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageFluxRemover(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Grinding Stone"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
     void manageGrinding(String title) throws RemoteException;

    /**
     * abstract method that manage the send for the tool card "Grozing Pliers"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageGrozing(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Lathekin"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageLathekin(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Lens Cutter"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageLens(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Running Pliers"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageRunning(String title) throws RemoteException;
    /**
     * abstract method that manage the send for the tool card "Tap Wheel
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageTap(String title) throws RemoteException;

    /**
     * abstract method that send a generic error to the client
     * @param error a string
     * @throws RemoteException if the stub is no longer online
     */
    void manageError(String error) throws RemoteException;
    /**
     * abstract method that manage the second send for the tool card "Flux Remover"
     * @param title the title of this card
     * @throws RemoteException if the stub is no longer online
     */
    void manageFluxRemover2(Dice dice, String title) throws RemoteException;

    /**
     * abstract method that send a communication of victory with disconnection of others players
     * @throws RemoteException if the stub is no longer online
     */
    void sendVictoryAbbandon() throws RemoteException;

    /**
     * abstract method that close the network between server and client
     * @throws RemoteException if the stub is no longer online
     */
    void closeConnection() throws RemoteException;

    /**
     * abstract method that send a news: a player has been reconnected
     * @param text a string of text
     * @throws RemoteException if the stub is no longer online
     */
   void sendGainConnection(String text) throws RemoteException;

    /**
     * abstract method that send a confirm of reconnection
     * @param text a string
     * @param index an integer for manage the new index of this player
     * @throws RemoteException if the stub is no longer online
     */
    void sendAcceptReconnection(String text, int index) throws RemoteException;

    void setDisconnected() throws RemoteException;

    /**
     * abstract method that sets an instance of Virtual View
     * @param vView a Virtual View
     * @throws RemoteException if the stub is no longer online
     */
    void setvView(VirtualView vView) throws RemoteException;

    ConnectionServer cloneObj() throws RemoteException;

    /**
     * abstract method that sets the stub and the possessor of the connection
     * @param stub the proxy of the client on server
     * @param username the possessor of this connection
     * @throws RemoteException if the stub is no longer online
     */
    void setClientRMI(ConnectionClient stub, String username) throws RemoteException;

    /**
     * abstract method that controls if the player is connected or not
     * @return a boolean, true if the player is connected, else false
     * @throws RemoteException if the stub is no longer online
     */
    boolean receiveMessage() throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Tap Wheel"
     * @param title title of the card
     * @param roundSchemeDice a chosen dice from the round scheme
     * @param dicesToMove the set of chosen dices of the map to be moved
     * @param row1Mit the current row position of the first dice on the map
     * @param row2Mit the current row position of the second dice on the map
     * @param column1Mit the current column position of the first dice on the map
     * @param column2Mit the current column position of the second dice on the map
     * @param row1Dest the row coordinate of the first dice where to be positioned on the map
     * @param row2Dest the row coordinate of the second dice where to be positioned on the map
     * @param column1Dest the column coordinate of the first dice where to be positioned on the map
     * @param column2Dest the column coordinate of the second dice where to be positioned on the map
     * @param roundSchemeDicePos the position of the chosen dice on the round scheme
     * @throws RemoteException if the stub is no longer online
     */
    void tapSet(String title, Dice roundSchemeDice, List<Dice> dicesToMove, int row1Mit, int row2Mit,
                int column1Mit, int column2Mit, int row1Dest, int row2Dest, int column1Dest, int column2Dest,
                int roundSchemeDicePos) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Running Pliers"
     * @param title title of the card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @param rowMit the current row position of the dice on the map
     * @param columnMit the current column position of the dice on the map
     * @throws RemoteException if the stub is no longer online
     */
    void coppperSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Cork-backed Straightedge"
     * @param title the title of this card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @throws RemoteException if the stub is no longer online
     */
    void corkSet(String title, Dice dice, int rowDest, int columnDest) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Eglomise Brush"
     * @param title the title of this card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @param rowMit the current row position of the dice on the map
     * @param columnMit the current column position of the dice on the map
     * @throws RemoteException if the stub is no longer online
     */
    void eglomiseSet(String title, Dice dice, int rowDest, int columnDest, int rowMit, int columnMit) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Flux Brush"
     * @param title the title of this card
     * @param dice the chosen dice after the re-roll
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param columnDest the column coordinate of the dice where to be positioned on the map
     * @param diceBefore the dice chosen before the re-roll
     * @throws RemoteException if the stub is no longer online
     */
    void fluxBrushSet(String title, Dice dice, int rowDest, int columnDest, Dice diceBefore) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Flux Remover"
     * @param title the title of this card
     * @param dice the chosen dice after the re-roll
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     * @throws RemoteException if the stub is no longer online
     */
    void fluxRemoverSet(String title, Dice dice, int row, int column) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Grinding Stone"
     * @param title the title of this card
     * @param dice the chosen dice after the usage of this card
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     * @param diceBefore the chosen dice in its original state
     * @throws RemoteException if the stub is no longer online
     */
    void grindingSet(String title, Dice dice, int row, int column, Dice diceBefore) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Grozing Pliers"
     * @param title the title of this card
     * @param dice the chosen dice after the usage of this card
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param colDest the column coordinate of the dice where to be positioned on the map
     * @throws RemoteException if the stub is no longer online
     */
    void grozingSet(String title, Dice dice, int rowDest, int colDest) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Lathekin"
     * @param title title of the card
     * @param dicesToMove the set of chosen dices of the map to be moved
     * @param row1Mit the current row position of the first dice on the map
     * @param row2Mit the current row position of the second dice on the map
     * @param col1Mit the current column position of the first dice on the map
     * @param col2Mit the current column position of the second dice on the map
     * @param row1Dest the row coordinate of the first dice where to be positioned on the map
     * @param row2Dest the row coordinate of the second dice where to be positioned on the map
     * @param col1Dest the column coordinate of the first dice where to be positioned on the map
     * @param col2Dest the column coordinate of the second dice where to be positioned on the map
     * @throws RemoteException if the stub is no longer online
     */
    void lathekinSet(String title, int row1Mit, int row2Mit, int col1Mit, int col2Mit, int row1Dest, int col1Dest,
                     List<Dice> dicesToMove, int row2Dest, int col2Dest) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Lathekin"
     * @param title title of the card
     * @param diceStock a chosen dice from the stock
     * @param numberRound the number of this round
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     * @param diceRound a round scheme dice
     * @throws RemoteException if the stub is no longer online
     */
    void lensSet(String title, Dice diceStock, int numberRound, int row, int column, Dice diceRound) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card "Running Pliers"
     * @param title title of the card
     * @param dice a chosen dice
     * @param rowDest the row coordinate of the dice where to be positioned on the map
     * @param colDest the column coordinate of the dice where to be positioned on the map
     * @throws RemoteException if the stub is no longer online
     */
    void runningSet(String title, Dice dice, int rowDest, int colDest) throws RemoteException;

    /**
     * abstract method that manages dice position on the map
     * @param dice a chosen dice
     * @param row the row coordinate of the dice where to be positioned on the map
     * @param column the column coordinate of the dice where to be positioned on the map
     * @throws RemoteException if the stub is no longer online
     */
    void posDice(Dice dice, int row, int column) throws RemoteException;

    /**
     * abstract method that manages the usage of the tool card
     * @param title the title of the tool card
     * @throws RemoteException if the stub is no longer online
     */
    void useTool(String title) throws RemoteException;

    /**
     * abstract method that manages an empty move
     * @throws RemoteException if the stub is no longer online
     */
    void passTurn() throws RemoteException;

    /**
     * abstract method that sets if the player is connected or not
     * @param connected a boolean, true if the player is connected, else false
     * @throws RemoteException if the stub is no longer online
     */
    void setConnected(boolean connected) throws RemoteException;

    /**
     * abstract method that sets the boolean value that indicates if the player is doing his move before the timer is out
     * @throws RemoteException if the stub is no longer online
     */
    void setPlayerOnline() throws RemoteException;

    boolean isConnection() throws RemoteException;

}
