package it.polimi.se2018.server.network;


import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract class that manage the connections between Server and Client server side
 * @author Samuele Guida
 */
public abstract class ConnectionServer implements Cloneable {

    protected VirtualView vView;
    protected String username;
    private boolean connected=true;

    /**
     * abstract method used for send an object from Server to Client
     * @param message an object
     */
    protected abstract void send (Object message);

    /**
     * method that set the username of a player
     * @param username a string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * method that returns the username of this connections
     * @return a string
     */
    public String getUsername() {
        return username;
    }

    /**
     * abstract method that returns a specific inputstream
     * @return an object
     */
    public abstract ObjectInputStream getInput();

    /**
     * method that set the maps to send to the players
     * @param player a player
     */
    public void sendMap(Player player){
        ArrayList<Map> mapsToPlayer= new ArrayList<>();
        for (int j=0; j<2;j++){ // sceglie 2 carte schema
            mapsToPlayer.add(vView.getController().getGame().randomMap()); // aggiunge la mappa estratta al messaggio da inviare
        }
        sendMapConn(mapsToPlayer,player);
    }

    /**
     * abstract method that send the maps set before
     * @param maps an arraylist of maps
     * @param player a player
     */
    abstract void sendMapConn(List<Map> maps, Player player);

    /**
     * abstract method that send the private information of a player
     * @param card private objective card
     */
    public abstract void sendPrivateInformation(PrivateObjectiveCard card);

    /**
     * abstract method that send the public information of the game
     * @param cards arraylist of public objective cards
     * @param tools arraylist of tool cards
     */
    public abstract void sendPublicInformation(List<PublicObjectiveCard> cards, List<ToolCard> tools);

    /**
     * abstract method that send a warning to other players that a player has left the game for disconnection
     * @param text a string
     * @param index new index of player in the array
     */
    public abstract void sendLostConnection (String text,int index);

    /**
     * abstract method that listen request of reconnection of a disconnected player
     */
    public abstract void tryReconnect();

    /**
     * abstract method that send the final message_socket to a player
     * @param players a player
     */
    public abstract void sendFinalPlayers(List<Player> players);

    /**
     * abstract methot that send the information of a turn for a player
     * @param dice boolean TRUE if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else false
     */
    public abstract void sendIsYourTurn (boolean dice, boolean tool);

    /**
     * abstract method that send a error to a player at the choose of username
     */
    public abstract void sendErrorUser();

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
    public abstract void sendUpdate(List<Map> maps, List<String> users, String message, List<Boolean> tools,
                                    RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favor);

    /**
     * abstract method that manage the send for the tool card "Copper Foil Burnisher"
     * @param title the title of this card
     */
    public abstract void manageCopper(String title);
    /**
     * abstract method that manage the send for the tool card "Cork Backed Straiightedge"
     * @param title the title of this card
     */
    public abstract void manageCork(String title);

    /**
     * abstract method that manage the send for the tool card "CEglomise Brush"
     * @param title the title of this card
     */
    public abstract void manageEglomise(String title);

    /**
     * abstract method that manage the send for the tool card "Flux Brush"
     * @param title the title of this card
     */
    public abstract void manageFluxBrush(String title);

    /**
     * abstract method that manage the first send for the tool card "Flux Remover"
     * @param title the title of this card
     */
    public abstract void manageFluxRemover(String title);
    /**
     * abstract method that manage the send for the tool card "Grinding Stone"
     * @param title the title of this card
     */
     public abstract void manageGrinding(String title);

    /**
     * abstract method that manage the send for the tool card "Grozing Pliers"
     * @param title the title of this card
     */
    public abstract void manageGrozing(String title);
    /**
     * abstract method that manage the send for the tool card "Lathekin"
     * @param title the title of this card
     */
    public abstract void manageLathekin(String title);
    /**
     * abstract method that manage the send for the tool card "Lens Cutter"
     * @param title the title of this card
     */
    public abstract void manageLens(String title);
    /**
     * abstract method that manage the send for the tool card "Running Pliers"
     * @param title the title of this card
     */
    public abstract void manageRunning(String title);
    /**
     * abstract method that manage the send for the tool card "Tap Wheel
     * @param title the title of this card
     */
    public abstract void manageTap(String title);

    /**
     * abstract method that send a generic error to the client
     * @param error a string
     */
    public abstract void manageError(String error);
    /**
     * abstract method that manage the second send for the tool card "Flux Remover"
     * @param title the title of this card
     */
    public abstract void manageFluxRemover2(Dice dice, String title);

    /**
     * abstract method that send a communication of victory with disconnection of others players
     */
    public abstract void sendVictoryAbbandon();

    /**
     * abstract method that close the network between server and client
     */
    public abstract void closeConnection();

    /**
     * method to clone a network
     * @return the cloned network
     * @throws CloneNotSupportedException if clone is not supported
     */
    @Override
    public ConnectionServer clone() throws CloneNotSupportedException {
        return (ConnectionServer) super.clone();
    }

    /**
     * method that returns if the network is active
     * @return a boolean
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * method that set the status of network
     * @param connected a boolean
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    /**
     * abstract method that send a news: a player has been reconnected
     * @param text a string of text
     */
    public abstract void sendGainConnection(String text);

    /**
     * abstract method that send a confirm of reconnection
     * @param text a string
     * @param index an integer for manage the new index of this player
     */
    public abstract void sendAcceptReconnection(String text, int index);

    /**
     * method that set an instance of Virtual View
     * @param vView a Virtual View
     */
    public void setvView(VirtualView vView) {
        this.vView = vView;
    }
}
