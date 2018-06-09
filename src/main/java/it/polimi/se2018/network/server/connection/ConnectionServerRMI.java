package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
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

    @Override
    public void tryReconnect() {
        // METODO CHE CONTROLLA SE IL GIOCATORE HA INVIATO UNA RICHIESTA PER RICONNETTERSI ALLA PARTITA
    }

    @Override
    public void sendFinalPlayers(ArrayList<Player> players) {
        // METODO CHE INVIA TUTTI I GIOCATORI A TUTTI I GIOCATORE
    }

    @Override
    public void sendIsYourTurn(int fav, boolean dice, boolean tool) {
        // METODO CHE INVIA ALL'UTENTE IL NUMERO DI SEGNALINI FAVORE, ED I BOOLEANI SE PUO' FARE O MENO UNA DETERMINATA
        // MOSSA. INOLTRE IL GIOCATORE COMINCIA A GESTIRE IL PROPRIO TURNO
    }

    @Override
    public void sendErrorUser() {
        // METODO CHE INFORMA IL GIOCATORE CHE IL SUO USERNAME ERA GIA' STATO SCELTO IN PRECEDENZA... DEVE SCEGLIERNE
        // UN ALTRO
    }


    @Override
    public void sendUpdate(ArrayList<Map> maps, ArrayList<String> users, String message, ArrayList<Boolean> tools,
                           RoundSchemeCell[] roundSchemeMap, ArrayList<Dice> stock) {
        // METODO CHE INVIA LE MATRICI DEI GIOCATORI, LA LISTA DEI GIOCATORI AGGIORNATA [Vedesi disconnessioni]
        // IL MESSAGGIO DA MOSTRARE A SCHERMO DI CHI TOCCA, ARRAYLIST DI BOOLEANI CHE SI RIFERISCONO ALLE CARTE TOOL,
        // LO SCHEMA DEI ROUND E LA RISERVA
    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI

    @Override
    public void manageCopper(String title) {

    }

    @Override
    public void manageCork(String title) {

    }

    @Override
    public void manageEglomise(String title) {

    }

    @Override
    public void manageFluxBrush(String title) {

    }

    @Override
    public void manageFluxRemover(String title) {

    }

    @Override
    public void manageGrinding(String title) {

    }

    @Override
    public void manageGrozing(String title) {

    }

    @Override
    public void manageLathekin(String title) {

    }

    @Override
    public void manageLens(String title) {

    }

    @Override
    public void manageRunning(String title) {

    }

    @Override
    public void manageTap(String title) {

    }

    @Override
    public void manageError(String error) {

    }

    @Override
    public void manageFluxRemover2(Dice dice, String title) {

    }
}
