package it.polimi.se2018.client.client_deserializer;

/**
 * class to make support data structure to deserializer the json file
 * with the info for the clientn about a client-server socket network
 * @author Andrea Micucci
 */
public class ClientStructure {

    private int port;
    private String ip;
    private int timerTurn;
    private int portRMI;

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
