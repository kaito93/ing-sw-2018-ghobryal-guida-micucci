package it.polimi.se2018.network.server.message;

import it.polimi.se2018.network.client.connection.ConnectionClient;

public class MessageYourTurn implements MessageCV {

    boolean posDice;
    boolean useTools;

    @Override
    public void accept(ConnectionClient client) {

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
