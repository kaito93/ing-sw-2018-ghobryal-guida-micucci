package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cards.Card;
import it.polimi.se2018.model.exception.notValidCellException;

/**
 * a generic private tool card
 * @author Anton Ghobryal
 */

public class ToolCard extends Card {
    private int id;
    private Color color;
    private boolean used;

    /**
     * class constructor
     * @param title the title of this card
     * @param description the description of the card rules
     * @param id1 card's number
     * @param color1 color associated to the card
     */

    public ToolCard(String title, String description,int id1, Color color1){
        super(title, description);
        id = id1;
        color = color1;
        used = false;
    }

    /**
     * @return if the card was used till now or not
     */

    public boolean isUsed(){
        return used;
    }

    /**
     * @return card's id
     */

    public int getId() {
        return id;
    }

    /**
     * @return card's associated color
     */

    public Color getColor() {
        return color;
    }

    /**
     * sets the boolean if the card is being used
     * @param used1 pass true if it's used
     */

    public void setUsed(boolean used1) {
        used = used1;
    }

    /**
     * controls if the dice that the player chose is really on the map or not
     * @param map player's map
     * @param dice chose dice
     * @param row where the dice is
     * @param column where the dice is
     * @return if the map contains the dice or not
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    protected boolean mapContainsDice(Map map, Dice dice, int row, int column) throws notValidCellException {
        for(int i=0; i<map.numRow(); i++)
            for(int j=0; j<map.numColumn(); j++)
                if(map.getCell(i, j).getDice()!=null)
                    if(map.getCell(i, j).getDice().getValue()==dice.getValue()
                            && map.getCell(i, j).getDice().getColor().equalsColor(dice.getColor())) {
                        row = i;
                        column = j;
                        return true;
                    }
        row = 0;
        column = 0;
        return false;
    }

    /**
     * verify if the dice is in the round scheme or not
     * @param dice a chosen dice
     * @param roundSchemeMap The Round Scheme
     * @param pos where the dice is
     * @return a boolean whether the dice is there or not
     */

    protected boolean roundSchemeContainsDice(Dice dice, RoundSchemeCell[] roundSchemeMap, int pos){
        for(int i=0; i<roundSchemeMap.length; i++){
            if(roundSchemeMap[i].getDice(dice.getValue(), dice.getColor())!=null) {
                pos = i;
                return true;
            }
        }
        return false;
    }
}
