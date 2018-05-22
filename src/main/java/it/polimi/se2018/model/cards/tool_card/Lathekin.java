package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.exception.notValidCellException;

/**
 * Lathekin Tool Card
 * @author Anton Ghobryal
 */

public class Lathekin extends ToolCard{

    /**
     * class constructor
     * @param title       the title of this card
     * @param description the description of the card rules
     * @param id1         card's number
     * @param color1      color associated to the card
     */
    public Lathekin(String title, String description, int id1, Color color1) {
        super(title, description, id1, color1);
    }

    /**
     * Read description of this card for further information
     * @param player who plays this turn
     * @param dice1 first dice needed to be repositioned
     * @param dice2 second dice needed to be repositioned
     * @param row1 row's coordinate on the map where the first dice needed to be repositioned
     * @param column1 column's coordinate on the map where the first dice needed to be repositioned
     * @param row2 row's coordinate on the map where the second dice needed to be repositioned
     * @param column2 column's coordinate on the map where the second dice needed to be repositioned
     * @return a boolean that is "true" if it is possible to put the dices in the player's map
     * @throws notValidCellException when the indexes of the row and the column not respect the interval number of matrix.
     */
    public boolean useTool(Player player, Dice dice1, Dice dice2, int row1, int column1, int row2, int column2) throws notValidCellException {
        int row3=0, column3=0, row4=0, column4=0;
        boolean a, b, c, d;
        if(mapContainsDice(player.getMap(), dice1, row3, column3)
            && mapContainsDice(player.getMap(), dice2, row4, column4)){
            a = player.posDice(dice1, row1, column1);
            b = player.posDice(null, row3, column3);
            c = player.posDice(dice2, row2, column2);
            d = player.posDice(null, row4, column4);
            if(!(a&&b)){
                System.out.println("first dice not valid");
            }
            if(!(c&&d)){
                System.out.println("second dice not valid");
            }
            return a && b && c && d;
        }
        return false;
    }
}
