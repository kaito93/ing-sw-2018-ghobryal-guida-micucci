package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.client.network.ConnectionClientSocket;

import java.util.ArrayList;
import java.util.List;

/**
 * class that manage the tool card "Tap Wheel"
 * @author Samuele Guida
 */

public class MessageTapWheel extends MessageTool {

    private static final long serialVersionUID = 6570985689428029947L;

    private Dice diceRoundScheme;
    private int row1Dest;
    private int column1Dest;
    private List<Dice> diceToMove = new ArrayList<>();
    private int row2Dest;
    private int column2Dest;
    private int posDiceinSchemeRound;
    private int row1Mit;
    private int col1Mit;
    private int row2Mit;
    private int col2Mit;

    /**
     * method that accept this message_socket client side
     * @param client network socket client side
     */
    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }
    /**
     * method that accept this message_socket server side
     * @param controller controller server side
     */
    @Override
    public void accept(Controller controller) {
        controller.manageTap(title,row1Mit,row2Mit,col1Mit,col2Mit,diceRoundScheme,row1Dest,column1Dest,diceToMove,
                row2Dest,column2Dest,posDiceinSchemeRound);
    }
    /**
     * method that set the initial row of the second dice
     * @param row2Mit an integer
     */
    public void setRow2Mit(int row2Mit) {
        this.row2Mit = row2Mit;
    }
    /**
     * method that set the initial row of the first dice
     * @param row1Mit an integer
     */
    public void setRow1Mit(int row1Mit) {
        this.row1Mit = row1Mit;
    }
    /**
     * method that set the initial column of the second dice
     * @param col2Mit an integer
     */
    public void setCol2Mit(int col2Mit) {
        this.col2Mit = col2Mit;
    }
    /**
     * method that set the initial column of the first dice
     * @param col1Mit an integer
     */
    public void setCol1Mit(int col1Mit) {
        this.col1Mit = col1Mit;
    }

    /**
     * method that set the dice from the Round Scheme
     * @param diceRoundScheme a dice
     */
    public void setDiceRoundScheme(Dice diceRoundScheme) {
        this.diceRoundScheme = diceRoundScheme;
    }

    /**
     * method that set the arrayList of the dice to moved
     * @param diceToMove arrayList of dices
     */
    public void setDiceToMove(List<Dice> diceToMove) {
        this.diceToMove = diceToMove;
    }

    /**
     * method that set the round of the dice choosed from Round Scheme
     * @param posDiceinSchemeRound an integer
     */
    public void setPosDiceinSchemeRound(int posDiceinSchemeRound) {
        this.posDiceinSchemeRound = posDiceinSchemeRound;
    }

    /**
     * method that set the final column of the first dice
     * @param column1Dest an integer
     */
    public void setColumn1Dest(int column1Dest) {
        this.column1Dest = column1Dest;
    }

    /**
     * method that set the final column of the second dice
     * @param column2Dest an integer
     */
    public void setColumn2Dest(int column2Dest) {
        this.column2Dest = column2Dest;
    }

    /**
     * method that set the final row of the first dice
     * @param row1Dest an integer
     */
    public void setRow1Dest(int row1Dest) {
        this.row1Dest = row1Dest;
    }
    /**
     * method that set the final row of the second dice
     * @param row2Dest an integer
     */
    public void setRow2Dest(int row2Dest) {
        this.row2Dest = row2Dest;
    }
}
