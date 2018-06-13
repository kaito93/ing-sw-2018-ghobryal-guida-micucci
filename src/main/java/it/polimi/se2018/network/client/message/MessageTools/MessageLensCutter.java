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

public class MessageLensCutter implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());
    private static final long serialVersionUID = -467044401650037099L;

    String title;
    Dice diceStock;
    int numberRound;
    Dice diceRound;
    int row;
    int column;

    // altre informazioni: la riserva, schema dei round.

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

        controller.manageLens(title,diceStock,numberRound,row,column,diceRound);


    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDiceRound(Dice diceRound) {
        this.diceRound = diceRound;
    }

    public void setDiceStock(Dice diceStock) {
        this.diceStock = diceStock;
    }

    public void setNumberRound(int numberRound) {
        this.numberRound = numberRound;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
