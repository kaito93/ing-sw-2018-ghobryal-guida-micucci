package it.polimi.se2018.model.cards.public_objective_card_strategy;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.notValidCellException;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Color Diagonals Public Objective Card
 * @author Anton Ghobryal
 */

public class ColorDiagonalsStrategy extends ObjectiveCardStrategy{

    private ArrayList<Cell> temp = new ArrayList<>();

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

        try {
            for(int row = 0; row<map.numRow(); row++)
                for(int column = 0; column<map.numColumn(); column++)
                    visitor(map, row, column);
            counter = temp.size();
        } catch (notValidCellException e) {
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

    private void visitor(Map map, int row, int column) throws notValidCellException{
        if(!map.isEmptyCell(row, column)) {
            try {
                if (row < 1 && column < 1 && map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column + 1).getDice().getColor())
                        && !temp.contains(map.getCell(row + 1, column + 1))) { //up left control
                    visitor(map, row + 1, column + 1);
                    temp.add(map.getCell(row, column));
                } else if (row > map.numRow() - 2 && column < 1 && map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column + 1).getDice().getColor())
                        && !temp.contains(map.getCell(row - 1, column + 1))) { //down left control
                    visitor(map, row - 1, column + 1);
                    temp.add(map.getCell(row, column));
                } else if (row > 0 && row <= map.numRow() - 2 && column < 1) { //centre left control
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column + 1).getDice().getColor())
                            && !temp.contains(map.getCell(row - 1, column + 1))) {
                        visitor(map, row - 1, column + 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column + 1).getDice().getColor())
                            && !temp.contains(map.getCell(row + 1, column + 1))) {
                        visitor(map, row + 1, column + 1);
                        temp.add(map.getCell(row, column));
                    }
                } else if (row < 1 && column > map.numColumn() - 2 && map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column - 1).getDice().getColor())
                        && !temp.contains(map.getCell(row + 1, column - 1))) { //up right control
                    visitor(map, row + 1, column - 1);
                    temp.add(map.getCell(row, column));
                } else if (row > map.numRow() - 2 && column > map.numColumn() - 2 && map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column - 1).getDice().getColor())
                        && !temp.contains(map.getCell(row - 1, column - 1))) { //down right control
                    visitor(map, row - 1, column - 1);
                    temp.add(map.getCell(row, column));
                } else if (row > 0 && row <= map.numRow() - 2 && column > map.numColumn() - 2) { //centre right control
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column - 1).getDice().getColor())
                            && !temp.contains(map.getCell(row - 1, column - 1))) {
                        visitor(map, row - 1, column - 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column - 1).getDice().getColor())
                            && !temp.contains(map.getCell(row + 1, column - 1))) {
                        visitor(map, row + 1, column - 1);
                        temp.add(map.getCell(row, column));
                    }
                } else if (row < 1 && column > 0 && column <= map.numColumn() - 2) { //centre up control
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column - 1).getDice().getColor())
                            && !temp.contains(map.getCell(row + 1, column - 1))) {
                        visitor(map, row + 1, column - 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column + 1).getDice().getColor())
                            && !temp.contains(map.getCell(row + 1, column + 1))) {
                        visitor(map, row + 1, column + 1);
                        temp.add(map.getCell(row, column));
                    }
                } else if (row > map.numRow() - 2 && column > 0 && column <= map.numColumn() - 2) { //centre down control
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column - 1).getDice().getColor())
                            && !temp.contains(map.getCell(row - 1, column - 1))) {
                        visitor(map, row - 1, column - 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column + 1).getDice().getColor())
                            && !temp.contains(map.getCell(row - 1, column + 1))) {
                        visitor(map, row - 1, column + 1);
                        temp.add(map.getCell(row, column));
                    }
                } else if (row > 0 && row < map.numRow() - 1 && column > 0 && column < map.numColumn() - 1) { //centre control
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column - 1).getDice().getColor())
                            && !temp.contains(map.getCell(row - 1, column - 1))) {
                        visitor(map, row - 1, column - 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row - 1, column + 1).getDice().getColor())
                            && !temp.contains(map.getCell(row - 1, column + 1))) {
                        visitor(map, row - 1, column + 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column - 1).getDice().getColor())
                            && !temp.contains(map.getCell(row + 1, column - 1))) {
                        visitor(map, row + 1, column - 1);
                        temp.add(map.getCell(row, column));
                    }
                    if (map.getCell(row, column).getDice().getColor().equalsColor(map.getCell(row + 1, column + 1).getDice().getColor())
                            && !temp.contains(map.getCell(row + 1, column + 1))) {
                        visitor(map, row + 1, column + 1);
                        temp.add(map.getCell(row, column));
                    }
                }
            } catch (notValidCellException e) {
                LOGGER.log(Level.SEVERE, e.toString()+"\nvisitor method in class ColorDiagonalStrategy", e);
                throw new notValidCellException();
            }
        }
    }
}
