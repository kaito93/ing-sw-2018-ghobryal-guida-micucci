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

import java.util.Collections;
import java.util.logging.Level;

public class MessageFluxRemover implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());
    private static final long serialVersionUID = -7097281702696167500L;

    String title;
    Dice dice;
    int row;
    int column;
    boolean a = false;

    // altre info: contenitore dei 90 dadi.

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {
        String error = "ciao";

        if (a) {
            if (!controller.getGame().searchToolCard(title).useTool(null, dice, 0, 0, controller.getGame().getDiceBag().getBox(),
                    false, row, column, null, null, null, 0)) {
                controller.getGame().getStock().add(dice);
                controller.manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
            } else{
                controller.getPlayersInRound().get(controller.getTurno()).incrementPosDice();
                controller.setTools();
            }

        } else {
            controller.getGame().getDiceBag().getBox().add(dice);
            controller.getGame().removeDiceStock(dice);
            Collections.shuffle(controller.getGame().getDiceBag().getBox());
            dice = controller.getGame().getDiceBag().getBox().remove(0);
            controller.getView().manageFluxRemover2(dice, title, controller.getPlayersInRound().get(controller.getTurno()));
        }

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isA() {
        return a;
    }

    public Dice getDice() {
        return dice;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
