package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.message.MessageMV;
import it.polimi.se2018.view.View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class ConnectionClient extends it.polimi.se2018.util.Observable<MessageMV> implements it.polimi.se2018.util.Observer<MessageVC> {

    String ip;
    int port;
    View view;
    Player thisPlayer;

    public abstract void run();

    public void chooseMap(ArrayList<Map> maps, Player player) {
        this.thisPlayer=player; // setto il giocatore proprietario di questa connessione
        Map mapPlayer = view.chooseMap(maps); // invoco la view per scegliere la mappa
        sendMessage(new ResponseMap(mapPlayer,thisPlayer.getName())); // invio la risposta al server
    }

    public abstract void sendMessage(Object message);

    public abstract void update(MessageVC event);


}
