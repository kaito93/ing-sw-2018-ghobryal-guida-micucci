package it.polimi.se2018.Model.card.strategy;

import it.polimi.se2018.Model.Map;

public class MediumShadesStrategy extends ObjectiveCardStrategy {
    @Override
    public int search(Map map, int score){
        int counter3=0;
        int counter4=0;
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn(); j++){
                if(map.getCell(i,j).getDice()!=null){
                    if(map.getCell(i,j).getDice().getValue()==3)
                        counter3++;
                    else if(map.getCell(i,j).getDice().getValue()==4)
                        counter4++;
                }
            }
        }
        counter3=Math.min(counter3, counter4);
        return counter3*score;
    }
}
