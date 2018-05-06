package it.polimi.se2018.Model.Server;

import it.polimi.se2018.Model.Connection.Connection;
import it.polimi.se2018.Model.Controller.Controller;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class Lobby extends Thread {

    Controller controller;
    static ArrayList<Connection> PlayerConnection =new ArrayList <Connection>();

    public Lobby(){


    }

    public void start(){

    }
}
