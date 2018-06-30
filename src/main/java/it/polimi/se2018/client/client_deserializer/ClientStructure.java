package it.polimi.se2018.client.client_deserializer;

/**
 * class to make support data structure to deserializer the json file
 * with the info for the clientn about a client-server socket network
 */
public class ClientStructure {

    private int port;
    private String ip;
    private int timerTurn;
    private int portRMI;

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

    /**
     * getter method for the timer for turn
     * @return an integer represent the timer
     */
    public int getTimerTurn() {
        return timerTurn;
    }

    /**
     * getter method to get the port RMI number
     * @return an integer represent the port number
     */
    public int getPortRMI() {
        return portRMI;
    }
}
