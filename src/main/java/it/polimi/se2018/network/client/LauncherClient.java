package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientRMI;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class LauncherClient {

    public static void main(String[] args) {
        ConnectionClient client;
        int port = 9736;
        String ip = "127.0.0.1";
        String choiceConnection = "socket";

        // To Do: Caricamento da interfaccia grafica di porta e ip

        if ("socket".equalsIgnoreCase(choiceConnection)) {
            client = new ConnectionClientSocket(port, ip);
        } else {
            client = new ConnectionClientRMI();
        }
        client.run();


    }

}
