package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.MessageTools.*;
import it.polimi.se2018.network.client.message.MessageVC;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.client.message.RequestReconnect;
import it.polimi.se2018.network.server.message.*;
import it.polimi.se2018.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;


public class ConnectionServerSocket extends ConnectionServer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    Message mex;
    Socket client;
    ObjectOutputStream output;
    ObjectInputStream input;


    public ConnectionServerSocket(Socket client, RequestConnection obj,ObjectOutputStream out, ObjectInputStream inp){
        this.client=client;
        this.setUsername(obj.getUser());
        input= inp;
        output = out;
    }

    public Socket getSocket(){
        return this.client;

    }

    public ObjectInputStream getInput(){
        return this.input;
    }

    public void send(Object message) {
        try {
            this.output.writeObject(message);
            this.output.flush();
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void sendMap(ArrayList<Map> maps, Player player) {
        MessageChooseMap message = new MessageChooseMap(); // prepara un messaggio da inviare per scegliere la carta schema
        for (int j=0; j<2;j++){ // sceglie 2 carte schema
            Map m = randomMap(maps);
            message.addMap(m); // aggiunge la mappa estratta al messaggio da inviare
            message.setPlayer(player); // invio alla view il giocatore proprietario
        }

        mex= new Message(Message.CVEVENT, message);
        send(mex); // viene inviato il messaggio al giocatore per scegliere la carta

    }

    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        MessageStart message = new MessageStart(); //crea un nuovo messaggio
        mex = new Message(Message.CVEVENT,message);
        message.setCard(card);
        send(mex);
    }

    @Override
    public void sendPublicInformation(ArrayList<PublicObjectiveCard> cards, ArrayList<ToolCard> tools) {
        MessagePublicInformation messag = new MessagePublicInformation();
        mex= new Message(Message.CVEVENT,messag);
        messag.setPublicObjective(cards);
        messag.setToolCards(tools);
        send(mex);
    }

    @Override
    public void sendLostConnection(String text) {
        mex = new Message(Message.SYSTEMEVENT, text); //crea un messaggio per avvisare tutti i giocatori ancora in gioco
        send(mex);
    }

    @Override
    public void tryReconnect() {
        boolean reconnect=false;
        while (!reconnect){ // fin quando il giocatore non si è riconnesso
            try{
                MessageVC reconn = (MessageVC) getInput().readObject();
                if (reconn instanceof RequestReconnect)
                    reconnect=false;
                else
                    reconnect=true;
            } catch (IOException e) {
                LOGGER.log(Level.OFF, "Il player " + getUsername()+" è ancora disconnesso. Non ho ricevuto nulla", e);

            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.OFF, "Il player " + getUsername()+" è disconnesso. Non manda dati corretti", e);

            }

        }
    }

    @Override
    public void sendFinalPlayers(ArrayList<Player> players) {
        MessageFinalScore messag = new MessageFinalScore();
        messag.setPlayersFinal(players);
        Message mex = new Message(Message.CVEVENT,messag);
        send(mex);
    }

    @Override
    public void sendIsYourTurn(int fav, boolean dice, boolean tool) {
        MessageYourTurn mes = new MessageYourTurn();
        mes.setFavor(fav);
        mes.setPosDice(dice);
        mes.setUseTools(tool);
        mex = new Message(Message.CVEVENT,mes);
        send(mex);
    }

    @Override
    public void sendErrorUser() {
        MessageNewUsername message = new MessageNewUsername(); // crea un nuovo messaggio
        mex= new Message(SYSTEMMESSAGE,message); // creo un messaggio di sistema
        send(mex); //invia il messaggio. [nota bene: non si salva conness nell'array]
    }

    @Override
    public void sendUpdate(ArrayList<Map> maps, ArrayList<String> users, String messa, ArrayList<Boolean> tools, RoundSchemeCell[] roundSchemeMap, ArrayList<Dice> stock) {
        MessageUpdate message= new MessageUpdate();
        message.setMessage(messa);
        message.setCells(maps);
        message.setStock(stock);
        message.setUseTools(tools);
        message.setUsers(users);
        message.setRoundSchemeMap(roundSchemeMap);
        mex = new Message(Message.MVEVENT,message);
        send(mex);
    }

    // METODI PER LA GESTIONE DELLE CARTE UTENSILI

    @Override
    public void manageTap(String title) {
        MessageTapWheel message = new MessageTapWheel();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }

    @Override
    public void manageRunning(String title) {
        MessageRunningPliers message = new MessageRunningPliers();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }

    @Override
    public void manageLens(String title) {
        MessageLensCutter message= new MessageLensCutter();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageLathekin(String title) {
        MessageLathekin message= new MessageLathekin();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageGrozing(String title) {
        MessageGrozingPliers message = new MessageGrozingPliers();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageGrinding(String title) {
        MessageGrindingStone message = new MessageGrindingStone();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageFluxRemover(String title) {
        MessageFluxRemover message = new MessageFluxRemover();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageFluxBrush(String title) {
        MessageFluxBrush message= new MessageFluxBrush();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageEglomise(String title) {
        MessageEglomiseBrush message = new MessageEglomiseBrush();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageCork(String title) {
        MessageCorkBackedStraightedge message = new MessageCorkBackedStraightedge();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);

    }

    @Override
    public void manageCopper(String title) {
        MessageCopperFoilBurnisher message = new MessageCopperFoilBurnisher();
        message.setTitle(title);
        mex = new Message(Message.CVEVENT,message);
        send(mex);
    }
}
