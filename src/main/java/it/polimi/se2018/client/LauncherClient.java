package it.polimi.se2018.client;

import it.polimi.se2018.client.network.ConnectionClient;
import it.polimi.se2018.client.network.ConnectionClientRMI;
import it.polimi.se2018.client.network.ConnectionClientSocket;
import it.polimi.se2018.client.client_deserializer.ClientDeserializer;
import it.polimi.se2018.server.network.ConnectionServer;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.path.PathDeserializer;
import it.polimi.se2018.client.view.View;
import it.polimi.se2018.client.view.ViewCli;
import it.polimi.se2018.client.view.ViewGui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * class that launch the client
 * @author Samuele Guida
 */
public class LauncherClient {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static int port;
    private static String ip;
    private static int portRMI;


    public static void main(String[] args) {

        PathDeserializer path = new PathDeserializer("src/main/java/it/polimi/se2018/client/json_client/Pathname.json");
        path.deserializing();
        ClientDeserializer clien = new ClientDeserializer(path.getPathFromType("client"));
        clien.deserializing();

        port = clien.getCs().getPort();
        ip = clien.getCs().getIp();
        int timer = clien.getCs().getTimerTurn();
        int portRMI = clien.getCs().getPortRMI();
        LauncherClient launcher = new LauncherClient(port,ip,timer,portRMI);
    }



    public LauncherClient(int port1, String ip1, int timer1, int portRMI1){
        View view;
        port=port1;
        ip=ip1;
        portRMI=portRMI1;
        int timer = timer1;



        boolean condition=true;
        while(condition){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Scegli con quale UI giocare [Cli o GUI]");
            String text=scanner.nextLine();
            if ("cli".equalsIgnoreCase(text)){
                view = new ViewCli(timer);
                condition = false;
            }
            if ("gui".equalsIgnoreCase(text)){
                view = new ViewGui(timer,this);
                view.startView();
                condition = false;
            }
        }
    }

    public void setConnection(int port, String ip, String username, View view, String choiceConnection){

        boolean condition=true;
        while(condition){
            if ("socket".equalsIgnoreCase(choiceConnection)) {
                ConnectionClient client = new ConnectionClientSocket(port, ip,view, username);
                view.setClient(client);
                try {
                    client.run();
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
                }
                condition=false;
            } else if ("rmi".equalsIgnoreCase(choiceConnection)){
                ConnectionClientRMI clientRMI;
                while (condition) {
                    try {
                        Registry registry = LocateRegistry.getRegistry(portRMI); //mi ritorna il registro sulla porta già attivata su server
                        ConnectionServer connectionServer = (ConnectionServer) registry.lookup("//localhost/ServerConnectionReference"); //cerca e ritorna l'oggetto reso disponibile su rete
                        clientRMI = new ConnectionClientRMI(view, username, registry); //creo una connessione di tipo rmi
                        connectionServer.setClientRMI(clientRMI, username); //rendo lo stub disponibile dalla parte del server
                        connectionServer.setUsername(username);
                        clientRMI.setSkeleton(connectionServer); //rendo lo skeleton disponibile dalla parte del cliente
                        view.setClient(clientRMI);
                        condition = false;
                    }catch (RemoteException | NotBoundException e){
                        condition=true;
                    }
                }
            }
            view.addLog("Connessione stabilita col server");
        }

    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

}