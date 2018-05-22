package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;

/**
 * Glazing Hammer Tool Card
 * @author Anton Ghobryal
 */

public class RunningPliers extends ToolCard {
    /**
     * class constructor
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public RunningPliers(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param playerCurr current player (this)
     * @param turns players in order
     * @param dice chose dice
     * @param map player's map
     * @param stock Round's Stock
     * @param row row's coordinate on the map where the dice should be placed
     * @param column column's coordinate on the map where the dice should be placed
     * @return a boolean that is "true" if it is possible to put a dice in it and if this card can be used
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player playerCurr, ArrayList<Player> turns, Dice dice, Map map, ArrayList<Dice> stock, int row, int column) throws notValidCellException {
        boolean a = false;
        if(turns.indexOf(playerCurr)==0 && stock.contains(dice)){
            a = map.setCell(dice, row, column);
            turns.remove(0);
            if(a)
                stock.remove(dice);
        }
        return  a;
    }
}