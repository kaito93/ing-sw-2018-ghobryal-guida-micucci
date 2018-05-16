/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.util;

import it.polimi.se2018.model.*;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author Andrea
 */
public class JsonWriteFiles {
    
    ArrayList<Dice> dices;

        
    public JsonWriteFiles(){
        dices = new ArrayList<Dice>();
        }
    
    public void RiempiArraylistScriviJson() throws FileNotFoundException{
        Gson gson = new Gson();
        Dice dice = new Dice();
        String stringSon1;
        String stringSon2;
        String stringSon3;
        String stringSon4;
        String stringSon5;
        PrintWriter writer = null;
        /*
        try{
            writer = new PrintWriter("src/main/java/it/polimi/se2018/JsonFiles/Dices.json");
        } catch (FileNotFoundException e){
            System.out.println("mancata stampa dei dadi");
        }
        dice.setColorDice(Color.BLUE);
        stringSon1 = gson.toJson(dice);
        dice.setColorDice(Color.GREEN);
        stringSon2 = gson.toJson(dice);
        dice.setColorDice(Color.PURPLE);
        stringSon3 = gson.toJson(dice);
        dice.setColorDice(Color.RED);
        stringSon4 = gson.toJson(dice);
        dice.setColorDice(Color.YELLOW);
        stringSon5 = gson.toJson(dice);
        for(int i = 0; i<18; i++)
        {
            writer.print(stringSon1);
            writer.print("\r\n");
            writer.print(stringSon2);
            writer.print("\r\n");
            writer.print(stringSon3);
            writer.print("\r\n");
            writer.print(stringSon4);
            writer.print("\r\n");
            writer.print(stringSon5);
            writer.print("\r\n");
        }
        writer.close();
        System.out.println("successo");*/
    }
    
 /*   public static void main(String[] args) throws FileNotFoundException {
      JsonWriteFiles x = new JsonWriteFiles();
      x.RiempiArraylistScriviJson();
    }*/
}
