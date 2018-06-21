package it.polimi.se2018.server.deserializer.tool_cards;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.server.deserializer.StrategyCardDeserializer;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.server.deserializer.tool_cards.tool_cards_strategy.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * class that create the deck of all cards, extends StrategyCardDeserializer
 */
public class ToolCardDeserializer extends StrategyCardDeserializer {

    private CopperFoilBurnisherBuilder cfbBuilder;
    private CorkbackedStraightedgeBuilder csBuilder;
    private EglomiseBrushBuilder ebBuilder;
    private FluxBrushBuilder fbBuilder;
    private FluxRemoverBuilder frBuilder;
    private GlazingHammerBuilder ghBuilder;
    private GrindingStoneBuilder gsBuilder;
    private GrozingPliersBuilder gpBuilder;
    private LathekinBuilder lBuilder;
    private LensCutterBuilder lcBuilder;
    private RunningPliersBuilder rpBuilder;
    private TapWheelBuilder twBuilder;
    private ArrayList<ToolCard> deck;
    private ArrayList<ToolCardTransfer> tooltrans;


    /**
     * class constructor: build all the object observer and all the variables of the class
     * @param pathname of the json file that need to be deserialized
     */
    public ToolCardDeserializer(String pathname) {
        super(pathname);
        cfbBuilder = new CopperFoilBurnisherBuilder();
        csBuilder = new CorkbackedStraightedgeBuilder();
        ebBuilder = new EglomiseBrushBuilder();
        fbBuilder = new FluxBrushBuilder();
        frBuilder = new FluxRemoverBuilder();
        ghBuilder = new GlazingHammerBuilder();
        gsBuilder = new GrindingStoneBuilder();
        gpBuilder = new GrozingPliersBuilder();
        lBuilder = new LathekinBuilder();
        lcBuilder = new LensCutterBuilder();
        rpBuilder = new RunningPliersBuilder();
        twBuilder = new TapWheelBuilder();
        deck = new ArrayList<>();
        tooltrans = new ArrayList<>();
    }

    @Override
    public void deserializing() {
        Type listTool = new TypeToken<ArrayList<ToolCardTransfer>>(){}.getType();
        tooltrans= this.getGson().fromJson(this.getBr(), listTool);
    }

    @Override
    public ToolCardTransfer getSingleTransiction(int index) {
        return this.tooltrans.get(index);
    }

    /**
     * method that initialize all the observer of the class, to make possible the creations of the cards
     */
    @Override
    public void setUpObserver() {
        this.addObserver(cfbBuilder);
        this.addObserver(csBuilder);
        this.addObserver(ebBuilder);
        this.addObserver(fbBuilder);
        this.addObserver(frBuilder);
        this.addObserver(ghBuilder);
        this.addObserver(gsBuilder);
        this.addObserver(gpBuilder);
        this.addObserver(lBuilder);
        this.addObserver(lcBuilder);
        this.addObserver(rpBuilder);
        this.addObserver(twBuilder);

    }

    /**
     * method that take all the cards created by the observers and put it in an arraylist that take the part of deck of tool cards
     */
    private void mountDeck(){
        cfbBuilder.setDeck(this.deck);
        csBuilder.setDeck(this.deck);
        ebBuilder.setDeck(this.deck);
        fbBuilder.setDeck(this.deck);
        frBuilder.setDeck(this.deck);
        ghBuilder.setDeck(this.deck);
        gsBuilder.setDeck(this.deck);
        gpBuilder.setDeck(this.deck);
        lBuilder.setDeck(this.deck);
        lcBuilder.setDeck(this.deck);
        rpBuilder.setDeck(this.deck);
        twBuilder.setDeck(this.deck);
    }

    /**
     * getter method that return the deck of all cards created from json files
     * @return the deck of cards
     */
    public List<ToolCard> getDeck() {
        return deck;
    }

    public void totalDeserializing(){
        this.deserializing();
        this.setUpObserver();
        for (ToolCardTransfer tooltran : tooltrans) this.startBuilding(tooltran);
        this.mountDeck();
    }

    private void startBuilding(ToolCardTransfer toolCardsingle){
        setChanged();
        notifyObservers(toolCardsingle);
    }

}
