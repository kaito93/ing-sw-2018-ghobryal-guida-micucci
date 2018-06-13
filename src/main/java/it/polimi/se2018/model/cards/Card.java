package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

import java.io.Serializable;
import java.util.List;

/**
 * Class Card
 * describes a generic card
 * @author Anton Ghobryal
 */

public abstract class Card implements Serializable {
    private String title;
    private String description;

    /**
     * Class Constructor
     * @param titleT the title of this card
     * @param descriptionD the description of the card rules
     */

    public Card(String titleT, String descriptionD){
        title=titleT;
        description=descriptionD;
    }

    /**
     * @return the title of the card
     */

    public String getTitle() {
        return title;
    }

    /**
     * @return the description of the card
     */

    public String getDescription() {
        return description;
    }

    /**
     * Overridden in PrivateObjectiveCard & PublicObjectiveCard
     */
    public abstract int search(Map map);

    /**
     * Overridden in PublicObjectiveCard
     */
    public abstract int getScore();

    /**
     * Overridden in ToolCard
     */
    public abstract ToolCardStrategy getStrategy();

    /**
     * Overridden in ToolCard
     */

    public abstract boolean isUsed();

    /**
     * Overridden in ToolCard
     */

    public abstract int getId();

    /**
     * Overridden in ToolCard
     */

    public abstract Color getColor();

    /**
     * Overridden in ToolCard
     * @param used1 pass true if it's used
     */

    public abstract void setUsed(boolean used1);

    /**
     * Overridden in ToolCard
     */
    public abstract boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock
            , boolean posDice, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
            , List<Player> turns, int posDice1);
}
