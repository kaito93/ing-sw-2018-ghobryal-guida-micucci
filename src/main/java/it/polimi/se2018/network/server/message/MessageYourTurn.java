package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClient;
import it.polimi.se2018.network.client.connection.ConnectionClientSocket;

public class MessageYourTurn implements MessageCV {

    private static final long serialVersionUID = -2685027267850020534L;
    boolean posDice;
    boolean useTools;
    int favor;

    @Override
    public void accept(ConnectionClientSocket client) {
        client.visit(this);
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public int getFavor() {
        return favor;
    }

    public void setUseTools(boolean useTools) {
        this.useTools = useTools;
    }

    public void setPosDice(boolean posDice) {
        this.posDice = posDice;
    }

    public boolean isPosDice() {
        return posDice;
    }

    public boolean isUseTools() {
        return useTools;
    }
}
