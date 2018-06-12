package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.logging.Level;

public class MessageCopperFoilBurnisher implements MessageCV, MessageVC {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());
    private static final long serialVersionUID = -2847811478201413002L;


    String title;
    Dice dice;
    int rowDest;
    int columnDest;
    int rowMit;
    int columnMit;

    // altre info: player in gioco

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {
        String error = "ciao";

        if (!controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()), dice
                , rowDest, columnDest, null, false, rowMit,
                columnMit, null, null, null, 0)) {
            controller.manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        } else
            controller.setTools();

    }

    public void setRow(int row) {
        this.rowDest = row;
    }

    public void setColumn(int column) {
        this.columnDest = column;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColumnMit(int columnMit) {
        this.columnMit = columnMit;
    }

    public void setRowMit(int rowMit) {
        this.rowMit = rowMit;
    }

    public String getTitle() {
        return title;
    }
}
