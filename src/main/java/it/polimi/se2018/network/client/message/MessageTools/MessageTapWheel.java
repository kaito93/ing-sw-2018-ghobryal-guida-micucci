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

import java.util.ArrayList;
import java.util.logging.Level;

public class MessageTapWheel implements MessageCV, MessageVC {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());
    private static final long serialVersionUID = 6570985689428029947L;

    String title;
    Dice diceRoundScheme;
    int row1Dest;
    int column1Dest;
    ArrayList<Dice> diceToMove = new ArrayList<>();
    int row2Dest;
    int column2Dest;
    int posDiceinSchemeRound;
    int row1Mit;
    int col1Mit;
    int row2Mit;
    int col2Mit;

    // altre info: giocatore in gioco, lo schema dei round.

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {
        String error = "ciao";
        controller.getGame().searchToolCard(title).getStrategy().setRow3(row1Mit);
        controller.getGame().searchToolCard(title).getStrategy().setRow4(row2Mit);
        controller.getGame().searchToolCard(title).getStrategy().setColumn3(col1Mit);
        controller.getGame().searchToolCard(title).getStrategy().setRow3(col2Mit);
        if (!controller.getGame().searchToolCard(title).useTool(controller.getPlayersInRound().get(controller.getTurno()),
                diceRoundScheme, row1Dest, column1Dest, diceToMove, false, row2Dest, column2Dest, null,
                controller.getGame().getRoundSchemeMap(), null, posDiceinSchemeRound))
            controller.manageError(ToolCardStrategy.getErrorBool().getErrorMessage());
        else
            controller.setTools();


    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRow2Dest(int row2Dest) {
        this.row2Dest = row2Dest;
    }

    public void setRow2Mit(int row2Mit) {
        this.row2Mit = row2Mit;
    }

    public void setRow1Mit(int row1Mit) {
        this.row1Mit = row1Mit;
    }

    public void setColumn2Dest(int column2Dest) {
        this.column2Dest = column2Dest;
    }

    public void setColumn1Dest(int column1Dest) {
        this.column1Dest = column1Dest;
    }

    public void setCol2Mit(int col2Mit) {
        this.col2Mit = col2Mit;
    }

    public void setCol1Mit(int col1Mit) {
        this.col1Mit = col1Mit;
    }

    public void setRow1Dest(int row1Dest) {
        this.row1Dest = row1Dest;
    }

    public void setDiceRoundScheme(Dice diceRoundScheme) {
        this.diceRoundScheme = diceRoundScheme;
    }

    public void setDiceToMove(ArrayList<Dice> diceToMove) {
        this.diceToMove = diceToMove;
    }

    public void setPosDiceinSchemeRound(int posDiceinSchemeRound) {
        this.posDiceinSchemeRound = posDiceinSchemeRound;
    }
}
