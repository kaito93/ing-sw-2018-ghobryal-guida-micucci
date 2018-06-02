package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;

public class MessageRunningPliers implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    String title;
    Dice dice;
    int row;
    int column;

    // altre informazioni: giocatore in gioco, il numero del round del giocatore (1 o 2), la riserva, playersInRound

    @Override
    public void accept(ConnectionClient client) {

    }

    @Override
    public void accept(Controller controller) {
        String error="ciao";
        try {
            controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()),dice,
                    controller.firstOrSecond(),0,controller.getGame().getStock(),false,row,column,null,
                    null,controller.getPlayersInRound(),0,error);
        } catch (notValidCellException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }
    public void setTitle(String title) {
        this.title = title;
    }
}
