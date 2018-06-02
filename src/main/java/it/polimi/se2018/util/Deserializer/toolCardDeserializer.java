package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.ToolCard;
import it.polimi.se2018.model.cards.tool_card_strategy.CopperFoilBurnisher;
import it.polimi.se2018.model.cards.tool_card_strategy.GlazingHammer;
import it.polimi.se2018.util.Deserializer.ToolCards.*;

import java.util.ArrayList;

/**
 * class that create the deck of all cards, extends StrategyCardDeserializer
 */
public class toolCardDeserializer extends StrategyCardDeserializer {

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


    /**
     * class constructor: build all the object observer and all the variables of the class
     * @param pathname of the json file that need to be deserialized
     */
    public toolCardDeserializer(String pathname) {
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
    }

    @Override
    /**
     * method that initialize all the observer of the class, to make possible the creations of the cards
     */
    public void SetUpObserver() {
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
    public void MountDeck(){
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
    public ArrayList<ToolCard> getDeck() {
        return deck;
    }

    public void TotalDeserializing(){
        this.Deserializing();
        this.SetUpObserver();
        for(int index=0; index<jsontrans.size(); index++)
            this.StartBuilding(jsontrans.get(index));
        this.MountDeck();
    }

}
