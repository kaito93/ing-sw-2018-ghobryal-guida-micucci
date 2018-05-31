package it.polimi.se2018.util.Deserializer.ToolCards;

import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.util.jsonTransiction;
import it.polimi.se2018.util.toolCardTransfer;

import java.util.Observable;
import java.util.Observer;

public class ToolBuilder implements Observer {
    private String toBeCompared;
    private toolCardTransfer jT = null;
    private ToolCard toolCardStrategic;
    private ToolCardStrategy strategy;

    public ToolBuilder(){
        jT = new toolCardTransfer();
    }

    @Override
    public void update(Observable o, Object arg) {
        jT = (toolCardTransfer) arg;
        System.out.println(this.toBeCompared);
        System.out.println(jT.getStrategy());
        if (toBeCompared.equalsIgnoreCase(jT.getStrategy())) {
            //toolCardStrategic = new ToolCard(jT.getTitle(), jT.getDescription(), jT.getId(), strategy);  da modificare quando anton mi da' l'ok
            System.out.println(toolCardStrategic.getTitle());
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

}
