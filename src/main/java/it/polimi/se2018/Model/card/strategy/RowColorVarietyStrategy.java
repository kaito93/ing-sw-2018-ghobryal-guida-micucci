package it.polimi.se2018.Model.card.strategy;

import it.polimi.se2018.Model.Map;


public class RowColorVarietyStrategy extends ObjectiveCardStrategy{

    @Override
    public int search(Map map, int score) {
        int counter=0;
        int colorCounter=0;
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn()-1; j++){
                for(int k=j+1; k<map.numColumn(); k++){
                    if(map.getCell(i,j).getDice()!=null&&map.getCell(i,k).getDice()!=null){
                        if(map.getCell(i,j).getDice().getColor().toString().equals(map.getCell(i,k).getDice().getColor().toString())){
                          colorCounter=0;
                          j = map.numColumn();
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
