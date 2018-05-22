package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Flux Remover Tool Card
 * @author Anton Ghobryal
 */

public class FluxRemover extends ToolCard{
    /**
     * class constructor
     *
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public FluxRemover(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param stockDice a chosen dice from the stock
     * @param bag the container of all dices
     * @param value the value should be forced into the chosen dice from the bag
     * @param row row's coordinate on the map where the chosen dice to be positioned
     * @param column column's coordinate on the map where the chosen dice to be positioned
     * @return a boolean that verifies if the operation of positioning the dice was successful or not
     * @throws notValidCellException
     */
    public boolean useTool(Map map, Dice stockDice, ArrayList<Dice> bag, int value, int row, int column) throws notValidCellException {
        bag.add(stockDice);
        Collections.shuffle(bag);
        Dice temp = bag.get(1);
        bag.remove(temp);
        temp.setValue(value);
        return map.setCell(temp, row, column);
    }
}
