package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;

public class MessageLensCutter implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    String title;
    Dice diceStock;
    int numberRound;
    Dice diceRound;

    // altre informazioni: la riserva, schema dei round.

    @Override
    public void accept(ConnectionClient client) {

    }

    @Override
    public void accept(Controller controller) {
        String error="ciao";

            controller.getGame().searchToolCard(title).useTool(null,diceStock,numberRound,0,controller.getGame().getStock(),
                    false,0,0,diceRound,controller.getGame().getRoundSchemeMap(),null,0);


    }
    public void setTitle(String title) {
        this.title = title;
    }
}
