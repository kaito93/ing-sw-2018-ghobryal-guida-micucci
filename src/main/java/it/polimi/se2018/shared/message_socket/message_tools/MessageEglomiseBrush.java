package it.polimi.se2018.shared.message_socket.message_tools;

import it.polimi.se2018.server.controller.Controller;
import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.shared.message_socket.client_to_server.MessageVC;
import it.polimi.se2018.shared.message_socket.server_to_client.MessageCV;

/**
 * class that manage the tool card "Eglomise Brush"
 * @author Samuele Guida
 */

public class MessageEglomiseBrush implements MessageCV, MessageVC {
    private static final long serialVersionUID = -1995035021791344031L;


    private String title;
    private Dice dice;
    private int rowDest;
    private int columnDest;
    private int rowMit;
    private int columnMit;

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

        controller.manageEglomise(title,dice,rowDest,columnDest,rowMit,columnMit);

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
