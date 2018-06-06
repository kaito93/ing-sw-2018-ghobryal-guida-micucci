package it.polimi.se2018.model;

import it.polimi.se2018.model.exception.notValidMatrixException;
import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.model.cell.*;
import it.polimi.se2018.util.Logger;

import java.io.Serializable;
import java.util.logging.Level;

/** class Map
 * contains all the method to interact with the map
 * @author Andrea Micucci, Anton Ghobryal
 */

public class Map implements Serializable {

    private String name;
    private int difficultyLevel;
    private Cell[][] cell;

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static ErrorBool errorBool = new ErrorBool(null, false);
    
    /** class constructor initialize the object Map type
     * @param glassWindowName name of the matrix, refers to the corrispondent card
     * @param difficulty level of difficulty of the matrix
     * @param row number of row of the matrix
     * @param column number of column of the matrix
     * @throws notValidMatrixException when the values of row and column are not valid to construct the matrix
     */
    public Map(String glassWindowName, int difficulty, int row, int column) throws notValidMatrixException{
        difficultyLevel = difficulty;
        name = glassWindowName;
        cell = new Cell[row][column];
        if ((row < 0) || (column < 0))
            throw new notValidMatrixException();
    }
    
    /** method that return the difficulty level of the match
     * @return an integer between 3 and 6
     */
    //tested
    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    /** method that return the number of column of the matrix that represent the glasswindow
     * @return an integer between 0 and n
     */
    //tested
    public int numColumn(){
        return cell[0].length;
    }
    
    /** method that return the number of row in the matrix that represent the glasswindow
     * @return an integer between 0 and n
     */
    //tested
    public int numRow(){
        return cell.length;
    }
    
    /** method that return a single cell of a matrix that represent the glasswindow
     * @param row where you want to search
     * @param column where you want to search
     * @return an object Cell
     * @throws notValidCellException: when the indexes of the row and the column not respect the interval number of matrix.
     */
    //tested
    public Cell getCell(int row, int column) throws notValidCellException{
        if ((row < 0) || (column < 0) || (row > numRow()-1) || (column > numColumn()-1))
            throw new notValidCellException();
        return cell[row][column];
    }
    
    /** method that return the name of the map (that represent the glasswindow)
     * @return a string that represent the name of the glasswindow
     */
    //tested
    public String getName(){
        return name;
    }

    /** method that verify if a number exist already in a column of the matrix
     * @param column of the matrix where you want to search the number
     * @param value that you are searching for
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if value already exist in the column of matrix, else false
     */
    public boolean valueAlreadyExistInColumn(int column, int value) throws notValidCellException{
        int index;
        for(index = 0; index < numRow(); index++)
            if(!isEmptyCell(index, column) && getCell(index, column).getDice().getValue() == value)
                return true;
        return false;
    }

    /** method that verify if a color exist already in a column of the matrix
     * @param column of the matrix where you want to search the number
     * @param color that you are searching for
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if color already exist in the column of matrix, else false
     */
    public boolean colorAlreadyExistInColumn(int column, Color color) throws notValidCellException{
        int index;
        for(index = 0; index < numRow(); index++)
            if(!isEmptyCell(index, column) && getCell(index, column).getDice().getColor().equalsColor(color))
                return true;
        return false;
    }
    
    /** method that verify if a number exist already in a row of the matrix
     * @param row of the matrix where you want to search the number
     * @param value that you are searching
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if the value already exist in the row of matrix, else false
     */
    public boolean valueAlreadyExistInRow(int row, int value) throws notValidCellException{
        int index;
        for(index = 0; index < numRow(); index++)
            if(!isEmptyCell(row, index) && getCell(row, index).getDice().getValue() == value)
                return true;
        return false;
    }

    /** method that verify if a color exist already in a row of the matrix
     * @param row of the matrix where you want to search the number
     * @param color that you are searching
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if the color already exist in the row of matrix, else false
     */
    public boolean colorAlreadyExistInRow(int row, Color color) throws notValidCellException{
        int index;
        for(index = 0; index < numRow(); index++)
            if(!isEmptyCell(row, index) && getCell(row, index).getDice().getColor().equalsColor(color))
                return true;
        return false;
    }

    /**
     * controls if the borders on the map are empty or not
     * @return a boolean, if the borders are empty or not
     */
    public boolean isBorderEmpty(){ //first positioning rule
        for(int i=0; i<numColumn(); i++) //controls up & down borders
            if(!isEmptyCell(0, i) || !isEmptyCell(numRow()-1, i))
                return false;
        for(int i=0; i<numRow(); i++) //controls left & right borders
            if(!isEmptyCell(i, 0) || !isEmptyCell(i, numColumn()-1))
                return false;
        return true;
    }

    /**
     * verifies if there's an adjacent dice
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, true if there's  an adjacent dice else false
     */

    public boolean isAdjacentDice(int row, int column){
        if(row < 1 && column < 1)
            return upleftDice(row, column);
        else if(row > numRow() - 2 && column < 1)
            return downleftDice(row, column);
        else if(row > 0 && row <= numRow() - 2 && column < 1)
            return centreleftDice(row, column);
        else if(row < 1 && column > numColumn() - 2)
            return uprightDice(row, column);
        else if(row > numRow() - 2 && column > numColumn() - 2)
            return downrightDice(row, column);
        else if(row > 0 && row <= numRow() - 2 && column > numColumn() - 2)
            return centrerightDice(row, column);
        else if(row < 1 && column > 0 && column <= numColumn() - 2)
            return upcentreDice(row, column);
        else if(row > numRow() - 2 && column > 0 && column <= numColumn() - 2)
            return downcentreDice(row, column);
        else if(row > 0 && row < numRow() - 1 && column > 0 && column < numColumn() - 1)
            return centreDice(row, column);
        return false;
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean upleftDice(int row, int column){
        return !isEmptyCell(row, column+1) || !isEmptyCell(row+1, column+1) || !isEmptyCell(row+1, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean downleftDice(int row, int column){
        return !isEmptyCell(row, column+1) || !isEmptyCell(row-1, column) || !isEmptyCell(row-1, column+1);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean centreleftDice(int row, int column){
        return upleftDice(row, column) || downleftDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean uprightDice(int row, int column){
        return !isEmptyCell(row, column-1) || !isEmptyCell(row+1, column-1) || !isEmptyCell(row+1, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean downrightDice(int row, int column){
        return !isEmptyCell(row-1, column) || !isEmptyCell(row-1, column-1) || !isEmptyCell(row, column-1);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean centrerightDice(int row, int column){
        return uprightDice(row, column) || downrightDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean upcentreDice(int row, int column){
        return uprightDice(row, column) || upleftDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean downcentreDice(int row, int column){
        return downrightDice(row, column) || downleftDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    public boolean centreDice(int row, int column){
        return centreleftDice(row, column) || centrerightDice(row, column);
    }

    /**
     * verifies if the dice respects the cell restriction if there's any
     * @param dice a chosen dice
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, if the dice respects the cell restriction else false
     */
    public boolean isCellValid(Dice dice, int row, int column){
        if(!isEmptyCell(row, column)){
            errorBool.setErrorMessage("There's a dice on the same cell");
            errorBool.setErrBool(true);
            return false;
        }else if(diceCompatibleCell(row, column, 0, null)){
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return true;
        }
        else if(diceCompatibleCell(row, column, dice.getValue(), null)) {
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return diceCompatibleCell(row, column, dice.getValue(), null);
        }
        else if(diceCompatibleCell(row, column, 0, dice.getColor())) {
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return diceCompatibleCell(row, column, 0, dice.getColor());
        }
        errorBool.setErrorMessage("This is not a valid cell");
        errorBool.setErrBool(true);
        return false;
    }

    /**
     * verifies if the chosen dice is compatible with cell in which the dice would be positioned
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @param value chosen dice's value
     * @param color chosen dice's color
     * @return a boolean, true if the dice is compatible else false
     */
    public boolean diceCompatibleCell(int row, int column, int value, Color color){
        return cell[row][column].getValue()==value && cell[row][column].getColor().equalsColor(color);
    }

    /**
     * positions the dice if it's possibile and it respects all the restrictions
     * @param dice a chosen dice
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, true if the dice can be positioned else false
     */

    public boolean posDice(Dice dice, int row, int column) {
        if(isBorderEmpty() && ((column>0 && row>0) && (row<numRow()-1 && column<numColumn()-1))) {
            errorBool.setErrorMessage("Player has to position the dice on the border for beginning");
            errorBool.setErrBool(true);
            return false;
        }
        else if(isCellValid(dice, row, column) && isBorderEmpty()
                && ((column>0 && row>0) && (row<numRow()-1 && column<numColumn()-1))){
            cell[row][column].setDice(dice);
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return true;
        }
        else if(!isBorderEmpty() && isAdjacentDice(row, column)) {
            try {
                if(isCellValid(dice, row, column) && !colorAlreadyExistInColumn(column, dice.getColor())
                        && !colorAlreadyExistInRow(row, dice.getColor()) && !valueAlreadyExistInColumn(column, dice.getValue())
                        && !valueAlreadyExistInRow(row, dice.getValue())) {
                    cell[row][column].setDice(dice);
                    errorBool.setErrorMessage(null);
                    errorBool.setErrBool(false);
                    return true;
                }
            } catch (notValidCellException e) {
                LOGGER.log(Level.SEVERE, e.toString()+"\nposDice method in Map class", e);
            }
        }
        errorBool.setErrorMessage("Player doesn't respect the positioning rules");
        errorBool.setErrBool(true);
        return false;
    }

    /**
     * controls if the cell's empty or not
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if there's no dice positioned on a chosen cell
     */

    public boolean isEmptyCell(int row, int column){
        return cell[row][column].getDice()==null;
    }

    /**
     * calculates the number of empty cells
     * @return an integer with the number of empty cells
     */

    public int emptyCells(){
        int counter = 0;
        for(int i=0; i<numRow(); i++)
            for(int j=0; j<numColumn(); j++)
                if(isEmptyCell(i, j))
                    counter++;
        return counter;
    }

    /**
     * @return a matrix of cells
     */
    public Cell[][] getCell() {
        return cell;
    }

    /**
     * destroys map
     */
    @Override
    @SuppressWarnings("Deprecated")
    public void finalize(){
        name = null;
        difficultyLevel = 0;
        cell = null;
        System.gc();
    }

    /**
     * incapsulates the error message if there is any
     * @return an ErrorBool type that incapsulates the error message if there is any
     */
    public static ErrorBool getErrorBool() {
        return errorBool;
    }
}
