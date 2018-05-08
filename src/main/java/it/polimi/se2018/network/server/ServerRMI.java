package it.polimi.se2018.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRMI extends Remote {

    void Connetti( Remote client ) throws RemoteException;

}
