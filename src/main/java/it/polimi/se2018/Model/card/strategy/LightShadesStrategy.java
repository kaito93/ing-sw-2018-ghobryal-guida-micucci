package it.polimi.se2018.Model.card.strategy;

import it.polimi.se2018.Model.Map;

public class LightShadesStrategy extends ObjectiveCardStrategy{
    @Override
    public int search(Map map, int score){
        int counter1=0;
        int counter2=0;
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn(); j++){
                if(map.getCell(i,j).getDice()!=null){
                    if(map.getCell(i,j).getDice().getValue()==1)
                        counter1++;
                    else if(map.getCell(i,j).getDice().getValue()==2)
                        counter2++;
                }
            }
        }
        counter1=Math.min(counter1, counter2);
        return counter1*score;
    }
}
