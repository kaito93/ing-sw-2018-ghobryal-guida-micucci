package it.polimi.se2018.network.server;

import it.polimi.se2018.network.connection.Connection;
import it.polimi.se2018.controller.Controller;

import java.util.ArrayList;

public class Lobby extends Thread {

    Controller controller;
    static ArrayList<Connection> PlayerConnection =new ArrayList <Connection>();

    public Lobby(){


    }

    public void start(){

    }
}
