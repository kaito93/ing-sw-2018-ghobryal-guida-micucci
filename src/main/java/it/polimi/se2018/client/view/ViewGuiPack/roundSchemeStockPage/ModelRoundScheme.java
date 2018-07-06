package it.polimi.se2018.client.view.ViewGuiPack.roundSchemeStockPage;

import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import javafx.scene.layout.GridPane;

/**
 * class model for RoundScheme
 * manage all the data for that class
 * @author Andrea Micucci
 */
public class ModelRoundScheme {

   RoundSchemeCell roundSchemeCell;

    /**
     * class constructor empty
     */
    public ModelRoundScheme(){

   }

    /**
     * setter method for roundSchemeCell
     * @param roundSchemeCell to be setted
     */
    public void setRoundSchemeCell(RoundSchemeCell roundSchemeCell) {
        this.roundSchemeCell = roundSchemeCell;
    }

    /**
     * getter method for roundSchemeCell
     * @return a RoundSchemeCell
     */
    public RoundSchemeCell getRoundSchemeCell() {
        return roundSchemeCell;
    }
}
