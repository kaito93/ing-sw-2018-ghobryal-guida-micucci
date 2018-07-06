package it.polimi.se2018.server.model;

import it.polimi.se2018.server.deserializer.maps.MapsDeserializer;
import it.polimi.se2018.server.deserializer.public_cards.PublicCardDeserializer;
import it.polimi.se2018.server.deserializer.tool_cards.ToolCardDeserializer;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import it.polimi.se2018.shared.path.PathDeserializer;
import it.polimi.se2018.server.deserializer.DeckOfPrivateCards;
import it.polimi.se2018.server.deserializer.DiceBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * class Game
 * contains all the resources for this game
 * @author Samuele Guida
 */
public class Game implements Serializable {

    private static final int MAXROUND = 10;

    private DiceBox diceBag;

    private List<Dice> stock;

    private ArrayList<PublicObjectiveCard> publicObjCard;

    private RoundSchemeCell[] roundSchemeMap;

    private ArrayList<ToolCard> toolCards;

    private List<Map> maps;

    private PathDeserializer path;


    /**
     * class constructor that initialize all the attributes
     */
    public Game(){

        path = new PathDeserializer("src/main/java/it/polimi/se2018/server/json_server/Pathname.json");

        path.deserializing();

        diceBag=new DiceBox(path.getPathFromType("dice")); // carica i dadi dal file json
        diceBag.deserializing();

        stock = new ArrayList<>();

        publicObjCard =new ArrayList <>();

        List<PublicObjectiveCard> cards = loadPublicObjCard(); // carica tutte le carte obiettivo pubblico

        for (int i=0; i<3;i++) // estrae 3 numeri casuali e inserisce le rispettive carte pubbliche nell'arraylist per la partita

        {

            Random random = new Random();

            int j = random.nextInt(cards.size());

            publicObjCard.add(cards.remove(j));

        }

        this.roundSchemeMap= new RoundSchemeCell[MAXROUND]; // Crea il tracciato dei round

        Dice dice=Dice.diceNull();
        List<Dice> list = new ArrayList<>();
        list.add(dice);
        for (int i=0;i<MAXROUND;i++){
            roundSchemeMap[i] = new RoundSchemeCell();
            roundSchemeMap[i].setDices(list);

        }

        List<ToolCard> tools = loadToolCards(); // carica le carte utensili

        toolCards = new ArrayList<>();

        for (int k=0; k<3; k++) { // per 3 volte

            Random random = new Random();

             int j = random.nextInt(tools.size()); // scegli un numero a caso

            toolCards.add(tools.remove(j)); // inserisci la carta nell'array.

        }

        setMaps();


    }

    /**
     * method that returns the list of tool card used in this game
     * @return list of tool card
     */

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     * method that loads the tool cards from a Json file
     * @return a list with the cards red
     */
    private List<ToolCard> loadToolCards(){


        ToolCardDeserializer tool= new ToolCardDeserializer(path.getPathFromType("tool"));
        tool.totalDeserializing();
        return tool.getDeck();
    }

    /**
     * method that loads the public objective cards from a Json file
     * @return a list with the cards red
     */
    private List<PublicObjectiveCard> loadPublicObjCard(){


        PublicCardDeserializer cards =  new PublicCardDeserializer(path.getPathFromType("public"));
        cards.totalDeserializing();
        return cards.getPublicObjectiveTransfer();
    }

    /**
     * method that loads the private objective cards from a Json file
     * @return a list with the cards red
     */

    private List<PrivateObjectiveCard> loadPrivateObjectiveCard(){


        DeckOfPrivateCards cards = new DeckOfPrivateCards(path.getPathFromType("private"));
        cards.deserializing();
        return cards.getObjectiveCards();

    }

    /**
     * method that search a tool card from its title
     * @param title title of the card chose by player
     * @return which card the player have chosen
     */
    public ToolCard searchToolCard(String title) {
        boolean found=false;
        int i=0;
        while (!found && i < toolCards.size()) {
            if (toolCards.get(i).getTitle().equalsIgnoreCase(title))
                found = true;
            else
                i++;
        }
        if (found)
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
    public List<PublicObjectiveCard> getPublicObjCard() {
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
    public void setPrivateObjectiveCard(List<Player> players){

        List<PrivateObjectiveCard> cards = loadPrivateObjectiveCard();
        for (Player player : players) {

            Random random = new Random();

            int j = random.nextInt(cards.size());

            player.setPrivateObjectiveCard(cards.get(j));

            cards.remove(j);

        }

    }

    /**
     * method that returns the stock of dices
     * @return the stock
     */
    public List<Dice> getStock() {
        return stock;
    }

    /**
     * method that setup the stock in every round
     * @param stocks the stock that have been created
     */
    public void setStock(List<Dice> stocks) {
        stock = stocks;
    }

    /**
     * method that returns all the maps
     * @return all the maps
     */
    public List<Map> getMaps() {
        return maps;
    }

    /**
     * method that setup an array list of all maps from the file Json
     */
    private void setMaps() {
        maps = loadMaps();
    }

    /**
     * method that loads all the maps from the file Json directly
     * @return an array list of all the maps
     */
    private List<Map> loadMaps() {

        MapsDeserializer mapscegia = new MapsDeserializer(path.getPathFromType("maps"));
        return mapscegia.totalDeserialize();
    }

    /**
     * method that gets the map with a specified name
     * @param name map's name
     * @return a map Type with the specified name
     */
    public Map getThatMap(String name){
        for (Map map : maps){
            if(map.getName().equalsIgnoreCase(name))
                return map;
        }
        return null;
    }

    /**
     * destroys game
     */
    @Override
    @SuppressWarnings("Deprecated")
    public void finalize() throws Throwable{
        publicObjCard.clear();
        for (RoundSchemeCell aRoundSchemeMap : roundSchemeMap)
            try {
                if (aRoundSchemeMap.getRestOfStock() != null)
                    aRoundSchemeMap.getRestOfStock().clear();
            } catch (NullPointerException e) {
                //salta
            }
        toolCards.clear();
        for (Map map: maps)
            map.finalize();
        System.gc();
        super.finalize();
    }

    /**
     * removes a specified dice from the stock
     * @param dice a chosen dice from the stock
     */
    public void removeDiceStock(Dice dice) {
        for (int i=0; i<stock.size();i++)
            if (dice.getValue()==stock.get(i).getValue() && dice.getColor().equalsColor(stock.get(i).getColor()))
                stock.remove(i);
    }

    /**
     * chooses a random map from maps array list
     * @return a random map form maps array list
     */
    public Map randomMap(){
        Random random = new Random();
        int j = random.nextInt(maps.size());// estrai un numero casuale tra tutte le mappe disponibili
        return maps.remove(j); // ritorna la mappa estratta dall'array list di mappe
    }
}