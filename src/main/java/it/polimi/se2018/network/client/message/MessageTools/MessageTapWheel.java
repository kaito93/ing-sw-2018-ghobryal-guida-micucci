package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;
import java.util.ArrayList;

public class MessageTapWheel implements MessageCV, MessageVC {

    private static final long serialVersionUID = 6570985689428029947L;

    private String title;
    private Dice diceRoundScheme;
    private int row1Dest;
    private int column1Dest;
    private ArrayList<Dice> diceToMove = new ArrayList<>();
    private int row2Dest;
    private int column2Dest;
    private int posDiceinSchemeRound;
    private int row1Mit;
    private int col1Mit;
    private int row2Mit;
    private int col2Mit;

    // altre info: giocatore in gioco, lo schema dei round.

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {
        controller.manageTap(title,row1Mit,row2Mit,col1Mit,col2Mit,diceRoundScheme,row1Dest,column1Dest,diceToMove,
                row2Dest,column2Dest,posDiceinSchemeRound);
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
