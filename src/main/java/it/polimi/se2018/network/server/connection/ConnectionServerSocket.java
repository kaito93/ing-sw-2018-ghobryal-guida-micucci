package it.polimi.se2018.network.server.connection;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.client.message.RequestConnection;
import it.polimi.se2018.network.server.message.MessageChooseMap;
import it.polimi.se2018.network.server.message.MessagePublicInformation;
import it.polimi.se2018.network.server.message.MessageStart;
import it.polimi.se2018.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;


public class ConnectionServerSocket extends ConnectionServer {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


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

        Message mess= new Message(Message.CVEVENT, message);
        send(mess); // viene inviato il messaggio al giocatore per scegliere la carta

    }

    @Override
    public void sendPrivateInformation(PrivateObjectiveCard card) {
        MessageStart message = new MessageStart(); //crea un nuovo messaggio
        Message mex = new Message(Message.CVEVENT,message);
        message.setCard(card);
        send(mex);
    }

    @Override
    public void sendPublicInformation(ArrayList<PublicObjectiveCard> cards, ArrayList<ToolCard> tools) {
        MessagePublicInformation messag = new MessagePublicInformation();
        Message mex= new Message(Message.CVEVENT,messag);
        messag.setPublicObjective(cards);
        messag.setToolCards(tools);
        send(mex);
    }

    @Override
    public void sendLostConnection(String text) {
        Message message = new Message(Message.SYSTEMEVENT, text); //crea un messaggio per avvisare tutti i giocatori ancora in gioco
        send(message);
    }
}
