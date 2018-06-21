package it.polimi.se2018.server.controller.public_objective_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.shared.exception.NotValidCellException;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Color Diagonals Public Objective Card
 * @author Anton Ghobryal
 */

public class ColorDiagonalsStrategy extends ObjectiveCardStrategy{

    private ArrayList<Dice> temp;

    public ColorDiagonalsStrategy(){
        super();
    }

    /**
     * Read description of this card for further information
     * @param map player's map
     * @param score n.a.
     * @return the number of dices with the same color on diagonals
     */

    @Override
    public int search(Map map, int score) { //kill score
        int counter=0; //controls the number of visited dices
        temp = new ArrayList<>();

        try {
            for(int row = 0; row<map.numRow(); row++)
                for(int column = 0; column<map.numColumn(); column++){
                    visitor(map, row, column);
                }
            for(int i=0; i<temp.size(); i++)
            counter = temp.size();
        } catch (NotValidCellException e) {
            LOGGER.log(Level.SEVERE, e.toString()+"\nsearch method in class ColorDiagonalStrategy", e);
        }
        temp = null;
        return counter;
    }

    /**
     * visits the map's diagonals if they
     * @param map player's map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     */

    private void visitor(Map map, int row, int column) throws NotValidCellException {
        try {
            if(!map.isEmptyCell(row, column)) {
                if (row < 1 && column < 1 ) { //up left control
                    downrightcontrol(map, row, column);
                } else if (row > map.numRow() - 2 && column < 1 ) { //down left control
                    uprightcontrol(map, row, column);
                } else if (row > 0 && row <= map.numRow() - 2 && column < 1) { //centre left control
                    uprightcontrol(map, row, column);
                    downrightcontrol(map, row, column);
                } else if (row < 1 && column > map.numColumn() - 2) { //up right control
                    downleftcontrol(map, row, column);
                } else if (row > map.numRow() - 2 && column > map.numColumn() - 2) { //down right control
                    upleftcontrol(map, row, column);
                } else if (row > 0 && row <= map.numRow() - 2 && column > map.numColumn() - 2) { //centre right control
                    upleftcontrol(map, row, column);
                    downleftcontrol(map, row, column);
                } else if (row < 1 && column > 0 && column <= map.numColumn() - 2) { //centre up control
                    downleftcontrol(map, row, column);
                    downrightcontrol(map, row, column);
                } else if (row > map.numRow() - 2 && column > 0 && column <= map.numColumn() - 2) { //centre down control
                    upleftcontrol(map, row, column);
                    uprightcontrol(map, row, column);
                } else if (row > 0 && row < map.numRow() - 1 && column > 0 && column < map.numColumn() - 1) { //centre control
                    upleftcontrol(map, row, column);
                    uprightcontrol(map, row, column);
                    downleftcontrol(map, row, column);
                    downrightcontrol(map, row, column);
                }
            }
            } catch (NotValidCellException e) {
                LOGGER.log(Level.SEVERE, e.toString()+"\nvisitor method in class ColorDiagonalStrategy", e);
                throw new NotValidCellException();
            }
    }


    private void uprightcontrol(Map map, int row, int column) throws NotValidCellException {
        try {
            if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column + 1).getDice().getColor())) {
                if(!temp.contains(map.getCell(row, column).getDice()))
                    temp.add(map.getCell(row, column).getDice());
                if(!temp.contains(map.getCell(row - 1, column + 1).getDice())) {
                    temp.add(map.getCell(row - 1, column + 1).getDice());
                    visitor(map, row - 1, column + 1);
                }
            }
        } catch (NullPointerException e) {
            // va bene così

        }
    }

    private void downrightcontrol(Map map, int row, int column) throws NotValidCellException {
        try {
            if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column + 1).getDice().getColor())) {
                if(!temp.contains(map.getCell(row, column).getDice()))
                    temp.add(map.getCell(row, column).getDice());
                if(!temp.contains(map.getCell(row + 1, column + 1).getDice())) {
                    temp.add(map.getCell(row + 1, column + 1).getDice());
                    visitor(map, row + 1, column + 1);
                }
            }
        } catch (NullPointerException e) {
            // va bene così

        }
    }

    private void upleftcontrol(Map map, int row, int column) throws NotValidCellException {
        try {
            if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column - 1).getDice().getColor())) {
                if(!temp.contains(map.getCell(row, column).getDice()))
                    temp.add(map.getCell(row, column).getDice());
                if(!temp.contains(map.getCell(row - 1, column - 1).getDice())) {
                    temp.add(map.getCell(row - 1, column - 1).getDice());
                    visitor(map, row - 1, column - 1);
                }
            }
        } catch (NullPointerException e) {
            // va bene così
        }
    }

    private void downleftcontrol(Map map, int row, int column) throws NotValidCellException {
        try {
            if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column - 1).getDice().getColor())) {
                if(!temp.contains(map.getCell(row, column).getDice()))
                    temp.add(map.getCell(row, column).getDice());
                if(!temp.contains(map.getCell(row + 1, column - 1).getDice())) {
                    temp.add(map.getCell(row + 1, column - 1).getDice());
                    visitor(map, row + 1, column - 1);
                }
            }
        } catch (NullPointerException e) {
            // va bene così
        }
    }
}
