package it.polimi.se2018.util.Deserializer;

import it.polimi.se2018.model.cards.tool_card_strategy.CopperFoilBurnisher;
import it.polimi.se2018.util.Deserializer.ToolCards.CopperFoilBurnisherBuilder;

public class toolCardDeserializer extends StrategyCardDeserializer {

    private CopperFoilBurnisherBuilder cfbBuilder;
    /**
     * class constructor, deserialize all public objective card from
     * a json file
     *
     * @param pathname
    // * @throws FileNotFoundException if the json file doesn't exist
     */
    public toolCardDeserializer(String pathname) {
        super(pathname);
    }

    @Override
    public void SetUpObserver() {

    }
}
