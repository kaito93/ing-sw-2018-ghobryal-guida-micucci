package it.polimi.se2018.model;

import it.polimi.se2018.model.exception.notValidCellException;
import it.polimi.se2018.model.cards.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

/** class Player
 * contains all method to change the status of data of the player
 * @author Andrea Micucci, Anton Ghobryal
 */
public class Player implements Serializable {

    private String name;
    private Map map;
    private int favorSig;
    private PrivateObjectiveCard privateObj;
    private int score;
    private boolean firstTurn;
    private boolean secondTurn;
    
    /** class constructor
     * @param name_player string of the name of the player connected
     * @param glassWindow schema of the glasswindow
     * @param priv single private goal's card
     */
    public Player(String name_player, Map glassWindow, PrivateObjectiveCard priv){
        name = name_player;
        map = glassWindow;
        favorSig = glassWindow.getDifficultyLevel();
        privateObj = priv;
        score = 0;
        firstTurn = false;
        secondTurn = false;
    }

    public Player (String user){
        this.name=user;
    }

    /** method that return the card private with the private goal
     * @return privateObject card, that is the card with the private goal
     */
    public PrivateObjectiveCard getCardPrivateObj(){
        return privateObj;
    }
    
    /** method that return the score of the player
     * @return an integer that indicate the points of the player
     */
    public int getScore(){
        return score;
    }
    
    /** method that return the number of favor sig of a player
     * @return an integer indicate the number of favor sig, between 0 and the difficulty level of the map
     */
    public int getFavSig()
    {
        return favorSig;
    }
    
    /** method that set the value of the boolean firstAction as boolval
     * @param boolval a boolean that can be true-false
     */
    public void SetFirstTurn(boolean boolval){
        this.firstTurn = boolval;
    }
    /** method that set the value of the boolean secondAction as boolval
     * @param boolval a boolean that can be true-false
     */    
    public void SetSecondTurn(boolean boolval){
        this.secondTurn = boolval;
    }
    
    /** method that return the value of the boolean firstTurn
     * @return the value of the boolean firstTurn
     */
    public boolean GetFirstTurn(){
        return this.firstTurn;
    }

     /** method that return the value of the boolean firstTurn
     * @return the value of the boolean firstTurn
     */
    public boolean GetSecondTurn(){
        return this.secondTurn;
    }
    
    /** method that return the map of the player
     * @return a map object that is the martix of the player's glasswindow
     */
    public Map getMap(){
        return map;
    }
    
    /** method that return the name of the player
     * @return a string with the name of the player
     */
    public String getName(){
        return name;
    }
    
    /** method that set the dice in an position of the matrix of the glasswindow
     * @param dice: that player want to position on the matrix
     * @param row: row of the cell where the dice has to be positioned
     * @param column: column of the cell where the dice has to be positioned
     * @return a boolean that is "true" if the dice had been positioned
     */
    public boolean posDice(Dice dice, int row, int column) throws notValidCellException {
        if(map.validPosition(row, column, dice))
                {
                    map.getCell(row, column).setDice(dice);
                    return true;
                }
              else
                return false;
    }
    
    
    /** method that activate the tool card taken by the player
     * @param toolCard the card that the player has choice
     * @return a boolean that is true if the card has been activated, else "false"
     */
  /*  public boolean useTool(ToolCard toolCard){
       return toolCard.handle(this);
    }*/

    /**
     * choose a dice from the passed stock
     * @param stock round stock
     * @param value dice's value
     * @param color dice's color
     * @return the chosen dice
     */
    public Optional<Dice> chooseDice(ArrayList<Dice> stock, int value, Color color){
        return stock.stream().filter((Dice d) -> (d.getValue()==value&&d.getColor().equals(color))).findFirst();
    }

    /**
     * return the oobject map
     * @param map that is used by the player
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * set a new private objective card for the player
     * @param newCard the new card that has to be setted
     *
     */
    public void setPrivateObjectiveCard(PrivateObjectiveCard newCard){
        this.privateObj = newCard;
    }
}
