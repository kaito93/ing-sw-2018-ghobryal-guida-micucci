package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.shared.message_socket.MessageVC;
import it.polimi.se2018.shared.message_socket.MessageCV;
import it.polimi.se2018.shared.model_shared.Dice;

/**
 * abstract class for the messages
 * @author Samuele Guida
 */
public abstract class MessageTool implements MessageVC, MessageCV {

    protected String title;
    protected Dice dice;
    protected int rowDest;
    protected int columnDest;
    protected int rowMit;
    protected int columnMit;

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
     * method that set the column
     * @param columnDest an integer
     */
    public void setColumnDest(int columnDest) {
        this.columnDest = columnDest;
    }
    /**
     * method that set the row
     * @param rowDest an integer
     */
    public void setRowDest(int rowDest) {
        this.rowDest = rowDest;
    }
    /**
     * method that set the Dice
     * @param dice a dice
     */
    public void setDice(Dice dice) {
        this.dice = dice;
    }
    /**
     * method that set the initial column
     * @param columnMit an integer
     */
    public void setColumnMit(int columnMit) {
        this.columnMit = columnMit;
    }
    /**
     * method that set the initial row
     * @param rowMit an integer
     */
    public void setRowMit(int rowMit) {
        this.rowMit = rowMit;
    }
}
