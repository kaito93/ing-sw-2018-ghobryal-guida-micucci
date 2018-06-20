package it.polimi.se2018.client.network;


import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.message_socket.client_to_server.MessageVC;

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
    public void sendPosDice(Dice dice, int column, int row) {

    }

    @Override
    public void sendUseTool(String titleCardTool) {

    }

    @Override
    public void sendPassMove() {

    }

    @Override
    public void sendReconnect() {

    }

    @Override
    public void sendDisconnect() {

    }
}
