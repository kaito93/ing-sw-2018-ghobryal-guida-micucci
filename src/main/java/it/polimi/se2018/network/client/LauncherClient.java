package it.polimi.se2018.network.client;

import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientRMI;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;
import it.polimi.se2018.util.Deserializer.ClientDeserializer;
import it.polimi.se2018.util.Deserializer.PathDeserializer;
import it.polimi.se2018.view.View;
import it.polimi.se2018.view.ViewCli;
import it.polimi.se2018.view.ViewGui;


public class LauncherClient {

    public static void main(String[] args) {
        ConnectionClient client;
        View view;

        PathDeserializer path = new PathDeserializer();
        path.Deserializing();
        ClientDeserializer clien = new ClientDeserializer(path.getPathFromType("client"));
        clien.Deserializing();

        int port = clien.getCs().getPort();
        String ip = clien.getCs().getIp();

        // QUI PUOI CHIUDERE IL BUFFER READER

        String choiceConnection = "socket";
        String choiceView = "cli";
        if ("cli".equalsIgnoreCase(choiceView))
             view = new ViewCli();
        else
             view = new ViewGui();



        // To Do: Caricamento da interfaccia grafica di porta e ip

        if ("socket".equalsIgnoreCase(choiceConnection)) {
            client = new ConnectionClientSocket(port, ip,view);
        } else {
            client = new ConnectionClientRMI();
        }
        view.setClient(client);
        view.startView(); // visualizza la view
        client.run();


    }

}
