package it.polimi.se2018.server.deserializer.public_cards;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.server.deserializer.StrategyCardDeserializer;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.deserializer.public_cards.public_card_strategy.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * class that deserializer the public cards dinamically building the type of cards
 * extends the strategy cards deserializer class
 * @author Andrea Micucci
 */
public class PublicCardDeserializer extends StrategyCardDeserializer {

    private ArrayList<PublicCardsTransfer> publCard;
    private ColorDiagonalsStrategyBuilder cdsBuilder;
    private ColorVarietyStrategyBuilder cvsBuilder;
    private ColumnColorVarietyStrategyBuilder ccvsBuilder;
    private ColumnShadeVarietyStrategyBuilder csvsBuilder;
    private DeepShadesStrategyBuilder dssBuilder;
    private LightShadesStrategyBuilder lssBuilder;
    private MediumShadesStrategyBuilder mssBuilder;
    private RowColorVarietyStrategyBuilder rcvsBuilder;
    private RowShadeVarietyStrategyBuilder rsvsBuilder;
    private ShadeVarietyStrategyBuilder svsBuilder;
    private ArrayList<PublicObjectiveCard> publicObjectiveTransfer;

    /**
     * class constructor that inizialize the buffered reader for the reading of the
     * json file contain the public cards
     * @param pathname of the file that has to be deserialized
     */
    public PublicCardDeserializer(String pathname){
        super(pathname);
        cdsBuilder = new ColorDiagonalsStrategyBuilder();
        cvsBuilder = new ColorVarietyStrategyBuilder();
        ccvsBuilder = new ColumnColorVarietyStrategyBuilder();
        csvsBuilder = new ColumnShadeVarietyStrategyBuilder();
        dssBuilder = new DeepShadesStrategyBuilder();
        lssBuilder = new LightShadesStrategyBuilder();
        mssBuilder = new MediumShadesStrategyBuilder();
        rcvsBuilder = new RowColorVarietyStrategyBuilder();
        rsvsBuilder = new RowShadeVarietyStrategyBuilder();
        svsBuilder = new ShadeVarietyStrategyBuilder();
        publicObjectiveTransfer = new ArrayList<>();
    }


    /**
     * class method that deserializer the json file and put the data about it in
     * publCard object in state of string
     */
    @Override
    public void deserializing() {
        Type trans = new TypeToken<ArrayList<PublicCardsTransfer>>(){}.getType();
        publCard = this.getGson().fromJson(this.getBr(), trans);
    }

    /**
     * method use to get a cell of the arraylist with the info about the public card in form of string
     * @return a cell of the arraylist with some strings and integer
     */
    @Override
    public PublicCardsTransfer getSingleTransiction(int index) {
        return this.publCard.get(index);
    }

    /**
     * method that is use to set all the observers of the class
     */
    @Override
    public void setUpObserver() {
        this.addObserver(cdsBuilder);
        this.addObserver(cvsBuilder);
        this.addObserver(ccvsBuilder);
        this.addObserver(csvsBuilder);
        this.addObserver(dssBuilder);
        this.addObserver(lssBuilder);
        this.addObserver(mssBuilder);
        this.addObserver(rcvsBuilder);
        this.addObserver(rsvsBuilder);
        this.addObserver(svsBuilder);
    }


    /**
     * method that take all the cards objects created by the observers and
     * put them in an arraylist of cards
     */
    private void setUpDeck(){
        cdsBuilder.setDeck(this.publicObjectiveTransfer);
        cvsBuilder.setDeck(this.publicObjectiveTransfer);
        ccvsBuilder.setDeck(this.publicObjectiveTransfer);
        csvsBuilder.setDeck(this.publicObjectiveTransfer);
        dssBuilder.setDeck(this.publicObjectiveTransfer);
        dssBuilder.setDeck(this.publicObjectiveTransfer);
        lssBuilder.setDeck(this.publicObjectiveTransfer);
        mssBuilder.setDeck(this.publicObjectiveTransfer);
        rcvsBuilder.setDeck(this.publicObjectiveTransfer);
        rsvsBuilder.setDeck(this.publicObjectiveTransfer);
        svsBuilder.setDeck(this.publicObjectiveTransfer);
    }

    /**
     * method that is use to get the arraylist with all the strings about all the cards
     * @return an arraylist with, in the cell, all the info about the cards, saved like strings
     */
    public List<PublicObjectiveCard> getPublicObjectiveTransfer() {
        return publicObjectiveTransfer;
    }

    /**
     * method that make the deserializer of the file json, by the invocation of all the methods
     * used to make it
     */
    public void totalDeserializing(){
        this.deserializing();
        this.setUpObserver();
        for(int index=0; index<publCard.size();index++)
            this.startBuilding(getSingleTransiction(index));
        this.setUpDeck();
    }

    /**
     * method that notify the availibility of a cell of the arraylist of cards by string, to recognise it
     * like a card object
     * @param publicCardsTransfer a cell of the arraylist of cards saved by strings
     */
    private void startBuilding(PublicCardsTransfer publicCardsTransfer){
        setChanged();
        notifyObservers(publicCardsTransfer);
    }
}
