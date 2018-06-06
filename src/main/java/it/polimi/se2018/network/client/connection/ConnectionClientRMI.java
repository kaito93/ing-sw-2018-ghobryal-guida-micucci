package it.polimi.se2018.network.client.connection;


import it.polimi.se2018.model.Dice;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.server.message.*;

/**
 * Class ConnectionClientRMI
 * @author Samuele Guida, Anton
 */

public class ConnectionClientRMI extends ConnectionClient {

    @Override
    public void run() {

    }

    @Override
    public void update(MessageVC event) {

    }

    @Override
    public void visit(MessageChooseMap message) {

    }

    @Override
    public void visit(MessagePublicInformation message) {

    }

    @Override
    public void visit(MessageStart message) {

    }

    @Override
    public void visit(MessageYourTurn message) {

    }

    @Override
    public void sendPosDice(Dice dice, int column, int row) {

    }

    @Override
    public void sendUseTool(String titleCardTool) {

    }

    @Override
    public void visit(MessageError message) {

    }
}
