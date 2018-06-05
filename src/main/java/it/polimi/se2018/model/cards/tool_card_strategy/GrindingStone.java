package it.polimi.se2018.model.cards.tool_card_strategy;

import it.polimi.se2018.model.Dice;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.RoundSchemeCell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.network.server.VirtualView.VirtualView;

import java.util.List;
import java.util.logging.Level;

/**
 * Grinding Stone Tool Card
 * @author Anton Ghobryal
 */

public class GrindingStone extends ToolCardStrategy {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());



    /**
     * Read description of this card for further information
     * @param player n.a.
     * @param dice the chosen dice
     * @param turn n.a.
     * @param a n.a.
     * @param stock n.a.
     * @param posDice n.a.
     * @param t1 n.a.
     * @param t2 n.a.
     * @param t3 n.a.
     * @param t4 n.a.
     * @param t5 n.a.
     * @param t6 n.a.
     */
    public void useTool(Player player, Dice dice, int turn, int a, List<Dice> stock
            , boolean posDice, int t1, int t2, Dice t3, RoundSchemeCell[] t4, List<Player> t5, int t6){
        if(dice==null){
            errorBoolTool.setErrorMessage("There's no passed dice");
            errorBoolTool.setErrBool(true);
            return;
        }
        else
            try {
                switch (dice.getValue()) {
                    case 1:
                        dice.setValue(6);
                        break;
                    case 2:
                        dice.setValue(5);
                        break;
                    case 3:
                        dice.setValue(4);
                        break;
                    case 4:
                        dice.setValue(3);
                        break;
                    case 5:
                        dice.setValue(2);
                        break;
                    case 6:
                        dice.setValue(1);
                        break;
                    default:
                        errorBoolTool.setErrorMessage("Invalid passed dice value");
                        errorBoolTool.setErrBool(true);
                        return;
                }
            }
            catch (InvalidValueException e){
                LOGGER.log(Level.SEVERE, e.toString()+"\nuseTool method in class GrindingStone Tool Card", e);
            }
        errorBoolTool.setErrorMessage(null);
        errorBoolTool.setErrBool(false);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGrinding(title,player);
    }
}
