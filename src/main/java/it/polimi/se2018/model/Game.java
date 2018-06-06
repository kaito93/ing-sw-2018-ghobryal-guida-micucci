package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.util.DeckOfPrivateCards;
import it.polimi.se2018.util.DiceBox;
import it.polimi.se2018.util.Deserializer.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;


/**
 * class Game
 * contains all the resources for this game
 * @author Samuele Guida
 */
public class Game {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    private static final int MAXROUND = 10;

    private static DiceBox diceBag;

    private static ArrayList<Dice> stock = new ArrayList<>();

    private static ArrayList<PublicObjectiveCard> publicObjCard =new ArrayList <>();

    private RoundSchemeCell roundSchemeMap[];

    private static ArrayList<ToolCard> toolCards = new ArrayList<>();

    private ArrayList<Map> maps;


    /**
     * class constructor that initialize all the attributes
     */
    public Game(){


        diceBag=new DiceBox(); // carica i dadi dal file json


        ArrayList<PublicObjectiveCard> cards = loadPublicObjCard(); // carica tutte le carte obiettivo pubblico

        for (int i=0; i<3;i++) // estrae 3 numeri casuali e inserisce le rispettive carte pubbliche nell'arraylist per la partita

        {

            Random random = new Random();

            int j = random.nextInt(cards.size());

            publicObjCard.add(cards.get(j));

            cards.remove(j);

        }

        this.roundSchemeMap= new RoundSchemeCell[MAXROUND]; // Crea il tracciato dei round

        ArrayList<ToolCard> tools = loadToolCards(); // carica le carte utensili

        for (int k=0; k<3; k++) { // per 3 volte

            Random random = new Random();

             int j = random.nextInt(tools.size()); // scegli un numero a caso

            toolCards.add(tools.get(j)); // inserisci la carta nell'array.

            tools.remove(j);

        }

        maps = loadMaps();


    }

    /**
     * method that returns the list of tool card used in this game
     * @return list of tool card
     */

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     * method that loads the tool cards from a Json file
     * @return a list with the cards red
     */
    private ArrayList<ToolCard> loadToolCards(){


        toolCardDeserializer tool= new toolCardDeserializer("src/main/java/it/polimi/se2018/JsonFiles/ToolCards.json");
        tool.TotalDeserializing();
        return tool.getDeck();
    }

    /**
     * method that loads the public objective cards from a Json file
     * @return a list with the cards red
     */
    private ArrayList<PublicObjectiveCard> loadPublicObjCard(){


        PublicCardDeserializer cards =  new PublicCardDeserializer("src/main/java/it/polimi/se2018/JsonFiles/PublicCards.json");
        cards.TotalDeserializing();
        return cards.getPublicObjectivetransfer();
    }

    /**
     * method that loads the private objective cards from a Json file
     * @return a list with the cards red
     */

    private ArrayList<PrivateObjectiveCard> loadPrivateObjectiveCard(){


        DeckOfPrivateCards cards = new DeckOfPrivateCards();
        return cards.getPrivCards();

    }

    /**
     * method that search a tool card from his title
     * @param title title of the card choosed by player
     * @return che card that player have chosen
     */
    public ToolCard searchToolCard(String title) {
        boolean find=false;
        int i=0;
        while (!find && i < toolCards.size()) {
            if (toolCards.get(i).getTitle().equalsIgnoreCase(title))
                find = true;
            else
                i++;
        }
        if (find)
            return toolCards.get(i);
        else
            return null;
    }

    /**
     * method that returns the number of rounds in a game
     * @return an integer between 1 and a generic value (basic: 10)
     */
    public int getMaxRound() {
        return MAXROUND;
    }

    /**
     * method that returns the dice bag of this game
     * @return the collection of dice
     */
    public DiceBox getDiceBag() {
        return diceBag;
    }

    /**
     * method that returns the list of public objective cards
     * @return a list of public cards
     */
    public ArrayList<PublicObjectiveCard> getPublicObjCard() {
        return publicObjCard;
    }

    /**
     * method that returns the round scheme
     * @return the list of a dice in the round scheme
     */
    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    /**
     * method that extracts one private objective for every player in game and set it
     * @param players the list of players in game
     */
    public void setPrivateObjectiveCard(ArrayList<Player> players){

        ArrayList<PrivateObjectiveCard> cards = loadPrivateObjectiveCard();
        for (Player player : players) {

            Random random = new Random();

            int j = random.nextInt(cards.size());

            player.setPrivateObjectiveCard(cards.get(j));

            cards.remove(j);

        }

    }

    /**
     * method that returns the stock of dice
     * @return the stock
     */
    public ArrayList<Dice> getStock() {
        return stock;
    }

    /**
     * method that setup the stock in every round
     * @param stocks the stock that have been created
     */
    public void setStock(ArrayList<Dice> stocks) {
        stock = stocks;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public void setMaps() {
        ArrayList<Map> maps= loadMaps();
    }

    public ArrayList<Map> loadMaps() {

        MapsDeserializer maps = new MapsDeserializer();
        return maps.totalDeserialize();
    }
}