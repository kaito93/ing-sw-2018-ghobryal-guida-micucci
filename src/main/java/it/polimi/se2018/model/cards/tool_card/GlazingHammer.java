package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;

import java.util.ArrayList;

/**
 * Glazing Hammer Tool Card
 * @author Anton Ghobryal
 */

public class GlazingHammer extends ToolCard {
    /**
     * class constructor
     *
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public GlazingHammer(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param stock Round's Stock
     * @param turn player's turn number
     * @param posDice a boolean verifies if the player has chosen a dice from the stock or not
     * @return a boolean that verifies if the player can use the card or not
     */

    //posDice ok!
    public boolean useTool(ArrayList<Dice> stock, int turn, boolean posDice){
        if(turn==2 && !posDice)
            for(Dice dice : stock)
                dice.throwDice();
        return turn==2 && !posDice;
    }
}
