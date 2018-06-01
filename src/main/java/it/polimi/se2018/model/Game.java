package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.public_objective_card_strategy.MediumShadesStrategy;
import it.polimi.se2018.model.cards.tool_card_strategy.FluxBrush;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.util.DiceBox;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

public class Game {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    private static final int MAXROUND = 10;

    private static DiceBox diceBag;

    private static ArrayList<Dice> stock = new ArrayList<>();

    private static ArrayList<PublicObjectiveCard> publicObjCard =new ArrayList <>();

    private RoundSchemeCell roundSchemeMap[];

    private static ArrayList<ToolCard> toolCards = new ArrayList<>();





    public Game(){


        try {
            diceBag=new DiceBox(); // carica i dadi dal file json
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

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


    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    private ArrayList<ToolCard> loadToolCards(){
        // TO DO MIK: Carica tutte le carte tools qui e ritornale

        // codice solo per test
        ArrayList<ToolCard> cards = new ArrayList<>();
        for (int i=0; i<3; i++){
            cards.add(new ToolCard("ciao","ciao",1,Color.BLUE,new FluxBrush()));
        }
        return cards;
    }



    private ArrayList<Dice> loadDiceBag(){

        // TO DO MIK: CARICA I 90 DADI DA FILE JSON E FA UN SHUFFLE E THROWDICE PER OGNI DADO
        // COSì ESCONO DADI COMPLETAMENTE CASUALI E POI RITORNALI

        return new ArrayList<>(); // solo per non dare errore



    }



    private ArrayList<PublicObjectiveCard> loadPublicObjCard(){

        // TO DO MIK: CARICA LE CARTE OBIETTIVO PUBBLICO DA FILE JSON E RITORNALE

        // CODICE SOLO PER TEST.
        MediumShadesStrategy cards = new MediumShadesStrategy();
        ArrayList<PublicObjectiveCard> arr = new ArrayList<>();
        for (int i=0; i<3;i++) {
            PublicObjectiveCard card = new PublicObjectiveCard("1", "1", 1, cards);
            arr.add(card);
        }
        return arr;

    }

    public int getMaxRound() {
        return MAXROUND;
    }

    public DiceBox getDiceBag() {
        return diceBag;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjCard() {
        return publicObjCard;
    }

    public RoundSchemeCell[] getRoundSchemeMap() {
        return roundSchemeMap;
    }

    public void setPrivateObjectiveCard(ArrayList<Player> players){

        ArrayList<PrivateObjectiveCard> cards = loadPrivateObjectiveCard();
        for (Player player : players) {

            Random random = new Random();

            int j = random.nextInt(cards.size());

            player.setPrivateObjectiveCard(cards.get(j));

            cards.remove(j);

        }

    }

    private ArrayList<PrivateObjectiveCard> loadPrivateObjectiveCard(){
        // TO DO MIK: CARICA QUI LE CARTE OBIETTIVO PRIVATO E RITORNALE

        // codice solo per test
        ArrayList<PrivateObjectiveCard> array = new ArrayList<>();
        for (int i=0; i<5;i++){
            array.add(new PrivateObjectiveCard("ciao","ciaociao",Color.BLUE));
        }
        return array;
    }

    public ArrayList<Dice> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Dice> stocks) {
        stock = stocks;
    }
}