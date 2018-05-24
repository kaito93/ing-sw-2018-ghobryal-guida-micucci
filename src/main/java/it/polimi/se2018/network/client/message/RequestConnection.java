package it.polimi.se2018.network.client.message;

import it.polimi.se2018.controller.Controller;

public class RequestConnection implements MessageVC{

    String msg;
    String username;

    public RequestConnection(String user){
        msg="connesso";
        username=user;
    }

    public String getUser(){
        return username;

    }

    @Override
    public void accept(Controller controller) {

    }
}
