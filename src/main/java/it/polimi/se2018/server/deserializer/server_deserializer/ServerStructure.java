package it.polimi.se2018.server.deserializer.server_deserializer;

/**
 * class to create a data support structure to deserializer the server information to make
 * possible the network between server and client: used for socket network
 */
public class ServerStructure {

    private int time;
    private int port;
    private int portRMI;

    /**
     * method to get the number of port for the connection socket
     * @return an integer that represent the port to make the network for socket
     */
    public int getPort() {
        return port;
    }

    /**
     * method to get the number of ms that has to wait to connect all client to the server
     * @return an integer that represent the number of ms for the network
     */
    public int getTime() {
        return time;
    }

    /**
     * method to get the number of port for the connection RMI
     * @return an integer that represent the port to make the network for socket
     */
    public int getPortRMI() {
        return portRMI;
    }
}
