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
                    visitor(map, row, column); //visita tutte le celle della matrice
                }
            counter = temp.size(); //ritorna il numero di dadi in diagonale
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
            // controllo le diagonali non visitati rispetto alla mia posizione attuale
            if(!map.isEmptyCell(row, column)) { //se la cella non è vuota,
                if (row < 1 && column < 1 ) { // controlla alto sinistra della mappa
                    downrightcontrol(map, row, column); //controlla basso destra rispetto alla mia posizione attuale
                } else if (row > map.numRow() - 2 && column < 1 ) { //controlla basso sinistra sulla mappa
                    uprightcontrol(map, row, column); //controlla alto destra rispetto alla mia posizione attuale
                } else if (row > 0 && row <= map.numRow() - 2 && column < 1) { //controlla centrale sinistra della mappa
                    uprightcontrol(map, row, column);
                    downrightcontrol(map, row, column);
                } else if (row < 1 && column > map.numColumn() - 2) { //controlla alto destra della mappa
                    downleftcontrol(map, row, column); //controlla basso sinistra rispetto alla mia posizione attuale
                } else if (row > map.numRow() - 2 && column > map.numColumn() - 2) { //controlla basso destra della mappa
                    upleftcontrol(map, row, column); //controlla alto sinistra rispetto alla mia posizione attuale
                } else if (row > 0 && row <= map.numRow() - 2 && column > map.numColumn() - 2) { //controlla centrale destra della mappa
                    upleftcontrol(map, row, column);
                    downleftcontrol(map, row, column);
                } else if (row < 1 && column > 0 && column <= map.numColumn() - 2) { //controlla alto centro della mappa
                    downleftcontrol(map, row, column);
                    downrightcontrol(map, row, column);
                } else if (row > map.numRow() - 2 && column > 0 && column <= map.numColumn() - 2) { //controlla basso centrale della mappa
                    upleftcontrol(map, row, column);
                    uprightcontrol(map, row, column);
                } else if (row > 0 && row < map.numRow() - 1 && column > 0 && column < map.numColumn() - 1) { //controlla centro mappa
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

    /**
     * controls the up right part of the map
     * @param map the player's map
     * @param row current row coordinate on the map
     * @param column current column coordinate on the map
     * @throws NotValidCellException if the cell is not valid
     */
    private void uprightcontrol(Map map, int row, int column) throws NotValidCellException {
        try {
            //se i dadi sono di colore uguale salva i dadi che non sono già salvati nell'arraylist
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

    /**
     * controls the down right part of the map
     * @param map the player's map
     * @param row current row coordinate on the map
     * @param column current column coordinate on the map
     * @throws NotValidCellException if the cell is not valid
     */
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

    /**
     * controls the up left part of the map
     * @param map the player's map
     * @param row current row coordinate on the map
     * @param column current column coordinate on the map
     * @throws NotValidCellException if the cell is not valid
     */
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

    /**
     * controls the down left part of the map
     * @param map the player's map
     * @param row current row coordinate on the map
     * @param column current column coordinate on the map
     * @throws NotValidCellException if the cell is not valid
     */
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
