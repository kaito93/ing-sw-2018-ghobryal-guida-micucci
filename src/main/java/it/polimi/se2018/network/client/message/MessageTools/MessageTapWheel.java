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

public class MessageTapWheel implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    String title;
    Dice diceRoundScheme;
    int row1;
    int column1;
    ArrayList<Dice> diceToMove= new ArrayList<>();
    int row2;
    int column2;
    int posDiceinSchemeRound;

    // altre info: giocatore in gioco, lo schema dei round.

    @Override
    public void accept(ConnectionClient client) {

    }

    @Override
    public void accept(Controller controller) {
        String error="ciao";

            controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()),
                    diceRoundScheme,row1,column1,diceToMove,false,row2,column2,null,
                    controller.getGame().getRoundSchemeMap(),null,posDiceinSchemeRound);


    }
    public void setTitle(String title) {
        this.title = title;
    }
}
