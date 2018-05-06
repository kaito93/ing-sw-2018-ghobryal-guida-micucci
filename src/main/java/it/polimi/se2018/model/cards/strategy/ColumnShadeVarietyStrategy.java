package it.polimi.se2018.model.cards.strategy;

import it.polimi.se2018.model.Map;

public class ColumnShadeVarietyStrategy extends ObjectiveCardStrategy {
    @Override
    public int search(Map map, int score) {
        int counter=0;
        int colorCounter=0;
        for(int i=0; i<map.numColumn(); i++){
            for(int j=0; j<map.numRow()-1; j++){
                for(int k=j+1; k<map.numRow(); k++){
                    if(map.getCell(j,i).getDice()!=null&&map.getCell(k,i).getDice()!=null){
                        if(map.getCell(j,i).getDice().getValue()==map.getCell(k,i).getDice().getValue()){
                            colorCounter=0;
                            j = map.numRow();
                            break;
                        }else colorCounter++;
                    }else break;
                }
            }
            if(colorCounter==map.numColumn()) {
                counter++;
                colorCounter = 0;
            }
        }
        return counter*score;
    }
}
