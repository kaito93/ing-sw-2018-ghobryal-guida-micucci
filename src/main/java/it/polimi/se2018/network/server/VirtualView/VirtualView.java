package it.polimi.se2018.network.server.VirtualView;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.network.server.message.MessageMV;
import it.polimi.se2018.network.server.message.MessageStart;
import it.polimi.se2018.util.Observable;
import it.polimi.se2018.util.Observer;
import org.omg.CORBA.Object;

import java.util.ArrayList;

public class VirtualView extends Observable<MessageVC> implements Observer<MessageMV> {

    SocketVirtualView socketView;
    RMIVirtualView RMIview;
    ArrayList<ConnectionServer> connections;
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<PlayerPlay> playersPlay = new ArrayList<PlayerPlay>();
    PlayerPlay currentPlayer;

    public static final int MVEvent=0;
    public static final int CVEvent=1;
    public static final int SystemMessage=2;


    public VirtualView (){
        this.RMIview = new RMIVirtualView(); // creo la virtual view per le connessioni socket
        this.socketView= new SocketVirtualView(); // creo la virtual view per le connessioni RMI
    }

    public void setClients(ArrayList<ConnectionServer> connect){
        for (int i=0; i<connect.size(); i++){
            PlayerPlay player = new PlayerPlay(connect.get(i));
            players.add(new Player(connect.get(i).getUsername()));

        }

    }

    public void start(){

        for (int i=0; i<this.connections.size();i++){
            MessageStart message = new MessageStart();
            // TO DO: CREARE GLI ATTRIBUTI DA SPEDIRE AL GIOCATORE
            Message mess= new Message(CVEvent,(Object) message);
            connections.get(i).send(mess);
        }



    };


    class PlayerPlay extends Thread{

        ConnectionServer client;
        boolean connected=false;

        public PlayerPlay (ConnectionServer player) {
            this.client=player;
            this.connected=true;

        }

        public boolean isConnected() {
            return connected;
        }
    }

    class SocketVirtualView extends VirtualView{}

    class RMIVirtualView extends VirtualView{}


    @Override
    public void update(MessageMV event) {

    }


}
