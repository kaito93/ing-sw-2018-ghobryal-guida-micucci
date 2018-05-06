package it.polimi.se2018.model.cards;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.Color;

public class PrivateObjectiveCard extends Card {
    private Color color;

    public PrivateObjectiveCard(String title, String description, Color color1){
        super(title, description);
        color = color1;
    }

    public int search(Map map){
        int score=0;
        for(int i=0; i<map.numRow(); i++){
            for (int j=0; j<map.numColumn(); j++){
                if(map.getCell(i, j).getDice()!=null)
                    if(map.getCell(i, j).getDice().getColor().equals(color))
                        score += map.getCell(i, j).getDice().getValue();
            }
        }
        return score;
    }
}
