package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.exception.notValidCellException;

/**
 * Cork-backed Straightedge Tool Card
 * @author Anton Ghobryal
 */

public class CorkbackedStraightedge extends ToolCard{
    /**
     * class constructor
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public CorkbackedStraightedge(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    public boolean useTool(Map map, Dice dice, int row, int column) throws notValidCellException {
        if(map.getCell(row, column).getDice()!=null)
            return false;
        else if(row<1 && column<1) { //up left control
            if (map.getCell(row, column + 1).getDice() != null || map.getCell(row + 1, column).getDice() != null
                    || map.getCell(row + 1, column + 1).getDice() != null)
                return false;
        }else if(row > 0 && row<map.numRow()-1 && column<1){ //centre left control
            if (map.getCell(row, column + 1).getDice() != null || map.getCell(row + 1, column).getDice() != null
                    || map.getCell(row + 1, column + 1).getDice() != null
                    || map.getCell(row-1, column).getDice()!=null || map.getCell(row-1, column+1).getDice()!=null)
                return false;
        }else if(row>map.numRow()-2 && column<1){ // down left control
            if(map.getCell(row-1, column).getDice()!=null || map.getCell(row-1, column+1).getDice()!=null
                    || map.getCell(row, column+1).getDice()!=null)
                return false;
        }else if(row<1 && column>0 && column<map.numColumn()-1){ //centre up control
            if(map.getCell(row, column-1).getDice()!=null ||  map.getCell(row, column+1).getDice()!=null
                    || map.getCell(row+1, column-1).getDice()!=null || map.getCell(row+1, column).getDice()!=null
                    || map.getCell(row+1, column+1).getDice()!=null)
                return false;
        }else if(row>0 && column>0 && row<map.numRow()-1 && column<map.numColumn()-1){ //centre control
            if(map.getCell(row, column-1).getDice()!=null ||  map.getCell(row, column+1).getDice()!=null
                    || map.getCell(row+1, column-1).getDice()!=null || map.getCell(row+1, column).getDice()!=null
                    || map.getCell(row+1, column+1).getDice()!=null || map.getCell(row-1, column-1).getDice()!=null
                    || map.getCell(row-1, column).getDice()!=null || map.getCell(row-1, column+1).getDice()!=null)
                return false;
        }else if(row>map.numRow()-2 && column>0 && column<map.numColumn()-1){ //centre down control
            if(map.getCell(row-1, column).getDice()!=null || map.getCell(row-1, column+1).getDice()!=null
                    || map.getCell(row, column+1).getDice()!=null || map.getCell(row-1, column-1).getDice()!=null
                    || map.getCell(row, column-1).getDice()!=null)
                return false;
        }else if(column>map.numColumn()-2 && row<1){ //up right control
            if(map.getCell(row, column-1).getDice()!=null || map.getCell(row+1, column-1).getDice()!=null
                    || map.getCell(row+1, column).getDice()!=null)
                return false;
        }else if(row>0 && row<map.numRow()-2 && column>map.numColumn()-2){ //centre right control
            if(map.getCell(row, column-1).getDice()!=null || map.getCell(row+1, column-1).getDice()!=null
                    || map.getCell(row+1, column).getDice()!=null || map.getCell(row-1, column-1).getDice()!=null
                    || map.getCell(row-1, column).getDice()!=null)
                return false;
        }else if(row>map.numRow()-2 && column>map.numColumn()-2){ //down right
            if(map.getCell(row-1, column).getDice()!=null || map.getCell(row-1, column-1).getDice()!=null
                    || map.getCell(row, column-1).getDice()!=null)
                return false;
        }
        return map.setCell(dice, row, column);
    }
}
