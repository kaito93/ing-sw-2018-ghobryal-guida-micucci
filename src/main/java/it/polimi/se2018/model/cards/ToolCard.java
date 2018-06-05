package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

import java.util.List;

public class ToolCard extends Card{
    private int id;
    private Color color;
    private boolean used;
    private ToolCardStrategy strategy;

    /**
     * class constructor
     * @param title the title of this card
     * @param description the description of the card rules
     * @param id1 card's number
     * @param color1 color associated to the card
     */

    public ToolCard(String title, String description, int id1, Color color1, ToolCardStrategy strategy1){
        super(title, description);
        id = id1;
        color = color1;
        used = false;
        strategy = strategy1;
    }

    /**
     * @return if the card was used till now or not
     */

    public boolean isUsed(){
        return used;
    }

    /**
     * @return card's id
     */

    public int getId() {
        return id;
    }

    /**
     * @return card's associated color
     */

    public Color getColor() {
        return color;
    }

    /**
     * sets the boolean if the card is being used
     * @param used1 pass true if it's used
     */

    public void setUsed(boolean used1) {
        used = used1;
    }

    /**
     * implements the card algorithm in a specific way
     * for parameters' description go to card's strategy
     * @param errorMessage an error message if the player doesn't have enough favour signals else null
     * @return a boolean which is true if the player respects the rules of using this card else false
     */
    public boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock
            , boolean posDice, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
            , List<Player> turns, int posDice1, String errorMessage){
        if(!isUsed() && player.getFavSig()>0){
            strategy.useTool(player, dice, row1, column1, stock,
                    posDice, row2, column2, roundSchemeDice, roundSchemeMap, turns, posDice1);
            if(!ToolCardStrategy.getErrorBoolTool().getErrBool()) {
                used = true;
                errorMessage = null;
                return player.modifyFavorSig(1);
            }else{
                errorMessage = "Player doesn't have enough favor signals";
                return false;
            }
        }else if(isUsed() && player.getFavSig()>1){
            strategy.useTool(player, dice, row1, column1, stock,
                    posDice, row2, column2, roundSchemeDice, roundSchemeMap, turns, posDice1);
            if(!ToolCardStrategy.getErrorBoolTool().getErrBool()){
                errorMessage = null;
                return player.modifyFavorSig(2);
            }
            else{
                errorMessage = "Player doesn't have enough favor signals";
                return false;
            }
        }
        return false;
    }

    /**
     * gets the used strategy
     * @return the used strategy
     */
    public ToolCardStrategy getStrategy() {
        return strategy;
    }
}
