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
    public int getDifficultyLevel(){
        return difficultyLevel;
    }

    /** method that return the number of column of the matrix that represent the glasswindow
     * @return an integer between 0 and n
     */
    public int numColumn(){
        return cell[0].length;
    }
    
    /** method that return the number of row in the matrix that represent the glasswindow
     * @return an integer between 0 and n
     */
    public int numRow(){
        return cell.length;
    }
    
    /** method that return a single cell of a matrix that represent the glasswindow
     * @param row where you want to search
     * @param column where you want to search
     * @return an object Cell
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public Cell getCell(int row, int column) throws notValidCellException{
        if ((row < 0) || (column < 0) || (row > numRow()-1) || (column > numColumn()-1))
            throw new notValidCellException();
        return cell[row][column];
    }
    
    /** method that return the name of the map (that represent the glasswindow)
     * @return a string that represent the name of the glasswindow
     */
    public String getName(){
        return name;
    }

    /** method that verify if there is an Adjacent dice with the same value
     * @param row row of the matrix where you want to search the value
     * @param column row of the matrix where you want to search the value
     * @param value the value that you are searching
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if there is an Adjacent dice with the same value, else false
     */
    public boolean isAdjacentValue(int row, int column, int value) throws notValidCellException{
        if(row < 1 && column < 1)
            return (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getValue()==value)
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getValue()==value);
        else if(row < 1 && column > numColumn() - 2)
            return (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getValue()==value)
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getValue()==value);
        else if(row < 1 && column > 0 && column <= numColumn() - 2)
            return (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getValue()==value)
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getValue()==value);
        else if(row > numRow() - 2 && column < 1)
            return (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getValue()==value)
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getValue()==value);
        else if(row > numRow() - 2 && column > numColumn() - 2)
            return (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getValue()==value);
        else if(row > numRow() - 2 && column > 0 && column <= numColumn() - 2)
            return (!isEmptyCell(row-1, column)  && getCell(row-1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getValue()==value)
                    || (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getValue()==value);
        else if(row > 0 && row <= numRow() - 2 && column < 1)
            return (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getValue()==value)
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getValue()==value);
        else if(row > 0 && row <= numRow() - 2 && column > numColumn() - 2)
            return (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getValue()==value)
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getValue()==value);
        else if(row > 0 && row < numRow() - 1 && column > 0 && column < numColumn() - 1)
            return (!isEmptyCell(row, column+1)  && getCell(row, column+1).getDice().getValue()==value)
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getValue()==value)
                    || (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getValue()==value);
        return false;
    }

    /** method that verify if there is an Adjacent dice with the same color
     * @param row row of the matrix where you want to search the color
     * @param column row of the matrix where you want to search the color
     * @param color the color that you are searching
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     * @return a boolean that is true if there is an Adjacent dice with the same color, else false
     */
    public boolean isAdjacentColor(int row, int column, Color color) throws notValidCellException{
        if(row < 1 && column < 1)
            return (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getColor().equalsColor(color));
        else if(row < 1 && column > numColumn() - 2)
            return (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getColor().equalsColor(color));
        else if(row < 1 && column > 0 && column <= numColumn() - 2)
            return (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getColor().equalsColor(color));
        else if(row > numRow() - 2 && column < 1)
            return (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getColor().equalsColor(color));
        else if(row > numRow() - 2 && column > numColumn() - 2)
            return (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getColor().equalsColor(color));
        else if(row > numRow() - 2 && column > 0 && column <= numColumn() - 2)
            return (!isEmptyCell(row-1, column)  && getCell(row-1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getColor().equalsColor(color));
        else if(row > 0 && row <= numRow() - 2 && column < 1)
            return (!isEmptyCell(row, column+1) && getCell(row, column+1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getColor().equalsColor(color));
        else if(row > 0 && row <= numRow() - 2 && column > numColumn() - 2)
            return (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getColor().equalsColor(color));
        else if(row > 0 && row < numRow() - 1 && column > 0 && column < numColumn() - 1)
            return (!isEmptyCell(row, column+1)  && getCell(row, column+1).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row+1, column) && getCell(row+1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row-1, column) && getCell(row-1, column).getDice().getColor().equalsColor(color))
                    || (!isEmptyCell(row, column-1) && getCell(row, column-1).getDice().getColor().equalsColor(color));
        return false;
    }

    /**
     * controls if the borders on the map are empty or not
     * @return a boolean, if the borders are empty or not
     */
    public boolean isBorderEmpty(){ //first positioning rule
        boolean control=false;
        for(int i=0; i<numColumn(); i++) //controls up & down borders
            if(!isEmptyCell(0, i) || !isEmptyCell(numRow()-1, i)){
                control=true;
                break;
            }
        if(!control)
            for(int i=1; i<numRow()-1; i++) //controls left & right borders
                if(!isEmptyCell(i, 0) || !isEmptyCell(i, numColumn()-1)) {
                    control=true;
                    break;
                }
        return !control;
    }

    /**
     * verifies if there's an adjacent dice
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, true if there's  an adjacent dice else false
     */
    public boolean isAdjacentDice(int row, int column){
        boolean a = upcontrol(row, column);
        boolean b = downcontrol(row, column);
        boolean c = centrecontrol(row, column);
        return a || b || c;
    }

    /**
     * controls the high part of the map if there's an Adjacent Dice
     * @helper a helper method for isAdjacentDice method
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, true if there's  an adjacent dice else false
     */
    private boolean upcontrol(int row, int column){
        if(row < 1 && column < 1)
            return upleftDice(row, column);
        else if(row < 1 && column > numColumn() - 2)
            return uprightDice(row, column);
        else if(row < 1 && column > 0 && column <= numColumn() - 2)
            return upcentreDice(row, column);
        return false;
    }

    /**
     * controls the low part of the map if there's an Adjacent Dice
     * @helper a helper method for isAdjacentDice method
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, true if there's  an adjacent dice else false
     */
    private boolean downcontrol(int row, int column){
        if(row > numRow() - 2 && column < 1)
            return downleftDice(row, column);
        else if(row > numRow() - 2 && column > numColumn() - 2)
            return downrightDice(row, column);
        else if(row > numRow() - 2 && column > 0 && column <= numColumn() - 2)
            return downcentreDice(row, column);
        return false;
    }

    /**
     * controls the centre part of the map if there's an Adjacent Dice
     * @helper a helper method for isAdjacentDice method
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @return a boolean, true if there's  an adjacent dice else false
     */
    private boolean centrecontrol(int row, int column){
        if(row > 0 && row <= numRow() - 2 && column < 1)
            return centreleftDice(row, column);
        else if(row > 0 && row <= numRow() - 2 && column > numColumn() - 2)
            return centrerightDice(row, column);
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

    private boolean upleftDice(int row, int column){
        return !isEmptyCell(row, column+1) || !isEmptyCell(row+1, column+1) || !isEmptyCell(row+1, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean downleftDice(int row, int column){
        return !isEmptyCell(row, column+1) || !isEmptyCell(row-1, column) || !isEmptyCell(row-1, column+1);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean centreleftDice(int row, int column){
        return upleftDice(row, column) || downleftDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean uprightDice(int row, int column){
        return !isEmptyCell(row, column-1) || !isEmptyCell(row+1, column-1) || !isEmptyCell(row+1, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean downrightDice(int row, int column){
        return !isEmptyCell(row-1, column) || !isEmptyCell(row-1, column-1) || !isEmptyCell(row, column-1);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean centrerightDice(int row, int column){
        return uprightDice(row, column) || downrightDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean upcentreDice(int row, int column){
        return uprightDice(row, column) || upleftDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean downcentreDice(int row, int column){
        return downrightDice(row, column) || downleftDice(row, column);
    }

    /**
     * a helper method which controls the dices's existence on some portion of the map
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     * @return a boolean, true if exist at least one adjacent dice on the portion else false
     */

    private boolean centreDice(int row, int column){
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
        }else if(diceCompatibleCell(row, column, 0, Color.NULL)){
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return true;
        }
        else if(diceCompatibleCell(row, column, dice.getValue(), Color.NULL)) {
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return diceCompatibleCell(row, column, dice.getValue(), Color.NULL);
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
    private boolean diceCompatibleCell(int row, int column, int value, Color color){
        if(diceCompatibleValueCell(row, column, value))
            return cell[row][column].getValue()==value;
        else if(cell[row][column].getValue()==0 && cell[row][column].getColor().equalsColor(Color.NULL))
            return true;
        else if(diceCompatibleColorCell(row, column, color))
            return color.equalsColor(cell[row][column].getColor());
        return false;
    }

    /**
     * verifies if the chosen dice is compatible with cell in which the dice would be positioned
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @param value chosen dice's value
     * @return a boolean, true if the dice is compatible else false
     */
    public boolean diceCompatibleValueCell(int row, int column, int value){
        if(cell[row][column].getValue()!=0)
            return cell[row][column].getValue()==value;
        else return false;
    }

    /**
     * verifies if the chosen dice is compatible with cell in which the dice would be positioned
     * @param row row's coordinate on the map where to position the dice
     * @param column column's coordinate on the map where to position the dice
     * @param color chosen dice's color
     * @return a boolean, true if the dice is compatible else false
     */
    public boolean diceCompatibleColorCell(int row, int column, Color color){
        if(!cell[row][column].getColor().equalsColor(Color.NULL))
            return color.equalsColor(cell[row][column].getColor());
        else return false;
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
                && ((column==0 || row==0) || (row==numRow()-1 || column==numColumn()-1))){
            cell[row][column].setDice(dice);
            errorBool.setErrorMessage(null);
            errorBool.setErrBool(false);
            return true;
        }
        else if(!isBorderEmpty() && isAdjacentDice(row, column)) {
            try {
                if(isCellValid(dice, row, column) && !isAdjacentColor(row, column, dice.getColor())
                        && !isAdjacentValue(row, column, dice.getValue())) {
                    cell[row][column].setDice(dice);
                    errorBool.setErrorMessage(null);
                    errorBool.setErrBool(false);
                    return true;
                }
            } catch (notValidCellException e) {
                LOGGER.log(Level.SEVERE, e.toString()+"\nposDice method in Map class", e);
                return false;
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
        try {
            return cell[row][column].getDice() == null;
        }catch (NullPointerException e){
            return true;
        }
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
    public Cell[][] getCells() {
        return cell;
    }

    /**
     * destroys map
     */
    @Override
    @SuppressWarnings("Deprecated")
    public void finalize() throws Throwable{
        for(int i=0; i<numRow(); i++)
            for(int j=0; j<numColumn(); j++) {
                cell[i][j].setDice(null);
                cell[i][j]=null;
            }
        System.gc();
        super.finalize();
    }

    /**
     * incapsulates the error message if there is any
     * @return an ErrorBool type that incapsulates the error message if there is any
     */
    public static ErrorBool getErrorBool() {
        return errorBool;
    }

    /**
     * removes the dice in the specified coordinates
     * @param row row's coordinate on the map
     * @param column column's coordinate on the map
     */
    public void removeDiceMap(int row, int column){
        if(!isEmptyCell(row, column))
            cell[row][column].setDice(null);
    }

    /**
     * controls if the passed dice is really in the passed coordinates or not
     * @param dice the supposed dice on cell
     * @param row row's coordinate on the map where there should be the passed dice
     * @param column column's coordinate on the map where there should be the passed dice
     * @return a boolean, true if the dice is compatible with the dice on the cell, else false
     */
    public boolean existDice(Dice dice, int row, int column){
        try {
            return cell[row][column].getDice().getValue()== dice.getValue() && cell[row][column].getDice().getColor().equalsColor(dice.getColor());
        }catch (NullPointerException e){
            return false;
        }
    }
}
