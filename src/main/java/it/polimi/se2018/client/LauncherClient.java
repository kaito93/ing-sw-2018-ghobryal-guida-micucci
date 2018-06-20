package it.polimi.se2018.client;

import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.client.network.ConnectionClientRMI;
import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.client.client_deserializer.ClientDeserializer;
import it.polimi.se2018.shared.path.PathDeserializer;
import it.polimi.se2018.client.view.View;
import it.polimi.se2018.client.view.ViewCli;
import it.polimi.se2018.client.view.ViewGui;

/**
 * class that launch the client
 * @author Samuele Guida
 */
public class LauncherClient {

    public static void main(String[] args) {
        ConnectionClient client;
        View view;

        PathDeserializer path = new PathDeserializer("src/main/java/it/polimi/se2018/client/json_client/Pathname.json");
        path.Deserializing();
        ClientDeserializer clien = new ClientDeserializer(path.getPathFromType("client"));
        clien.deserializing();

        int port = clien.getCs().getPort();
        String ip = clien.getCs().getIp();
        int timer = clien.getCs().getTimerTurn();

        // QUI PUOI CHIUDERE IL BUFFER READER

        String choiceConnection = "socket";
        String choiceView = "cli";
        if ("cli".equalsIgnoreCase(choiceView))
             view = new ViewCli(timer);
        else
             view = new ViewGui(timer);



        // To Do: Caricamento da interfaccia grafica di porta e ip

        if ("socket".equalsIgnoreCase(choiceConnection)) {
            client = new ConnectionClientSocket(port, ip,view);
        } else {
            client = new ConnectionClientRMI();
        }
        view.setClient(client);
        view.startView(); // visualizza la vView
        client.run();


    }

}
