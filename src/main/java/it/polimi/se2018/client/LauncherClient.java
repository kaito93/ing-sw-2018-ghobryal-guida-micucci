package it.polimi.se2018.client;

import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.client.network.ConnectionClientRMI;
import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.client.client_deserializer.ClientDeserializer;
import it.polimi.se2018.client.view.LoginMain;
import it.polimi.se2018.server.network.ConnectionServer;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.path.PathDeserializer;
import it.polimi.se2018.client.view.View;
import it.polimi.se2018.client.view.ViewCli;
import it.polimi.se2018.client.view.ViewGui;
import javafx.application.Application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

/**
 * class that launch the client
 * @author Samuele Guida
 */
public class LauncherClient {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


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

        Application.launch(LoginMain.class);
        String username = LoginMain.getUsername();
        String choiceConnection = LoginMain.getConnections();
        String choiceView = LoginMain.getUint();
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
                client = new ConnectionClientSocket(port, ip,view, username);
                view.setClient(client);
                try {
                    client.run();
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
                }
                condition=false;
            } else if ("rmi".equalsIgnoreCase(choiceConnection)){
                ConnectionClientRMI clientRMI;
                try {
                    Registry registry = LocateRegistry.getRegistry(1100);
                    ConnectionServer connectionServer = (ConnectionServer) registry.lookup("//localhost/ServerConnectionReference");
                    clientRMI = new ConnectionClientRMI();
                    connectionServer.setClientRMI(clientRMI,username);
                    view.setClient(clientRMI);
                    condition=false;
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
                } catch (NotBoundException e) {
                    LOGGER.log(Level.SEVERE, "Oggetto Non Disponibile", e);
                }
            }
        }

    }

}
