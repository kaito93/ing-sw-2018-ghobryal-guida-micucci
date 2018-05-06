package it.polimi.se2018.Model.card.strategy;

import it.polimi.se2018.Model.Map;

public class DeepShadesStrategy extends ObjectiveCardStrategy {
    @Override
    public int search(Map map, int score){
        int counter5=0;
        int counter6=0;
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn(); j++){
                if(map.getCell(i,j).getDice()!=null){
                    if(map.getCell(i,j).getDice().getValue()==5)
                        counter5++;
                    else if(map.getCell(i,j).getDice().getValue()==6)
                        counter6++;
                }
            }
        }
        counter5=Math.min(counter5, counter6);
        return counter5*score;
    }
}
