package it.polimi.se2018.client.network;

import com.sun.org.apache.regexp.internal.RE;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.network.ConnectionServer;
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
     * @throws RemoteException if the skeleton is no longer online
     */
    void run() throws RemoteException;

    /**
     * abstract method that send the choice of set a dice
     * @param dice the dice chosen
     * @param column the column where set the dice
     * @param row the row where set the dice
     * @throws RemoteException if the skeleton is no longer online
     */
    void sendPosDice (Dice dice, int column, int row) throws RemoteException;

    /**
     * abstract method that send the choice of use a tool card
     * @param titleCardTool the title of the tool card
     * @throws RemoteException if the skeleton is no longer online
     */
    void sendUseTool(String titleCardTool) throws RemoteException;

    /**
     * method that set the username of this player
     * @param username username's player
     * @throws RemoteException if the skeleton is no longer online
     */
    void setUsername(String username) throws RemoteException;

    /**
     * abstact method that send a empty move
     * @throws RemoteException if the skeleton is no longer online
     */
    void sendPassMove () throws RemoteException;

    /**
     * abstract method that send a request to reconnect to the game
     * @throws RemoteException if the skeleton is no longer online
     */
    void sendReconnect() throws RemoteException;

    /**
     * abstact method that send a message_socket of inactivity
     * @throws RemoteException if the skeleton is no longer online
     */
    void sendDisconnect() throws RemoteException;

    /**
     * abstract method that gets the username of this player
     * @return username's player
     * @throws RemoteException if the skeleton is no longer online
     */
    String getUsername() throws RemoteException;

    /**
     * abstract method that gets the view
     * @return the view
     * @throws RemoteException if the skeleton is no longer online
     */
    View getView() throws RemoteException;

    /**
     * abstract method that receives and visualizes the maps to be chosen from the player
     * @param cells list of all maps in Cell[][] format
     * @param names list of names of all maps
     * @param fav list of favor points/difficulty level of the map
     * @return the index of the chosen map
     * @throws RemoteException if the skeleton is no longer online
     */
    int receiveMapConn(List<Cell[][]> cells, List<String> names, List<Integer> fav) throws RemoteException;

    /**
     * abstract method that visualizes the private card information of a player
     * @param privateObjectiveCard private objective card
     * @throws RemoteException if the skeleton is no longer online
     */
    void viewPrivateCard(PrivateObjectiveCard privateObjectiveCard) throws RemoteException;

    /**
     * abstract method that visualizes the public information of the game
     * @param titlePublic a list of public objective cards titles
     * @param descriptionPublic a list of public objective cards descriptions
     * @param titleTool a list of tool cards titles
     * @param descriptionTool a list of tool cards descriptions
     * @param publicScore a list of public objective cards scorers
     * @throws RemoteException if the skeleton is no longer online
     */
    void viewPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTool, List<String> descriptionTool, List<Integer> publicScore) throws RemoteException;

    /**
     * abstract method that visualizes the final scores of all players
     * @param finalScore a list of final scores
     * @throws RemoteException if the skeleton is no longer online
     */
    void  viewScore(List<Integer> finalScore) throws RemoteException;

    /**
     * abstract method that visualizes the information of a turn for a player
     *
     * @param dice boolean TRUE if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else false
     * @throws RemoteException if the skeleton is no longer online
     */
    void isTurn(boolean dice, boolean tool) throws RemoteException;

    /**
     * abstract method that manage a request of a new username
     * @throws RemoteException if the skeleton is no longer online
     */
    void requestNewUsername() throws RemoteException;

    /**
     * abstract method that visualizes update of the client with a new news
     *
     * @param cells an arraylist of maps of all players in game
     * @param users an arraylist of string of username of all players in game
     * @param useTools an arraylist of boolean that manage the use of tool cards
     * @param roundSchemeMap a matrix of the round scheme map
     * @param stock a matrix of dices
     * @param favors an arraylist of integer
     * @throws RemoteException if the skeleton is no longer online
     */
    void receiveUpdate(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                       RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors, String message) throws RemoteException;

    /**
     * abstract method that manages the tool card Tap Wheel
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void tapWheel(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Copper Foil Burnisher
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void copperFoil(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Cork-backed Straightedge
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void corkbacked(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Eglomise Brush
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void eglomise(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Flux Brush
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void fluxBrush(String title) throws RemoteException;

    /**
     * abstract method that manages the first choice of the dice to be exchanged with a dice from the bag
     * (read the description of the card Flux Remover for further information)
     * @return a random dice from the bag
     * @throws RemoteException if the skeleton is no longer online
     */
    Dice fluxRemover() throws RemoteException;

    /**
     * abstract method that manages the dice positioning of the chosen dice after using the card Flux Remover
     * @param title a string of the title of the card
     * @param dice a random dice from the bag
     * @throws RemoteException if the skeleton is no longer online
     */
    void fluxRemover2(String title, Dice dice) throws RemoteException;

    /**
     * abstract method that manages the tool card Grinding Stone
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void grinding(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Grozing Pliers
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void grozing(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Lathekin
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void lathekin(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Lens Cutter
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void lens(String title) throws RemoteException;

    /**
     * abstract method that manages the tool card Running Pliers
     * @param title a string of the title of the card
     * @throws RemoteException if the skeleton is no longer online
     */
    void running(String title) throws RemoteException;

    /**
     * abstract method that manages a generic error to the client
     *
     * @param error a string of the error
     * @throws RemoteException if the skeleton is no longer online
     */
    void handleError(String error) throws RemoteException;

    /**
     * abstract method that manages a warning to other players that a player has left the game for disconnection
     *
     * @param text  a string of the error
     * @param index new index of player in the array
     * @throws RemoteException if the skeleton is no longer online
     */
    void receiveLostConnection(String text, int index) throws RemoteException;

    void setSkeleton(ConnectionServer skeleton) throws RemoteException;
}
