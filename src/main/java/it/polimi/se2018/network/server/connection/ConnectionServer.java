package it.polimi.se2018.network.server.connection;


import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public abstract class ConnectionServer implements Cloneable {

    protected VirtualView vView;
    protected String username;
    protected boolean connected=true;

    protected abstract void send (Object message);

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract ObjectInputStream getInput();

    public void sendMap(ArrayList<Map> maps, Player player){
        ArrayList<Map> mapsToPlayer= new ArrayList<>();
        for (int j=0; j<2;j++){ // sceglie 2 carte schema
            mapsToPlayer.add(vView.getController().getGame().randomMap()); // aggiunge la mappa estratta al messaggio da inviare
        }
        sendMapConn(mapsToPlayer,player);
    }

    public abstract void sendMapConn(ArrayList<Map> maps, Player player);

    public abstract void sendPrivateInformation(PrivateObjectiveCard card);

    public abstract void sendPublicInformation(ArrayList<PublicObjectiveCard> cards, ArrayList<ToolCard> tools);

    public abstract void sendLostConnection (String text,int index);

    public abstract void tryReconnect();

    public abstract void sendFinalPlayers(ArrayList<Player> players);

    public abstract void sendIsYourTurn (boolean dice, boolean tool);

    public abstract void sendErrorUser();

    public abstract void sendUpdate(ArrayList<Map> maps, ArrayList<String> users, String message, ArrayList<Boolean> tools,
                                    RoundSchemeCell roundSchemeMap[], ArrayList<Dice> stock, ArrayList<Integer> favor);

    public abstract void manageCopper(String title);
    public abstract void manageCork(String title);
    public abstract void manageEglomise(String title);
    public abstract void manageFluxBrush(String title);
    public abstract void manageFluxRemover(String title);
    public abstract void manageGrinding(String title);
    public abstract void manageGrozing(String title);
    public abstract void manageLathekin(String title);
    public abstract void manageLens(String title);
    public abstract void manageRunning(String title);
    public abstract void manageTap(String title);

    public abstract void manageError(String error);
    public abstract void manageFluxRemover2(Dice dice, String title);
    public abstract void sendVictoryAbbandon();
    public abstract void closeConnection();

    @Override
    public ConnectionServer clone() throws CloneNotSupportedException {
        return (ConnectionServer) super.clone();
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public abstract void sendGainConnection(String text);

    public abstract void sendAcceptReconnection(String text, int index);

    public void setvView(VirtualView vView) {
        this.vView = vView;
    }
}
