package it.polimi.se2018.util.Deserializer;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.util.Deserializer.PublicCards.*;
import it.polimi.se2018.util.PublicCardsTransfer;
import it.polimi.se2018.util.jsonTransiction;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * class that deserialize the public cards dinamically building the type of cards
 * extends the strategy cards deserializer class
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
    private ArrayList<PublicObjectiveCard> publicObjectivetransfer;

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
        publicObjectivetransfer = new ArrayList();
    }

    @Override
    /**
     * class method that deserialize the json file and put the data about it in
     * publCard object in state of string
     */
    public void Deserializing() {
        Type trans = new TypeToken<ArrayList<PublicCardsTransfer>>(){}.getType();
        publCard = this.getGson().fromJson(this.getBr(), trans);
    }

    @Override
    /**
     * method use to get a cell of the arraylist with the info about the public card in form of string
     * @return a cell of the arraylist with some strings and integer
     */
    public PublicCardsTransfer getSingleTransiction(int index) {
        return this.publCard.get(index);
    }

    @Override
    /**
     * method that is use to set all the observers of the class
     */
    public void SetUpObserver() {
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
    public void SetUpDeck(){
        cdsBuilder.setDeck(this.publicObjectivetransfer);
        cvsBuilder.setDeck(this.publicObjectivetransfer);
        ccvsBuilder.setDeck(this.publicObjectivetransfer);
        csvsBuilder.setDeck(this.publicObjectivetransfer);
        dssBuilder.setDeck(this.publicObjectivetransfer);
        dssBuilder.setDeck(this.publicObjectivetransfer);
        lssBuilder.setDeck(this.publicObjectivetransfer);
        mssBuilder.setDeck(this.publicObjectivetransfer);
        rcvsBuilder.setDeck(this.publicObjectivetransfer);
        rsvsBuilder.setDeck(this.publicObjectivetransfer);
        svsBuilder.setDeck(this.publicObjectivetransfer);
    }

    /**
     * method that is use to get the arraylist with all the strings about all the cards
     * @return an arraylist with, in the cell, all the info about the cards, saved like strings
     */
    public ArrayList<PublicObjectiveCard> getPublicObjectivetransfer() {
        return publicObjectivetransfer;
    }

    /**
     * method that make the deserialize of the file json, by the invocation of all the methods
     * used to make it
     */
    public void TotalDeserializing(){
        this.Deserializing();
        this.SetUpObserver();
        for(int index=0; index<publCard.size();index++)
            this.StartBuilding(getSingleTransiction(index));
        this.SetUpDeck();
    }

    /**
     * method that notify the availibility of a cell of the arraylist of cards by string, to recognise it
     * like a card object
     * @param publCardsingle a cell of the arraylist of cards saved by strings
     */
    public void StartBuilding(PublicCardsTransfer publCardsingle){
        setChanged();
        notifyObservers(publCardsingle);
    }

    /**
     * method that is used to get the arraylist of all cards saved by strings
     * @return an arraylist of public cards transfer data structure
     */
    public ArrayList<PublicCardsTransfer> getPublCard() {
        return publCard;
    }
}
