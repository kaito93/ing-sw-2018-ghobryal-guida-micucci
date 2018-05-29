package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;

/**
 * Deep Shades Public Objective Card
 * @author Anton Ghobryal
 */

public class DeepShadesStrategy implements ObjectiveCardStrategy {

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){
        int counter5=0;
        int counter6=0;
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for(int j=0; j<map.numColumn(); j++){   try {
                //iterates on columns
                if(!map.isEmptyCell(i, j)){   //contols if there is a dice
                    if(map.getCell(i,j).getDice().getValue()==5)
                        counter5++;
                    else if(map.getCell(i,j).getDice().getValue()==6)
                        counter6++;
                }
                } catch (notValidCellException ex) {
                    System.out.println("deepShadeStrategy");
                }
            }
        }
        // the minimum of the counters is how many times the player achieved this card
        counter5=Math.min(counter5, counter6);
        return counter5*score;
    }
}
