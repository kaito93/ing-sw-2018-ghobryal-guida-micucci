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
        View view=null;

        PathDeserializer path = new PathDeserializer("src/main/java/it/polimi/se2018/client/json_client/Pathname.json");
        path.deserializing();
        ClientDeserializer clien = new ClientDeserializer(path.getPathFromType("client"));
        clien.deserializing();

        int port = clien.getCs().getPort();
        String ip = clien.getCs().getIp();
        int timer = clien.getCs().getTimerTurn();

        // QUI PUOI CHIUDERE IL BUFFER READER

        // X MIK: CHIEDERE LE INFORMAZIONI TRAMITE GUI INERENTI A CONNESSIONE, USERNAME E UI

        //String username="ciao";

        String choiceConnection = "socket";
        String choiceView = "cli";
        boolean condition=true;
        while(condition){
            if ("cli".equalsIgnoreCase(choiceView)){
                view = new ViewCli(timer);
                condition = false;
            }
            if ("gui".equalsIgnoreCase(choiceView)){
                view = new ViewGui(timer);
                condition = false;
            }
        }

        // To Do: Caricamento da interfaccia grafica di porta e ip
        condition=true;
        while(condition){
            if ("socket".equalsIgnoreCase(choiceConnection)) {
                client = new ConnectionClientSocket(port, ip,view);
                view.setClient(client);
                view.startView(); // poi dovrà essere tolto appena verrà preso l'username dalla view
                client.run();
                condition=false;
            } else if ("rmi".equalsIgnoreCase(choiceConnection)){
                // PRENDI IL SERVER
                client = new ConnectionClientRMI();
                // PASSI AL SERVER IL CLIENT APPENA CREATO
                view.setClient(client);
                view.startView(); // poi dovrà essere tolto appena verrà preso l'username dalla view
                condition=false;
            }
        }

    }

}
