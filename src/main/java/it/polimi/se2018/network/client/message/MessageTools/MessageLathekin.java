package it.polimi.se2018.network.client.message.MessageTools;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.MessageCV;

import java.util.ArrayList;

public class MessageLathekin implements MessageCV, MessageVC {

    private static final long serialVersionUID = -6323405125564666679L;

    private String title;
    private int row1Dest;
    private int column1Dest;
    private ArrayList<Dice> dices = new ArrayList<>();
    private int row2Dest;
    private int column2Dest;
    private int row1Mit;
    private int col1Mit;
    private int row2Mit;
    private int col2Mit;

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    @Override
    public void accept(Controller controller) {
        controller.manageLathekin(title,row1Mit,row2Mit,col1Mit,col2Mit,row1Dest,column1Dest,dices,row2Dest,column2Dest);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setRow1Dest(int row1Dest) {
        this.row1Dest = row1Dest;
    }

    public void setCol1Mit(int col1Mit) {
        this.col1Mit = col1Mit;
    }

    public void setCol2Mit(int col2Mit) {
        this.col2Mit = col2Mit;
    }

    public void setColumn1Dest(int column1Dest) {
        this.column1Dest = column1Dest;
    }

    public void setColumn2Dest(int column2Dest) {
        this.column2Dest = column2Dest;
    }

    public void setDices(ArrayList<Dice> dices) {
        this.dices = dices;
    }

    public void setRow1Mit(int row1Mit) {
        this.row1Mit = row1Mit;
    }

    public void setRow2Dest(int row2Dest) {
        this.row2Dest = row2Dest;
    }

    public void setRow2Mit(int row2Mit) {
        this.row2Mit = row2Mit;
    }
}
