package it.polimi.se2018.network.server.connection;


import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

public abstract class ConnectionServer {

    String username;

    public abstract void send (Object message);

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

}
