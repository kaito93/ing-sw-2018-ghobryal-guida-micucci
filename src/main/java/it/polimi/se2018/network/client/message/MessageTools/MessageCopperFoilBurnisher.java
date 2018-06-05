package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;

public class MessageCopperFoilBurnisher implements MessageCV, MessageVC {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    String title;
    Dice dice;
    int row;
    int column;

    // altre info: player in gioco

    @Override
    public void accept(ConnectionClient client) {

    }

    @Override
    public void accept(Controller controller) {
        String error = "ciao";

        controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()), dice
                , row, column, null, false, 0, 0, null, null, null, 0);

    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
