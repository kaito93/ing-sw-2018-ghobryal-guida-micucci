package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.RoundSchemeCell;

import java.util.ArrayList;

/**
 * Lens Cutter Tool Card
 * @author Anton Ghobryal
 */

public class LensCutter extends ToolCard{

    /**
     * class constructor
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public LensCutter(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param stockDice a chosen dice from the stock
     * @param stock round's stock
     * @param roundSchemeDice a chosen dice from the Round Scheme
     * @param roundSchemeMap the Round Scheme
     * @return a boolean that verifies if the chosen dice from the Round Scheme
     */

    //non ho posizionato il dado
    public Dice useTool(Dice stockDice, ArrayList<Dice> stock, Dice roundSchemeDice, RoundSchemeCell[] roundSchemeMap){
        ArrayList<Dice> temp = new ArrayList<>();
        int pos = 0;
        if(roundSchemeContainsDice(roundSchemeDice, roundSchemeMap, pos) && stockDice!=null && roundSchemeDice!=null
                && stock.contains(stockDice)){
            temp.add(stockDice);
            stock.remove(stockDice);
            roundSchemeMap[pos].removeDice(roundSchemeDice);
            roundSchemeMap[pos].setDices(temp);
            return roundSchemeDice;
        }
        return null;
    }
}
