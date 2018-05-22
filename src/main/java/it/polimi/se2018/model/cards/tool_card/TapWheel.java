package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.notValidCellException;

/**
 * Tap Wheel Tool Card
 * @author Anton Ghobryal
 */

public class TapWheel extends ToolCard {

    /**
     * class constructor
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public TapWheel(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param roundSchemeMap the Round Scheme
     * @param roundSchemeMapDice a chosen dice from the Round Scheme
     * @param dice1 first dice needed to be repositioned
     * @param dice2 second dice needed to be repositioned (off if numDicesToMove=1)
     * @param numDicesToMove the number of dices the player decides to move
     * @param map player's map
     * @param row1 row's coordinate on the map where the first chosen dice to be positioned
     * @param column1 column's coordinate on the map where the first chosen dice to be positioned
     * @param row2  row's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @param column2 column's coordinate on the map where the second chosen dice to be positioned (off if numDicesToMove=1)
     * @return a boolean that verifies if the operation of positioning the dice was successful or not
     * @throws notValidCellException
     */
    public boolean useTool(RoundSchemeCell[] roundSchemeMap, Dice roundSchemeMapDice, Dice dice1, Dice dice2, int numDicesToMove, Map map, int row1, int column1, int row2, int column2) throws notValidCellException {
        int a=0, b=0, c=0, f=0, g=0;
        boolean d, e;
        if(roundSchemeContainsDice(roundSchemeMapDice, roundSchemeMap, a)){
            if(numDicesToMove==1){
                if(roundSchemeMapDice.getColor().equalsColor(dice1.getColor()) && mapContainsDice(map, dice1, b, c)){
                    d = map.setCell(dice1, row1, column1);
                    if(d)
                        map.setCell(null, b, c);
                    return d;
                }
            }else if(numDicesToMove==2){
                if(roundSchemeMapDice.getColor().equalsColor(dice1.getColor()) && mapContainsDice(map, dice1, b, c)
                    && mapContainsDice(map, dice2, f, g) && dice2.getColor().equalsColor(dice1.getColor())){
                    d = map.setCell(dice1, row1, column1);
                    e = map.setCell(dice2, row2, column2);
                    if(d)
                        map.setCell(null, b, c);
                    if(e)
                        map.setCell(null, f, g);
                    return d && e;
                }
            }
        }
        return false;
    }
}
