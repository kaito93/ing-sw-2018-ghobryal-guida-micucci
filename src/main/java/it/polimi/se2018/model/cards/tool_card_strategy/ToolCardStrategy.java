package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.*;
import it.polimi.se2018.network.server.VirtualView.VirtualView;
import it.polimi.se2018.util.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * a generic private tool card
 * @author Anton Ghobryal
 */

public abstract class ToolCardStrategy implements Serializable {

    protected static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    protected static ErrorBool errorBool = new ErrorBool(null, false);

    /**
     * gets an ErrorBoolTool status
     * @return a data structure within an error message and an error boolean
     */
    public static ErrorBool getErrorBool() {
        return errorBool;
    }

    public abstract void useTool(Player player, Dice dice, int row1, int column1, List<Dice> stock
        , boolean posDice, int row2, int column2, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap
        , List<Player> turns, int posDice1);

    public abstract void requestMessage(VirtualView view, String title, int player);

}
