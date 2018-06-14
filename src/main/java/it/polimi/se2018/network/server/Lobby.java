package it.polimi.se2018.network.server;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.controller.Controller;

import java.util.ArrayList;
import java.util.logging.Level;

public class Lobby extends Thread {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    int number;
    Controller controller;
    ArrayList<ConnectionServer> playerConnection;
    VirtualView view;
    ArrayList<Player> players;

    public Lobby(ArrayList<ConnectionServer> connections, int partita){

        this.playerConnection=connections; // salvo le connessioni dei giocatori
        number=partita;

    }

    @Override
    public void run(){

        this.view= new VirtualView(); // crea la virtual view per interfacciarsi con i giocatori
        players=this.view.setClients(playerConnection); // setta i giocatori
        this.controller = new Controller(view,players); // crea il controller
        this.view.setController(this.controller);
        view.start();
        this.view.startServer(); // avvia la view
        this.controller.startGame(); // fa cominciare effettivamente la partita
        LOGGER.log(Level.INFO,"La partita "+ String.valueOf(number)  +"  è terminata");

    }
}
