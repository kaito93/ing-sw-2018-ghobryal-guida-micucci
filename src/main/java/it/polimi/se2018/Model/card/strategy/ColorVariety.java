package it.polimi.se2018.Model.card.strategy;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Map;

public class ColorVariety extends ObjectiveCardStrategy{
    @Override
    public int search(Map map, int score){
        int counterBlue=0;
        int counterGreen=0;
        int counterYellow=0;
        int counterRed=0;
        int counterPurble=0;
        for(int i=0; i<map.numRow(); i++){
            for(int j=0; j<map.numColumn(); j++){
                if(map.getCell(i,j).getDice()!=null){
                    if(map.getCell(i,j).getDice().getColor().equals(Color.BLUE))
                        counterBlue++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.GREEN))
                        counterGreen++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.YELLOW))
                        counterYellow++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.RED))
                        counterRed++;
                    else if(map.getCell(i,j).getDice().getColor().equals(Color.PURPLE))
                        counterPurble++;
                }
            }
        }
        counterBlue=Math.min(counterBlue, counterGreen);
        counterBlue=Math.min(counterBlue, counterYellow);
        counterBlue=Math.min(counterBlue, counterRed);
        counterBlue=Math.min(counterBlue, counterPurble);
        return counterBlue*score;
    }
}
