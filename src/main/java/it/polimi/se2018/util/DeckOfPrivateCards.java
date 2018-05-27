package it.polimi.se2018.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PrivateObjectiveCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * class to create the deck of all the private cards saved in json file
 */
public class DeckOfPrivateCards {

    Gson gson = new Gson();
    File json;
    BufferedReader br;
    ArrayList<PrivateObjectiveCard> privCards;

    /**
     * class constructor: create a deck of all the private cards
     * @throws FileNotFoundException if the json file with all the info about the private cards is not found
     */
    public DeckOfPrivateCards() throws FileNotFoundException{
        json = new File("src/main/java/it/polimi/se2018/JsonFiles/PrivateObjectiveCards.json");
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            System.out.println("file per le carte pubbliche non trovato");
        }
        Type listPrivCards = new TypeToken<ArrayList<PrivateObjectiveCard>>(){}.getType();
        privCards = gson.fromJson(br, listPrivCards);
        System.out.println(privCards.get(1).getTitle());
        System.out.println(privCards.get(3).getTitle());
    }

    /**
     * method that erase a card with a choosen index in the deck of private cards
     * @param index of the card that has to be eliminated
     */
    public void eraseChoosenCard(int index){
        int i = 0;
        while (i<privCards.size()){
            if (i==index)
                privCards.remove(i);
            i++;
        }
    }

    /**
     * method that return all the private cards in the arraylist
     * @return an arraylist of private objective cards
     */
    public ArrayList<PrivateObjectiveCard> getPrivCards() {
        return privCards;
    }
}