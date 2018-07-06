package it.polimi.se2018.server.controller.tool_card_strategy;

import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.server.model.Player;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.server.network.VirtualView;

import java.util.List;

/**
 * Grozing Pliers Tool Card
 * @author Anton Ghobryal
 */

public class GrozingPliers extends ToolCardStrategy {

    /**
     * Read description of this card for further information
     *
     * @param player player on turn
     * @param dice a chosen dice from the stock
     * @param row  row's coordinate where to position the dice
     * @param column column's coordinate where to position the dice
     * @param t     n.a.
     * @param t1    n.a.
     * @param t2    n.a.
     * @param t3    n.a.
     * @param t4    n.a.
     * @param t5    n.a.
     * @param t6    n.a.
     */

    //qui non posso posizonarti il dado perché ci sono 2 valori diversi, quindi mandi un nuovo messaggio di tipo
    // posizionamento dado con le coordinate e il dado da mettere e hai i metodi qui che i servono
    public void useTool(Player player, Dice dice, int row, int column, List<Dice> t, int t1, int t2, Dice t3,
                        RoundSchemeCell[] t4, List<Player> t5, int t6){
        // L'algoritmo di questa carta è stato spostato nella View (nel metodo ManageGrozing()) per evitare un continuo
        // invio di messaggi contenenti una singola e elementare informazione. In questo modo l'utente invia al server
        // solamente le informazioni del dado da posizionare e delle relative coordinate come in un normalissimo
        // posizionamento di dadi.
        // Il metodo UseTool rimane comunque perchè viene richiamato tramite RMI
        //il resto della logica della carta è stato spostato nel controller nel manager della carta
        posDiceControl(player, dice, row, column); //posizionamento normale del dado scelto
        ToolCardStrategy.errorBool.setErrBool(false);
    }

    @Override
    public void requestMessage(VirtualView view, String title, int player) {
        view.createMessageGrozing(title,player);
    }
}
