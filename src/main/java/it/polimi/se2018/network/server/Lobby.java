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

    private int number;
    private ArrayList<ConnectionServer> playerConnection;



    public Lobby(ArrayList<ConnectionServer> connections, int partita){

        this.playerConnection=connections; // salvo le connessioni dei giocatori
        number=partita;

    }

    @Override
    public void run(){

        Controller controller;
        VirtualView view;
        ArrayList<Player> players;
        view= new VirtualView(); // crea la virtual vView per interfacciarsi con i giocatori
        players=view.setClients(playerConnection); // setta i giocatori
        controller = new Controller(view,players); // crea il controller
        view.setController(controller);
        view.start();
        view.startServer(); // avvia la vView
        controller.startGame(); // fa cominciare effettivamente la partita
        LOGGER.log(Level.INFO,"La partita "+ String.valueOf(number)  +"  è terminata");
        view.disconnect();
        for (ConnectionServer aPlayerConnection : playerConnection) {
            aPlayerConnection.closeConnection();
        }

    }
}
