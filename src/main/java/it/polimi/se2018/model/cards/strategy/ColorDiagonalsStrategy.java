package it.polimi.se2018.model.cards.strategy;

import it.polimi.se2018.model.Map;

/**
 * Color Diagonals Public Objective Card
 * @author Anton Ghobryal
 */

public class ColorDiagonalsStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){ //kill score
        int counter=0;

        //inspection Left->Right
        for(int i=0; i<map.numRow()-1; i++){ //no control last column
            for(int j=0; j<map.numColumn()-1; j++){ //no control last row
                if(map.getCell(i,j).getDice()!=null&&map.getCell(i+1,j+1).getDice()!=null){
                    if(map.getCell(i,j).getDice().getColor().equals(map.getCell(i+1,j+1).getDice().getColor())){
                        if(counter==0)
                            counter = 2; //2 dices is the min number
                        else counter++;
                    }
                }
            }
        }

        //inspection Right->Left
        for(int i=map.numRow()-1; i>0; i--){
            for(int j=0; j<map.numColumn()-1; j++){
                if(map.getCell(i,j).getDice()!=null&&map.getCell(i-1,j+1).getDice()!=null){
                    if(map.getCell(i,j).getDice().getColor().equals(map.getCell(i-1,j+1).getDice().getColor())){
                        if(counter==0)
                            counter = 2; //2 dices is the min number
                        else counter++;
                    }
                }
            }
        }

        return counter;
    }
}
