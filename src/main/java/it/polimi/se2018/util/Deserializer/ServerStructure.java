package it.polimi.se2018.util.Deserializer;

public class ServerStructure {

    int time;
    int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPort() {
        return port;
    }

    public int getTime() {
        return time;
    }
}
