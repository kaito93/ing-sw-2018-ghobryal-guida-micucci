package it.polimi.se2018.util.Deserializer;

public class ClientStructure {

    int port;
    String ip;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
