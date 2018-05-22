package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Map;
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

    public abstract void run();

    public void chooseMap(ArrayList<Map> maps) {
        Map mapPlayer = view.chooseMap(maps);
        sendMessage(new ResponseMap(mapPlayer));
    }

    public abstract void sendMessage(Object message);

    public abstract void update(MessageVC event);


}
