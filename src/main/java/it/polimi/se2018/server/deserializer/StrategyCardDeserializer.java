package it.polimi.se2018.server.deserializer;

import it.polimi.se2018.shared.Deserializer;


/**
 * abstract class that will use to make the deserializer of the cards
 */
public abstract class StrategyCardDeserializer extends Deserializer {


    /**
     * class constructor that inizialize the reading of the json file
     *
     * @param path path of the json file
     */
    public StrategyCardDeserializer(String path) {
        super(path);
    }

    /**
     * method that extract a cell from the arraylist of jsontransiction
     *
     * @param index of cell that need to be extract
     * @return the cell of the arraylist
     */
    public abstract JsonTransition getSingleTransiction(int index);

    /**
     * abstract method to set-up the observers of this class
     */
    public abstract void setUpObserver();

    public abstract void totalDeserializing();

}
