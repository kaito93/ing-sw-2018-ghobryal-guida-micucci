package it.polimi.se2018.util.Deserializer;

/**
 * class to make support data structure to deserialize the json file
 * with the info for the clientn about a client-server socket connection
 */
public class ClientStructure {

    int port;
    String ip;

    /**
     * setter class for the ip number
     * @param ip integer with the number of the client
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * setter method for the port to make the communication between client
     * and server
     * @param port an integer represent the socket port between client and server
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * getter method to get the port number
     * @return an integer represent the port number
     */
    public int getPort() {
        return port;
    }

    /**
     * getter method for the client ip
     * @return an integer represent the client ip andress
     */
    public String getIp() {
        return ip;
    }
}
