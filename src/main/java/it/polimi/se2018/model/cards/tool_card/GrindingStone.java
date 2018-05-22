package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;

/**
 * Grinding Stone Tool Card
 * @author Anton Ghobryal
 */

public class GrindingStone extends ToolCard {
    /**
     * class constructor
     *
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public GrindingStone(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param dice the chosen dice
     */
    public void useTool(Dice dice){
        switch (dice.getValue()){
            case 1:
                dice.setValue(6);
                break;
            case 2:
                dice.setValue(5);
                break;
            case 3:
                dice.setValue(4);
                break;
            case 4:
                dice.setValue(3);
                break;
            case 5:
                dice.setValue(2);
                break;
            case 6:
                dice.setValue(1);
                break;
        }
    }
}
