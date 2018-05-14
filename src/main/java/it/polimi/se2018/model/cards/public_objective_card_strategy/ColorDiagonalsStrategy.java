package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Color Diagonals Public Objective Card
 * @author Anton Ghobryal
 */

public class ColorDiagonalsStrategy extends ObjectiveCardStrategy{

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score the score the player achieves out of this card
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     * @return how many times the player achieves this card multiplied to its score
     */

    @Override
    public int search(Map map, int score){ //kill score
        int counter=0;

        //inspection Left->Right
        for(int i=0; i<map.numRow()-1; i++){ //no control last column
            for(int j=0; j<map.numColumn()-1; j++){ try {
                //no control last row
                if(map.getCell(i,j).getDice()!=null&&map.getCell(i+1,j+1).getDice()!=null){
                    if(map.getCell(i,j).getDice().getColor().equals(map.getCell(i+1,j+1).getDice().getColor())){
                        if(counter==0)
                            counter = 2; //2 dices is the min number
                        else counter++;
                    }
                }
                } catch (notValidCellException ex) {
                    System.out.println("colorDiagonalStrategy");
                }
            }
        }

        //inspection Right->Left
        for(int i=map.numRow()-1; i>0; i--){
            for(int j=0; j<map.numColumn()-1; j++){
                try {
                    if(map.getCell(i,j).getDice()!=null&&map.getCell(i-1,j+1).getDice()!=null){
                        if(map.getCell(i,j).getDice().getColor().equals(map.getCell(i-1,j+1).getDice().getColor())){
                            if(counter==0)
                                counter = 2; //2 dices is the min number
                            else counter++;
                        }
                    }
                } catch (notValidCellException ex) {
                    System.out.println("colorDiagonalStrategy");
                }
            }
        }

        return counter;
    }
}
