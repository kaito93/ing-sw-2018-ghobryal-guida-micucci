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
     * method to set the port number socket
     * @param port an integer represent the port that has to be setted
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * method to set the timeout for network of the socket network
     * @param time an integer that represent the ms that has to wait to connect
     *             client and server
     */
    public void setTime(int time) {
        this.time = time;
    }

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
     * method to set the port number for RMI
     * @param portRMI an integer represent the port that has to be setted
     */
    public void setPortRMI(int portRMI) {
        this.portRMI = portRMI;
    }
    /**
     * method to get the number of port for the connection RMI
     * @return an integer that represent the port to make the network for socket
     */
    public int getPortRMI() {
        return portRMI;
    }
}
