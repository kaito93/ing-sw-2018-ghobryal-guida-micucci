package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.server.model.Map;

/**
 * Light Shades Public Objective Card
 * @author Anton Ghobryal
 */

public class LightShadesStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){
        int counter1=0;
        int counter2=0;
        for(int i=0; i<map.numRow(); i++){  //iterates on rows
            for(int j=0; j<map.numColumn(); j++){
                //iterates on columns
                if(!map.isEmptyCell(i, j)){   //contols if there is a dice
                    if(map.getCell(i,j).getDice().getValue()==1)
                        counter1++;
                    else if(map.getCell(i,j).getDice().getValue()==2)
                        counter2++;
                }
            }
        }
        // the minimum of the counters is how many times the player achieved this card
        counter1=Math.min(counter1, counter2);
        return counter1*score;
    }
}
