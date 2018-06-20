package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.shared.message_socket.client_to_server.MessageVC;
import it.polimi.se2018.shared.message_socket.server_to_client.MessageCV;
import java.util.ArrayList;
import java.util.List;

/**
 * class that manage the tool card "Tap Wheel"
 * @author Samuele Guida
 */

public class MessageTapWheel implements MessageCV, MessageVC {

    private static final long serialVersionUID = 6570985689428029947L;

    private String title;
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
     * method that set the title of a card
     * @param title a string
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * method that return the title of the card
     * @return a string
     */
    public String getTitle() {
        return title;
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
}
