package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;

/**
 * Grozing Pliers Tool Card
 * @author Anton Ghobryal
 */

public class GrozingPliers extends ToolCard{

    private int firstValue; //+1
    private int secondValue; //-1

    /**
     * class constructor
     * @param title the title of this card
     * @param description the description of the card rules
     * @param id1 card's number
     * @param color1 color associated to the card
     */

    public GrozingPliers(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param value dice's value that is needed to be increased or decreased
     */

    public void useTool(int value){
        if(value>1 && value < 6){
            firstValue = value + 1;
            secondValue = value - 1;
        } else if(value==1){
            firstValue = value + 1;
            secondValue = 0;
        } else if(value==6){
            firstValue = 0;
            secondValue = value - 1;
        }
    }

    /**
     * @return the value increased if it's possible (different from zero)
     */

    public int getFirstValue() {
        return firstValue;
    }

    /**
     * @return the value decreased if it's possible (different from zero)
     */

    public int getSecondValue() {
        return secondValue;
    }

    /**
     * forces the chosen value (the incremented one) into the dice's value
     * @param dice the chosen dice
     * @return if the operation was successful or not
     */

    public boolean setFirstValue(Dice dice){
        if(firstValue!=0)
            dice.setValue(firstValue);
        return firstValue!=0;
    }

    /**
     * forces the chosen value (the decremented one) into the dice's value
     * @param dice the chosen dice
     * @return a boolean that verifies if the operation was successful or not
     */

    public boolean setSecondValue(Dice dice){
        if(secondValue!=0)
            dice.setValue(secondValue);
        return secondValue!=0;
    }
}
