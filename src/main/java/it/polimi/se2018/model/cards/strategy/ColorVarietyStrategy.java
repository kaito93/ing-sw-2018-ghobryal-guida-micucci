package it.polimi.se2018.model.cards.strategy;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Map;

/**
 * Color Variety Public Objective Card
 * @author Anton Ghobryal
 */

public class ColorVarietyStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){
        int counterBlue=0;
        int counterGreen=0;
        int counterYellow=0;
        int counterRed=0;
        int counterPurble=0;

        //counts how many dices of each color
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn(); j++){
                if(map.getCell(i,j).getDice()!=null){
                    if(map.getCell(i,j).getDice().getColor().equals(Color.BLUE))
                        counterBlue++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.GREEN))
                        counterGreen++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.YELLOW))
                        counterYellow++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.RED))
                        counterRed++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.PURPLE))
                        counterPurble++;
                }
            }
        }

        // the minimum of the counters is how many times the player achieved this card
        counterBlue=Math.min(counterBlue, counterGreen);
        counterBlue=Math.min(counterBlue, counterYellow);
        counterBlue=Math.min(counterBlue, counterRed);
        counterBlue=Math.min(counterBlue, counterPurble);
        return counterBlue*score;
    }
}
