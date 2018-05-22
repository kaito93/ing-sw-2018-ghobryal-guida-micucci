package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Flux Brush Tool Card
 * @author Anton Ghobryal
 */

public class FluxBrush extends ToolCard{

    /**
     * class constructor
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public FluxBrush(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param stock round's stock
     * @param dice a stock dice
     * @param map player's map
     * @param row row's coordinate where to position the dice
     * @param column column's coordinate where to position the dice
     * @return a boolean that is "true" if it is possible to put the dice in the player's map
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(ArrayList<Dice> stock, Dice dice, Map map, int row, int column) throws notValidCellException {
        dice.throwDice();
        if(map.setCell(dice, row, column)) {
            stock.remove(dice);
            return true;
        }
        return false;
    }
}
