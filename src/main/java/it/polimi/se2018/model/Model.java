package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.*;
import it.polimi.se2018.model.cards.public_objective_card_strategy.MediumShadesStrategy;
import it.polimi.se2018.model.cards.tool_card_strategy.FluxBrush;

import java.util.ArrayList;
import java.util.Random;

public class Model {

    public static final int MAXROUND = 10;

    static ArrayList<Dice> diceBag=new ArrayList<>();

    static ArrayList<Dice> stock = new ArrayList<>();

    static ArrayList<PublicObjectiveCard> publicObjCard =new ArrayList <>();

    RoundSchemeCell roundSchemeMap[];

    static ArrayList<ToolCard> toolCards = new ArrayList<>();





    public Model(){



        diceBag=loadDiceBag(); // carica i dadi dal file json

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

    public ArrayList<ToolCard> loadToolCards(){
        // TO DO MIK: Carica tutte le carte tools qui e ritornale

        // codice solo per test
        ArrayList<ToolCard> cards = new ArrayList<ToolCard>();
        for (int i=0; i<3; i++){
            cards.add(new ToolCard("ciao","ciao",1,Color.BLUE,new FluxBrush()));
        }
        return cards;
    }



    public ArrayList<Dice> loadDiceBag(){

        // TO DO MIK: CARICA I 90 DADI DA FILE JSON E RITORNALI

        return new ArrayList<Dice>(); // solo per non dare errore



    }



    public ArrayList<PublicObjectiveCard> loadPublicObjCard(){

        // TO DO MIK: CARICA LE CARTE OBIETTIVO PUBBLICO DA FILE JSON E RITORNALE

        // CODICE SOLO PER TEST.
        MediumShadesStrategy cards = new MediumShadesStrategy();
        ArrayList<PublicObjectiveCard> arr = new ArrayList<PublicObjectiveCard>();
        for (int i=0; i<3;i++) {
            PublicObjectiveCard card = new PublicObjectiveCard("1", "1", 1, cards);
            arr.add(card);
        }
        return arr;

    }

    public int getMaxRound() {
        return MAXROUND;
    }

    public ArrayList<Dice> getDiceBag() {
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
        for (int i=0; i<players.size();i++) // estrae n numeri casuali e inserisce le rispettive carte private dentro ai giocatori

        {

            Random random = new Random();

            int j = random.nextInt(cards.size());

            players.get(i).setPrivateObjectiveCard(cards.get(j));

            cards.remove(j);

        }

    }

    public ArrayList<PrivateObjectiveCard> loadPrivateObjectiveCard (){
        // TO DO MIK: CARICA QUI LE CARTE OBIETTIVO PRIVATO E RITORNALE

        // codice solo per test
        ArrayList<PrivateObjectiveCard> array = new ArrayList<PrivateObjectiveCard>();
        for (int i=0; i<5;i++){
            array.add(new PrivateObjectiveCard("ciao","ciaociao",Color.BLUE));
        }
        return array;
    }

    public ArrayList<Dice> getStock() {
        return stock;
    }
}