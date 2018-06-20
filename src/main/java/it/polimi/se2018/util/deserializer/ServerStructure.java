package it.polimi.se2018.util.deserializer;

/**
 * class to create a data support structure to deserialize the server information to make
 * possible the connection between server and client: used for socket connection
 */
public class ServerStructure {

    int time;
    int port;

    /**
     * method to set the port number
     * @param port an integer represent the port that has to be setted
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * method to set the timeout for connection of the socket connection
     * @param time an integer that represent the ms that has to wait to connect
     *             client and server
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * method to get the number of port for the connections
     * @return an integer that represent the port to make the connection for socket
     */
    public int getPort() {
        return port;
    }

    /**
     * method to get the number of ms that has to wait to connect all client to the server
     * @return an integer that represent the number of ms for the connection
     */
    public int getTime() {
        return time;
    }
}
