package it.polimi.se2018.server.model.cards;


import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.util.List;

/**
 * a generic private objective card
 * @author Anton Ghobryal
 */

public class PrivateObjectiveCard extends Card {
    private Color color;

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
                    //iterates on columns
                    if(map.getCell(i, j).getDice()!=null   //there has to be a Dice
                            && map.getCell(i, j).getDice().getColor().equals(color))   //the card's color should match the Dice's color
                        score += map.getCell(i, j).getDice().getValue();    //sums the Dice's value
            }
        }
        return score;
    }

    /**
     * Overridden in PublicObjectiveCard
     */
    @Override
    public int getScore() {
        return 0;
    }

    /**
     * Overridden in ToolCard
     */
    @Override
    public ToolCardStrategy getStrategy() {
        return null;
    }

    /**
     * Overridden in ToolCard
     */
    @Override
    public boolean isUsed() {
        return false;
    }

    /**
     * Overridden in ToolCard
     */
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap, List<Player> turns, int posDice1) {
        return false;
    }


}
