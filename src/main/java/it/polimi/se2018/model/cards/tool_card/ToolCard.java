package it.polimi.se2018.model.cards.tool_card;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.cards.Card;

/**
 * a generic private tool card
 * @author Anton Ghobryal
 */

public class ToolCard extends Card {
    private int id;
    private Color color;
    private boolean used;
    private ToolCardStrategy strategy;

    public ToolCard(String title, String description,int id1, Color color1, boolean used1, ToolCardStrategy strategy1){
        super(title, description);
        id = id1;
        color = color1;
        used = used1;
        strategy = strategy1;
    }

    public boolean isUsed(){
        return used;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void handlePlayer(Player player){

    }
}
