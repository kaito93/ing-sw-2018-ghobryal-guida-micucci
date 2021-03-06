package it.polimi.se2018.client.network;


import it.polimi.se2018.client.view.View;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.network.ConnectionServer;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;

/**
 * Class ConnectionClientRMI
 *
 * @author Anton Ghobryal
 */

public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient, Serializable {

    private static final String NAMEONREGISTER = "//localhost/ServerConnectionReference";
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final String SERVERDISCONNECTION = "Il server si è disconnesso";

    private ConnectionServer skeleton;

    private View view;
    private String username;
    private Registry registry;
    private final Object lock;

    private boolean playerOn = true;


    /**
     * class constructor
     *
     * @param view     the vView for the interaction with the player
     * @param username the possessor of this connection
     * @param registry remote object registry that provides methods for storing and retrieving remote object references
     *                 bound with arbitrary string names
     */
    public ConnectionClientRMI(View view, String username, Registry registry) throws RemoteException {
        super();
        this.view = view;
        this.username = username;
        this.registry = registry;
        lock = new Object();
    }


    /**
     * this method is not used for rmi connection
     * it's only used for socket connection
     */
    @Override
    public void run() {
        //non lo uso per RMI
    }

    /**
     * sets the server's proxy on client territory
     *
     * @param skeleton remote object that represents the server proxy on client territory
     */
    public void setSkeleton(ConnectionServer skeleton) {
        try {
            this.skeleton = skeleton;
        } catch (NullPointerException e) {
            this.skeleton = null;
        }
    }

    /**
     * method that receives and visualizes the maps to be chosen from the player
     *
     * @param cells list of all maps in Cell[][] format
     * @param names list of names of all maps
     * @param fav   list of favor points/difficulty level of the map
     * @return the index of the chosen map
     */
    public int receiveMapConn(List<Cell[][]> cells, List<String> names, List<Integer> fav) {
        Cell[][] mapPlayer = view.chooseMap(cells, username, names, fav);
        return cells.indexOf(mapPlayer);
    }

    /**
     * method that visualizes the private card information of a player
     *
     * @param privateObjectiveCard private objective card
     */
    public void viewPrivateCard(PrivateObjectiveCard privateObjectiveCard) {
        view.setPrivateInformation(privateObjectiveCard.getTitle(), privateObjectiveCard.getDescription());
    }

    /**
     * method that visualizes the public information of the game
     *
     * @param titlePublic       a list of public objective cards titles
     * @param descriptionPublic a list of public objective cards descriptions
     * @param titleTool         a list of tool cards titles
     * @param descriptionTool   a list of tool cards descriptions
     * @param publicScore       a list of public objective cards scorers
     */
    public void viewPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTool, List<String> descriptionTool, List<Integer> publicScore) {
        view.setPublicInformation(titlePublic, descriptionPublic, titleTool, descriptionTool, publicScore);
    }

    /**
     * method that visualizes the final scores of all players
     *
     * @param finalScore a list of final scores
     */
    public void viewScore(List<Integer> finalScore) {
        view.seeScore(finalScore);
    }

    /**
     * method that visualizes the information of a turn for a player
     *
     * @param dice boolean TRUE if the player have already positioned a dice, else False
     * @param tool boolean True if the player gave already used a tool card, else false
     */
    public void isTurn(boolean dice, boolean tool) {
        view.updateFavor(dice, tool);
        view.turn();
    }

    /**
     * method that manage a request of a new username
     */
    public void requestNewUsername() {
        try {
            String newUsername = view.askNewUsername(); //chiedo un nuovo username
            ConnectionServer connectionServer = (ConnectionServer) registry.lookup(NAMEONREGISTER); //cerca e ritorna l'oggetto reso disponibile su rete
            ConnectionClientRMI clientRMI = new ConnectionClientRMI(view, newUsername, registry); //creo una connessione di tipo rmi
            connectionServer.setClientRMI(clientRMI, newUsername); //rendo lo stub disponibile dalla parte del server
            connectionServer.setUsername(newUsername);
            clientRMI.setSkeleton(connectionServer); //rendo lo skeleton disponibile dalla parte del cliente
            view.setClient(clientRMI);
        } catch (RemoteException | NotBoundException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that visualizes update of the client with a new news
     *
     * @param cells          an arraylist of maps of all players in game
     * @param users          an arraylist of string of username of all players in game
     * @param useTools       an arraylist of boolean that manage the use of tool cards
     * @param roundSchemeMap a matrix of the round scheme map
     * @param stock          a matrix of dices
     * @param favors         an arraylist of integer
     */
    public void receiveUpdate(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                              RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors, String message, String username) {
        view.updateUsers(users, cells, useTools, roundSchemeMap, stock, favors, username);
        view.addLog(message);
    }

    /**
     * manages the tool card Tap Wheel
     *
     * @param title a string of the title of the card
     */
    public void tapWheel(String title) {
        List<Object> obj = view.manageTap();
        try {
            skeleton.tapSet(title, (Dice) obj.get(0), (List<Dice>) obj.get(9), (int) obj.get(1), (int) obj.get(5),
                    (int) obj.get(2), (int) obj.get(6), (int) obj.get(3), (int) obj.get(7), (int) obj.get(4),
                    (int) obj.get(8), (int) obj.get(10));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);

        }
    }

    /**
     * manages the tool card Copper Foil Burnisher
     *
     * @param title a string of the title of the card
     */
    public void copperFoil(String title) {
        List<Object> obj = view.manageCE();
        try {
            skeleton.coppperSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2), (int) obj.get(3), (int) obj.get(4));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Cork-backed Straightedge
     *
     * @param title a string of the title of the card
     */
    public void corkbacked(String title) {
        List<Object> obj = view.manageCork();
        try {
            skeleton.corkSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Eglomise Brush
     *
     * @param title a string of the title of the card
     */
    public void eglomise(String title) {
        List<Object> obj = view.manageCE();
        try {
            skeleton.eglomiseSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2), (int) obj.get(3), (int) obj.get(4));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Flux Brush
     *
     * @param title a string of the title of the card
     */
    public void fluxBrush(String title) {
        List<Object> obj = view.managefluxBrush();
        try {
            skeleton.fluxBrushSet(title, (Dice) obj.get(1), (int) obj.get(2), (int) obj.get(3), (Dice) obj.get(0));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Grinding Stone
     *
     * @param title a string of the title of the card
     */
    public void grinding(String title) {
        List<Object> obj = view.manageGrinding();
        try {
            skeleton.grindingSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2), (Dice) obj.get(3));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Grozing Pliers
     *
     * @param title a string of the title of the card
     */
    public void grozing(String title) {
        List<Object> obj = view.manageGrozing();
        try {
            skeleton.grozingSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Lathekin
     *
     * @param title a string of the title of the card
     */
    public void lathekin(String title) {
        List<Object> obj = view.manageLathekin();
        try {
            skeleton.lathekinSet(title, (int) obj.get(0), (int) obj.get(4), (int) obj.get(1), (int) obj.get(5),
                    (int) obj.get(2), (int) obj.get(3), (List<Dice>) obj.get(8), (int) obj.get(6), (int) obj.get(7));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Lens Cutter
     *
     * @param title a string of the title of the card
     */
    public void lens(String title) {
        List<Object> obj = view.manageLens();
        try {
            skeleton.lensSet(title, (Dice) obj.get(0), (int) obj.get(2), (int) obj.get(3), (int) obj.get(4), (Dice) obj.get(1));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the tool card Running Pliers
     *
     * @param title a string of the title of the card
     */
    public void running(String title) {
        List<Object> obj = view.manageCork();
        try {
            skeleton.runningSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * manages the first choice of the dice to be exchanged with a dice from the bag
     * (read the description of the card Flux Remover for further information)
     *
     * @return a random dice from the bag
     */
    public Dice fluxRemover() {
        return view.managefluxRemove();
    }

    /**
     * manages the dice positioning of the chosen dice after using the card Flux Remover
     *
     * @param title a string of the title of the card
     * @param dice  a random dice from the bag
     */
    public void fluxRemover2(String title, Dice dice) {
        List<Object> obj = view.manageFluxRemove2(dice);
        try {
            skeleton.fluxRemoverSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that manages a generic error to the client
     *
     * @param error a string of the error
     */
    public void handleError(String error) {

        view.manageError(error);
    }

    /**
     * method that sends the choice of set a dice
     *
     * @param dice   the chosen dice
     * @param column the column where to set the dice
     * @param row    the row where to set the dice
     */
    @Override
    public void sendPosDice(Dice dice, int column, int row) {
        try {
            skeleton.posDice(dice, column, row);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that sends the choice of use a tool card
     *
     * @param titleCardTool the title of the tool card
     */
    @Override
    public void sendUseTool(String titleCardTool) {
        try {
            skeleton.useTool(titleCardTool);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that sends an empty move
     */
    @Override
    public void sendPassMove() {
        try {
            skeleton.passTurn();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that manages a warning to other players that a player has left the game for disconnection
     *
     * @param text  a string of the error
     * @param index new index of player in the array
     */
    public void receiveLostConnection(String text, int index) {
        view.updateIndex(index);
        view.addError(text);
    }

    /**
     * exits the winner
     *
     * @param text Message of victory
     */
    public void receiveVictoryAbbandon(String text) {
        view.addError(text);
        System.exit(0);
    }

    /**
     * method that sends a request to reconnect to the game
     */
    @Override
    public void sendReconnect() {
        try {
            playerOn = true;
            skeleton.setConnected(true);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that disconnects the player for inactivity
     */
    @Override
    public void sendDisconnect() {
        try {
            playerOn = false;
            skeleton.setPlayerOnline(false);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, SERVERDISCONNECTION);
            System.exit(0);
        }
    }

    /**
     * method that sets the username of this player
     *
     * @param username username's player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * method that gets the username of this player
     *
     * @return username's player
     */
    public String getUsername() {
        return username;
    }

    /**
     * method that gets the view
     *
     * @return the view
     */
    public View getView() {
        return view;
    }

    /**
     * exits the system
     */
    public void exitSystem() {
        view.addError("La partita è terminata. Il giocatore non può riconettersi");
        System.exit(0);
    }

    public void waitReconnect() {
        boolean a = false;
        try {
            while (!a) {
                synchronized (lock) {
                    lock.wait(100);
                }
                a = playerOn;
                if (a)
                    return;
            }
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }



    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
