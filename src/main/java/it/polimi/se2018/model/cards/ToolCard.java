package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.*;
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
    @Override
    public boolean isUsed(){
        return used;
    }

    /**
     * @return card's id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return card's associated color
     */
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setUsed(boolean used1) {

    }

    /**
     * implements the card algorithm in a specific way
     * for parameters' description go to card's strategy
     * @return a boolean which is true if the player respects the rules of using this card else false
     */
    @Override
    public boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock
            , boolean posDice, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
            , List<Player> turns, int posDice1){
        if((!isUsed() && player.getFavSig()<1) || (isUsed() && player.getFavSig()<2)){
            ToolCardStrategy.getErrorBool().setErrorMessage("Player doesn't have enough favor signals");
            ToolCardStrategy.getErrorBool().setErrBool(true);
            return false;
        }else if(!isUsed() && player.getFavSig()>0){
            strategy.useTool(player, dice, row1, column1, stock,
                    posDice, row2, column2, roundSchemeDice, roundSchemeMap, turns, posDice1);
            if(!ToolCardStrategy.getErrorBool().getErrBool()) {
                used = true;
                return player.modifyFavorSig(1);
            }
        }else if(isUsed() && player.getFavSig()>1){
            strategy.useTool(player, dice, row1, column1, stock,
                    posDice, row2, column2, roundSchemeDice, roundSchemeMap, turns, posDice1);
            if(!ToolCardStrategy.getErrorBool().getErrBool()){
                return player.modifyFavorSig(2);
            }
        }
        return false;
    }

    @Override
    public int search(Map map) {
        return 0;
    }

    @Override
    public int getScore() {
        return 0;
    }

    /**
     * gets the used strategy
     * @return the used strategy
     */
    @Override
    public ToolCardStrategy getStrategy() {
        return strategy;
    }

}
