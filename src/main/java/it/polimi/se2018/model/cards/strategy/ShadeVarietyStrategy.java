package it.polimi.se2018.model.cards.strategy;

import it.polimi.se2018.model.Map;

public class ShadeVarietyStrategy extends ObjectiveCardStrategy{
    @Override
    public int search(Map map, int score){
        int counter1=0;
        int counter2=0;
        int counter3=0;
        int counter4=0;
        int counter5=0;
        int counter6=0;
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn(); j++){
                if(map.getCell(i,j).getDice()!=null){
                    if(map.getCell(i,j).getDice().getValue()==1)
                        counter1++;
                    else if(map.getCell(i,j).getDice().getValue()==2)
                        counter2++;
                    else if(map.getCell(i,j).getDice().getValue()==3)
                        counter3++;
                    else if(map.getCell(i,j).getDice().getValue()==4)
                        counter4++;
                    else if(map.getCell(i,j).getDice().getValue()==5)
                        counter5++;
                    else if(map.getCell(i,j).getDice().getValue()==6)
                        counter6++;
                }
            }
        }
        counter1=Math.min(counter1, counter2);
        counter1=Math.min(counter1, counter3);
        counter1=Math.min(counter1, counter4);
        counter1=Math.min(counter1, counter5);
        counter1=Math.min(counter1, counter6);
        return counter1*score;
    }
}
