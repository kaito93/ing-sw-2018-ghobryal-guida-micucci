package it.polimi.se2018.network.server;

import it.polimi.se2018.network.server.connection.ConnectionServer;
import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.network.server.connection.ConnectionServer;

import java.util.ArrayList;

public class Lobby extends Thread {

    Controller controller;
    static ArrayList<ConnectionServer> PlayerConnection =new ArrayList <ConnectionServer>();

    public Lobby(){


    }

    public void start(){

    }
}
