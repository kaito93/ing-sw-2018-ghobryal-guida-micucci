package it.polimi.se2018.client.network;


import it.polimi.se2018.client.view.View;
import it.polimi.se2018.server.network.ConnectionServer;
import it.polimi.se2018.shared.model_shared.Dice;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class ConnectionClientRMI
 * @author Anton
 */

public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient,Serializable {

    private transient ConnectionServer skeleton;

    private transient ConnectionClient connectionClient;

    private transient View view;
    private transient String username;

    public ConnectionClientRMI() throws RemoteException {
        super();
    }


    @Override
    public void run() {

    }

    @Override
    public void sendPosDice(Dice dice, int column, int row) {

    }

    @Override
    public void sendUseTool(String titleCardTool) {

    }

    @Override
    public void sendPassMove() {

    }

    @Override
    public void sendReconnect() {

    }

    @Override
    public void sendDisconnect() {

    }

    /**
     * method that set the username of this player
     * @param username username's player
     */
    public void setUsername(String username){
        this.username= username;
    }

    public String getUsername() {
        return username;
    }

    public View getView() {
        return view;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
