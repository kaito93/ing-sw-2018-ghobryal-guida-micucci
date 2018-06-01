package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.PrivateObjectiveCard;

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
    private boolean setDice;
    private boolean useTools;


    /**
     * Class Constructor
     * @param user username
     */
    public Player (String user){
        this.name=user;
    }

    /** method that return the card private with the private goal
     * @return privateObject card, that is the card with the private goal
     */
    //tested
    public PrivateObjectiveCard getCardPrivateObj(){
        return privateObj;
    }
    
    /** method that return the score of the player
     * @return an integer that indicate the points of the player
     */
    //tested
    public int getScore(){
        return score;
    }

    /**
     * sets the player's final score
     * @param score the calculated final score
     */
    //tested
    public void setScore(int score) {
        this.score = score;
    }

    /** method that return the number of favor sig of a player
     * @return an integer indicate the number of favor sig, between 0 and the difficulty level of the map
     */
    //tested
    public int getFavSig()
    {
        return favorSig;
    }
    
    /** method that set the value of the boolean firstAction as boolval
     * @param boolval a boolean that can be true-false
     */
    //tested
    public void setUseTools(boolean boolval){
        this.useTools = boolval;
    }
    /** method that set the value of the boolean secondAction as boolval
     * @param boolval a boolean that can be true-false
     */
    //tested
    public void setSetDice(boolean boolval){
        this.setDice = boolval;
    }
    
    /** method that return the value of the boolean firstTurn
     * @return the value of the boolean firstTurn
     */
    //tested
    public boolean getSetDice(){
        return this.setDice;
    }

     /** method that return the value of the boolean firstTurn
     * @return the value of the boolean firstTurn
     */
     //tested
    public boolean getUseTools(){
        return this.useTools;
    }
    
    /** method that return the map of the player
     * @return a map object that is the martix of the player's glasswindow
     */
    //tested
    public Map getMap(){
        return map;
    }
    
    /** method that return the name of the player
     * @return a string with the name of the player
     */
    //tested
    public String getName(){
        return name;
    }

    /**
     * choose a dice from the passed stock
     * @param stock round stock
     * @param value dice's value
     * @param color dice's color
     * @return the chosen dice
     */
    @Deprecated
    public Optional<Dice> chooseDice(ArrayList<Dice> stock, int value, Color color){
        return stock.stream().filter((Dice d) -> (d.getValue()==value&&d.getColor().equals(color))).findFirst();
    }

    /**
     * return the oobject map
     * @param map that is used by the player
     */
    //tested
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * assigns the favor signs to the player
     */
    public void setFavorSig(){
        favorSig = map.getDifficultyLevel();
    }

    /**
     * modifies favor signals on request
     * @param num the number of signals is taken from the player
     * @return a boolean if the player has enough favor signals to be taken or not
     */
    public boolean modifyFavorSig(int num){
        if(favorSig>0){
            favorSig -= num;
            return favorSig>=0;
        }
        return false;
    }

    /**
     * set a new private objective card for the player
     * @param newCard the new card that has to be setted
     */
    public void setPrivateObjectiveCard(PrivateObjectiveCard newCard){
        this.privateObj = newCard;
    }
}
