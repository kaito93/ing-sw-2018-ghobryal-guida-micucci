package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.util.Logger;

import java.util.List;
import java.util.logging.Level;

/**
 * a generic private objective card
 * @author Anton Ghobryal
 */

public class PrivateObjectiveCard extends Card {
    private Color color;

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    /**
     * Class Constructor
     * @param title the title of this card
     * @param description the description of the card rules
     * @param color1 the color of this card
     */
    public PrivateObjectiveCard(String title, String description, Color color1){
        super(title, description);
        color = color1;
    }

    /**
     * searches the values of all positioned dices of a specified color
     * @param map player's map
     * @return the sum of these values
     */

    @Override
    public int search(Map map){
        int score=0;
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for (int j=0; j<map.numColumn(); j++){
                try {
                    //iterates on columns
                    if(map.getCell(i, j).getDice()!=null   //there has to be a Dice
                            && map.getCell(i, j).getDice().getColor().equals(color))   //the card's color should match the Dice's color
                        score += map.getCell(i, j).getDice().getValue();    //sums the Dice's value
                }catch (notValidCellException e) {
                    LOGGER.log(Level.SEVERE, e.toString()+"PrivateObjectiveCards", e);
                }
            }
        }
        return score;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public ToolCardStrategy getStrategy() {
        return null;
    }

    @Override
    public boolean isUsed() {
        return false;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void setUsed(boolean used1) {}

    @Override
    public boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock, boolean posDice, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap, List<Player> turns, int posDice1) {
        return false;
    }


}
