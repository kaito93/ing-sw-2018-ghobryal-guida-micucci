package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientRMI;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

import java.util.Scanner;

public class LauncherClient {

    public static void main(String[] args) {
        ConnectionClient client;
        int port = 9736;
        String ip = "127.0.0.1";
        String choiceConnection = "socket";
        String username;

        // To Do: Caricamento da interfaccia grafica di porta e ip
        System.out.println("Quale sarà il tuo username?");
        username = new Scanner(System.in).nextLine();
        if ("socket".equalsIgnoreCase(choiceConnection)) {
            client = new ConnectionClientSocket(port, ip,username);
        } else {
            client = new ConnectionClientRMI();
        }
        client.run();


    }

}
