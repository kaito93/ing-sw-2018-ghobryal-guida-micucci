package it.polimi.se2018.model;

/** class Player
 * contains all method to change the status of data of the player
 * @author Andrea Micucci, Anton Ghobryal
 */
import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cell.Cell;

public class Player {

    String name;
    Map map;
    int favorSig;
    PrivateObjectiveCard privateObj;
    int score;
    
    /** class constructor
     * @param id_Player_interface: integer of the player who is connected
     * @param name_player: string of the name of the player connected
     * @param glassWindow: schema of the glasswindow
     * @param priv: single private goal's card
     */
    public Player(Long id_Player_interface, String name_player, Map glassWindow, PrivateObjectiveCard priv){
        name = name_player;
        map = glassWindow;
        favorSig = 0;
        privateObj = priv;
        score = 0;
    }
    
    /** method that return the card private with the private goal
     * @return privateObject card, that is the card with the private goal
     */
    public Card getCardPrivateObj(){
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
    
    /** method that return the map of the player
     * @return: a map object that is the martix of the player's glasswindow 
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
     * @throws notValidCellException: when the indexes of the cell are not valid and not indicate a cell in the matrix
     * @return: a boolean that is "true" if the dice had been positioned
     */
    public boolean posDice(Dice dice, int row, int column) throws notValidCellException{
        if(this.map.validPosition(row, column, dice) == true)
                {
                    map.getCell(row, column).setDice(dice);
                    return true;
                }
              else
                return false;
    }
    
    /** method that initialize the number of favour using the difficulty level of the matrix
     * @param glassWindow: the matrix represent the map of the player
     */
    public void initFavor(Map glassWindow){
        favorSig = glassWindow.getDifficultyLevel();
    }
    
    /** method that activate the tool card taken by the player
     * @param toolCard: the card that the player has choice
     * @return a boolean that is true if the card has been activated, else "false"
     */
  /*  public boolean useTool(ToolCard toolCard){
       return toolCard.handle(this);
    }*/

}
