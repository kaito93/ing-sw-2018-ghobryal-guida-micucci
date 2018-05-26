package it.polimi.se2018.network.server.message;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.network.client.connection.ConnectionClient;

public class MessageStart implements MessageCV {

    Map map;
    PrivateObjectiveCard card;
    int favor;


    @Override
    public void accept(ConnectionClient client) {

    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setCard(PrivateObjectiveCard card) {
        this.card = card;
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public int getFavor() {
        return favor;
    }

    public Map getMap() {
        return map;
    }

    public PrivateObjectiveCard getCard() {
        return card;
    }
}
