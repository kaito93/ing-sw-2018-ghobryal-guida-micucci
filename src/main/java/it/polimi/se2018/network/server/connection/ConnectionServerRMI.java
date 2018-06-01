package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ConnectionServerRMI extends ConnectionServer {

    @Override
    public void send(Object message) {
        // METODO PER INVIARE UN OGGETTO AL GIOCATORE... SE NE HAI DI BISOGNO. ALTRIMENTI LASCIALO VUOTO
    }

    @Override
    public ObjectInputStream getInput() {
        return null;
    }

    @Override
    public void sendMap(ArrayList<Map> maps, Player player) {
        // METODO PER INVIARE LA SCELTA DELLE MAPPE AI GIOCATORI
    }

    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        // METODO PER INVIARE LA CARTA OBIETTIVO PRIVATA
    }

    @Override
    public void sendPublicInformation(ArrayList<PublicObjectiveCard> cards, ArrayList<ToolCard> tools) {
        // METODO PER INVIARE I TITOLI E LE DESCRIZIONI DELLE CARTE OBIETTIVO PUBBLICHE E DEI TOOLS

    }

    @Override
    public void sendLostConnection(String text) {
        // METODO PER INVIARE UNA STRINGA DI COMUNICAZIONE AI CLIENT CHE UN GIOCATORE SI E' DISCONNESSO.
    }
}
