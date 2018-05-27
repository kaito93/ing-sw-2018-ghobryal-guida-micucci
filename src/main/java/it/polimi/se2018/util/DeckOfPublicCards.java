package it.polimi.se2018.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PublicObjectiveCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * class that create a deck of all the public objective cards from a file json
 */
public class DeckOfPublicCards {
    Gson gson = new Gson();
    File json;
    BufferedReader br;
    ArrayList<PublicObjectiveCard> publCards;

    /**
     * class constructor, create an arraylist of public cards
     * @throws FileNotFoundException if the json file doesn't exist
     */
    public DeckOfPublicCards() throws FileNotFoundException {
        json = new File("src/main/java/it/polimi/se2018/JsonFiles/PublicObjectiveCards.json");
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            System.out.println("file di lettura delle carte pubbliche non trovato");
        }

        Type listPublCards = new TypeToken<ArrayList<PublicObjectiveCard>>() {
        }.getType();
        publCards = gson.fromJson(br, listPublCards);       //non ancora completamente funzionante, c'è un campo di troppo nel file json
    }

    /**
     * getter method that return the arraylist of cards
     * @return the arraylist of public objective cards
     */
    public ArrayList<PublicObjectiveCard> getPublicObjectiveCard(){
        return this.publCards;
    }

    /**
     * method that erase all unused cards
     * @param index1 first index of choosen card
     * @param index2 second index of choosen card
     * @param index3 third index of choosen card
     */
    public void eraseCards(int index1, int index2, int index3){
        int i=0;
        while (i<this.getPublicObjectiveCard().size()){
            if ((i!=index1) || (i!=index2) || (i !=index3))
                this.publCards.remove(i);
            i++;
        }
    }
}
