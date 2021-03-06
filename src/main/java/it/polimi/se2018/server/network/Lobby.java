package it.polimi.se2018.server.network;

import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.server.controller.Controller;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;

/**
 * Class that manage a single game
 * @author Samuele Guida
 */
public class Lobby extends Thread {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private int number;
    private List<ConnectionServer> playerConnection;


    /**
     * class constructor
     * @param connections an arraylist of connections of all clients
     * @param partita an Id for this game
     */
    public Lobby(List<ConnectionServer> connections, int partita){

        this.playerConnection=connections; // salvo le connessioni dei giocatori
        number=partita;

    }

    /**
     * method that let's start the game in a new thread
     */
    @Override
    public void run(){

        Controller controller;
        VirtualView view;
        List<Player> players;
        view= new VirtualView(); // crea la virtual vView per interfacciarsi con i giocatori
        controller = new Controller(view); // crea il controller
        view.setController(controller);
        players=view.setClients(playerConnection); // setta i giocatori
        controller.setPlayers(players);
        view.start();
        view.startServer(); // avvia la vView
        controller.startGame(); // fa cominciare effettivamente la partita
        LOGGER.log(Level.INFO,"La partita {0} ", String.valueOf(number)  +"  è terminata");
        view.disconnect();
        try {
            for (ConnectionServer aPlayerConnection : playerConnection) {
                aPlayerConnection.closeConnection();
            }
        }catch (RemoteException e){
            LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
        }
    }
}
