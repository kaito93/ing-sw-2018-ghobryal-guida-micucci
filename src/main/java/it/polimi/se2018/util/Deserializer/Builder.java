package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.public_objective_card_strategy.ObjectiveCardStrategy;
import it.polimi.se2018.util.PublicCardsTransfer;
import it.polimi.se2018.util.jsonTransiction;

import java.util.Observable;
import java.util.Observer;

public class Builder implements Observer{
    private PublicObjectiveCard poc;
    private ObjectiveCardStrategy strategy;
    private String toBeCompared;
    private PublicCardsTransfer jT = null;

    public Builder(){
        jT = new PublicCardsTransfer();
    }

    @Override
    public void update(Observable o, Object arg) {
        jT = (PublicCardsTransfer) arg;
        System.out.println(this.toBeCompared);
        System.out.println(jT.getStrategy());
       if (toBeCompared.equalsIgnoreCase(jT.getStrategy())) {
           poc = new PublicObjectiveCard(jT.getTitle(), jT.getDescription(), jT.getScores(), strategy);
           System.out.println(poc.getTitle());
       }
       else
           System.out.println("sono fuori dall'IF");
    }

    public void setjT(PublicCardsTransfer jT) {
        this.jT = jT;
    }

    public ObjectiveCardStrategy getStrategy() {
        return strategy;
    }

    public PublicObjectiveCard getPoc() {
        return poc;
    }

    public void setPoc(PublicObjectiveCard poc) {
        this.poc = poc;
    }

    public void setStrategy(ObjectiveCardStrategy strategy) {
        this.strategy = strategy;
    }

    public void setToBeCompared(String toBeCompared) {
        this.toBeCompared = toBeCompared;
    }
}
