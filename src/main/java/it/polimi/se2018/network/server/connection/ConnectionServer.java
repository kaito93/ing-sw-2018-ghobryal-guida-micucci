package it.polimi.se2018.network.server.connection;


import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

public abstract class ConnectionServer {

    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMMESSAGE=2;

    String username;

    protected abstract void send (Object message);

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract ObjectInputStream getInput();

    public abstract void sendMap(ArrayList<Map> maps, Player player);

    public Map randomMap(ArrayList<Map> ma){
        Random random = new Random();
        int j = random.nextInt(ma.size()); // estrai un numero casuale tra tutte le mappe disponibili
        Map val= ma.get(j); // salvati la mappa
        ma.remove(j); // rimuovi la mappa dall array di mappe
        return val; // ritorna la mappa estratta
    }

    public abstract void sendPrivateInformation(PrivateObjectiveCard card);

    public abstract void sendPublicInformation(ArrayList<PublicObjectiveCard> cards, ArrayList<ToolCard> tools);

    public abstract void sendLostConnection (String text);

    public abstract void tryReconnect();

    public abstract void sendFinalPlayers(ArrayList<Player> players);

    public abstract void sendIsYourTurn (int fav, boolean dice, boolean tool);

    public abstract void sendErrorUser();

    public abstract void sendUpdate(ArrayList<Map> maps, ArrayList<String> users, String message, ArrayList<Boolean> tools,
                                    RoundSchemeCell roundSchemeMap[], ArrayList<Dice> stock);

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

}
