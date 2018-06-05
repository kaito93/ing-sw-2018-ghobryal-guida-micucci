package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.ArrayList;
import java.util.logging.Level;

public class MessageLathekin implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    String title;
    int row1;
    int column1;
    ArrayList<Dice> dices = new ArrayList<>();
    int row2;
    int column2;

    // Altre info: il player in gioco

    @Override
    public void accept(ConnectionClient client) {

    }

    @Override
    public void accept(Controller controller) {
        String error="ciao";
            controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()),
                    null,row1,column1,dices,false,row2,column2,null,null,null,
                    0);


    }
    public void setTitle(String title) {
        this.title = title;
    }
}
