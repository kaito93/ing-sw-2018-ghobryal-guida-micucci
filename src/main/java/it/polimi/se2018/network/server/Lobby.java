package it.polimi.se2018.network.server;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.controller.Controller;

import java.util.ArrayList;

public class Lobby extends Thread {

    Controller controller;
    static ArrayList<ConnectionServer> playerConnection =new ArrayList <>();
    VirtualView view;
    ArrayList<Player> players;

    public Lobby(ArrayList<ConnectionServer> connections){

        this.playerConnection=connections; // salvo le connessioni dei giocatori

    }

    @Override
    public void run(){

        this.view= new VirtualView(); // crea la virtual view per interfacciarsi con i giocatori
        players=this.view.setClients(playerConnection); // setta i giocatori
        this.view.startServer(); // avvia la view
        this.controller = new Controller(view,players); // crea il controller
        this.view.setController(this.controller);
        this.controller.startGame(); // fa cominciare effettivamente la partita

    }
}
