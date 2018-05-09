package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageMV;

import java.util.Observable;
import java.util.Observer;

public abstract class ConnectionClient extends it.polimi.se2018.util.Observable<MessageMV> implements it.polimi.se2018.util.Observer<MessageVC> {

    String ip;
    int port;

    public abstract void run();

}
