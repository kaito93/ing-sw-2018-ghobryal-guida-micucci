package it.polimi.se2018.server.model.cards;


import it.polimi.se2018.server.controller.tool_card_strategy.ToolCardStrategy;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

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
     * @param map where to search
     * @return an integer with the points
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
     */
    public abstract boolean useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock
            , int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
            , List<Player> turns, int posDice1);
}
