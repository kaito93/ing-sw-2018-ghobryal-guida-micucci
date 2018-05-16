package it.polimi.se2018.network.server;

import it.polimi.se2018.model.exception.notValidMatrixException;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.controller.Controller;

import java.util.ArrayList;

public class Lobby extends Thread {

    Controller controller;
    static ArrayList<ConnectionServer> playerConnection =new ArrayList <ConnectionServer>();
    VirtualView view;

    public Lobby(ArrayList<ConnectionServer> connections){

        this.playerConnection=connections; // salvo le connessioni dei giocatori
        this.view= new VirtualView(); // crea la virtual view per interfacciarsi con i giocatori
        this.view.setClients(connections); // setta i giocatori
        this.view.startServer(); // avvia la view
        this.controller = new Controller(view);
    }

    public void start(){



    }
}
