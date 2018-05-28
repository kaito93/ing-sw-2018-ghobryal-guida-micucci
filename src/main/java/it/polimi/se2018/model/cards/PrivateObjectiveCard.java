package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Color;

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

    public int search(Map map){
        int score=0;
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for (int j=0; j<map.numColumn(); j++){  try {
                //iterates on columns
                if(map.getCell(i, j).getDice()!=null)   //there has to be a Dice
                    if(map.getCell(i, j).getDice().getColor().equals(color))    //the card's color should match the Dice's color
                        score += map.getCell(i, j).getDice().getValue();    //sums the Dice's value
                } catch (notValidCellException ex) {
                    System.out.println("PrivateObjectiveCards");
                }
            }
        }
        return score;
    }
}
