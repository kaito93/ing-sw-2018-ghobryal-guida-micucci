package it.polimi.se2018.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.ToolCard;

import javax.tools.Tool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * class that create the deck of all the tool cards from json file
 */
public class DeckToolCards {
    Gson gson = new Gson();
    File json;
    BufferedReader br;
    ArrayList<ToolCard> toolCards;

    /**
     * class constructor: generate a deck of toolcards from json file
     * @throws FileNotFoundException if the file is not found
     */
    public DeckToolCards() throws FileNotFoundException{
        json = new File("src/main/java/it/polimi/se2018/JsonFiles/ToolCards.json");
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            System.out.println("file carte utensili non trovato");
        }
        Type listTool = new TypeToken<ArrayList<ToolCard>>(){}.getType();
        toolCards = gson.fromJson(br, listTool);
    }

    /**
     * method that return the deck of toolcards
     * @return an arraylist contain all the toolcards
     */
    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }
}
