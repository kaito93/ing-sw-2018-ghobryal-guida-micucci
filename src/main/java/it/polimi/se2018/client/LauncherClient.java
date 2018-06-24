package it.polimi.se2018.client;

import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.client.network.ConnectionClientRMI;
import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.client.client_deserializer.ClientDeserializer;
import it.polimi.se2018.client.view.LoginMain;
import it.polimi.se2018.shared.path.PathDeserializer;
import it.polimi.se2018.client.view.View;
import it.polimi.se2018.client.view.ViewCli;
import it.polimi.se2018.client.view.ViewGui;
import javafx.application.Application;

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
        //        METTI TUTTE LE INFORMAZIONI NELLE 3 VARIABILI QUI SOTTO
        //        OCCUPATI TU DI FARE I CONTROLLI SUL CORRETTO INPUT (Username non nullo).

        Application.launch(LoginMain.class);
        String username = LoginMain.getUsername();
        String choiceConnection = LoginMain.getConnections();
        String choiceView = LoginMain.getUInt();
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

        condition=true;
        while(condition){
            if ("socket".equalsIgnoreCase(choiceConnection)) {
                client = new ConnectionClientSocket(port, ip,view,username);
                view.setClient(client);
                client.run();
                condition=false;
            } else if ("rmi".equalsIgnoreCase(choiceConnection)){
                // PRENDI IL SERVER
                client = new ConnectionClientRMI(username);
                // PASSI AL SERVER IL CLIENT APPENA CREATO
                view.setClient(client);
                condition=false;
            }
        }

    }

}
