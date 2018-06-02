package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;

public class MessageGrozingPliers implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    String title;
    Dice dice;
    int value;

    @Override
    public void accept(ConnectionClient client) {

    }

    @Override
    public void accept(Controller controller) {
        String error="ciao";
        try {
            controller.getGame().searchToolCard(title).useTool(null,dice,value,0,null,false,0,0,
                    null,null,null,0,error);
        } catch (notValidCellException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);        }

    }
    public void setTitle(String title) {
        this.title = title;
    }
}
