package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

public class MessageRunningPliers implements MessageCV, MessageVC {

    private static final long serialVersionUID = 7135700756609444452L;

    private String title;
    private Dice dice;
    private int rowDest;
    private int columnDest;

    // altre informazioni: giocatore in gioco, il numero del round del giocatore (1 o 2), la riserva, playersInRound

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {

        controller.manageRunning(title,dice,rowDest,columnDest);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRowDest(int rowDest) {
        this.rowDest = rowDest;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public void setColumnDest(int columnDest) {
        this.columnDest = columnDest;
    }

}
