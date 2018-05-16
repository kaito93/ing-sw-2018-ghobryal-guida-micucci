package it.polimi.se2018.model;

import it.polimi.se2018.model.cards.*;
import java.util.ArrayList;
import java.util.Random;

public class Model {

    final int maxRound = 10;

    static ArrayList<Dice> diceBag=new ArrayList <Dice>();

    static ArrayList<PublicObjectiveCard> publicObjCard =new ArrayList <PublicObjectiveCard>();

    RoundSchemeCell roundSchemeMap[];





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

        this.roundSchemeMap= new RoundSchemeCell[maxRound]; // Crea il tracciato dei round



    }



    public ArrayList<Dice> loadDiceBag(){

        // TO DO MIK: CARICA I 90 DADI DA FILE JSON E RITORNALI

        return new ArrayList<Dice>(); // solo per non dare errore



    }



    public ArrayList<PublicObjectiveCard> loadPublicObjCard(){

        // TO DO MIK: CARICA LE CARTE OBIETTIVO PUBBLICO DA FILE JSON E RITORNALE

        return new ArrayList<PublicObjectiveCard>();

    }



}