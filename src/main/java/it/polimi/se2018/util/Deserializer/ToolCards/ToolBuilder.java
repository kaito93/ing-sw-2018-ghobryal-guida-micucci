package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.util.jsonTransiction;
import it.polimi.se2018.util.toolCardTransfer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ToolBuilder implements Observer {
    private String toBeCompared;
    private toolCardTransfer jT = null;
    private ArrayList<ToolCard> toolCardStrategic;
    private ToolCardStrategy strategy;

    public ToolBuilder(){
        jT = new toolCardTransfer();
        toolCardStrategic = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        jT = (toolCardTransfer) arg;
        System.out.println(this.toBeCompared);
        System.out.println(jT.getStrategy());
        if (toBeCompared.equalsIgnoreCase(jT.getStrategy())) {
            toolCardStrategic.add(new ToolCard(jT.getTitle(), jT.getDescription(), jT.getId(), (Color) jT.getColor(), strategy));
        }
        else
            System.out.println("sono fuori dall'IF");
    }

    public void setjT(toolCardTransfer jT) {
        this.jT = jT;
    }

    public jsonTransiction getjT() {
        return jT;
    }

    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }

    public void setStrategy(ToolCardStrategy strategy) {
        this.strategy = strategy;
    }

    public void setDeck(ArrayList deck){
        int i;
        for(i=0; i<=toolCardStrategic.size(); i++){
            if (toolCardStrategic == null)
                break;
            else
                deck.add(toolCardStrategic.get(i));
        }
    }
}
