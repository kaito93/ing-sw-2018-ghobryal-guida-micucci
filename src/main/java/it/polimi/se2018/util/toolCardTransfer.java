package it.polimi.se2018.util;

import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.cards.tool_card_strategy.ToolCardStrategy;

public class toolCardTransfer extends jsonTransiction {

    private String strategic;
    private Color color;
    private int id;

    public String getStrategy() {
        return strategic;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
