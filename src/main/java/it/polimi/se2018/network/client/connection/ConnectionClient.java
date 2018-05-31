package it.polimi.se2018.network.client.connection;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.ResponseMap;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Observable;
import it.polimi.se2018.util.Observer;
import it.polimi.se2018.view.View;

import java.util.ArrayList;


public abstract class ConnectionClient extends Observable<MessageMV> implements Observer<MessageVC> {

    String ip;
    int port;
    View view;
    String username;

    public abstract void run();

    public abstract void visit (MessageChooseMap message);

    public abstract void visit (MessagePublicInformation message);

    public abstract void visit (MessageStart message);

    public abstract void visit (MessageYourTurn message);

    public abstract void sendPosDice (Dice dice, int column, int row);

    public abstract void sendUseTool(String titleCardTool);





}
