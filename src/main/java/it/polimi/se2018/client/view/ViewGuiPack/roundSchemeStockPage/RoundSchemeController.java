package it.polimi.se2018.client.view.ViewGuiPack.roundSchemeStockPage;

import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.logging.Level;

/**
 * Controller class for RoundScheme fx window
 * manage all the behaviour in that window
 * @author Andrea Micucci
 */
public class RoundSchemeController {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


    @FXML
    private GridPane roundschemestock;

    /**
     * method to return to the window of the gameboard
     * @param event a clicked button
     */
    public void returnOnGameboard(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    @FXML
    /**
     * method to initialize the FX window
     */
    void initialize(){
        int rowindex=0;
        int columnindex=0;
        for (int j = 0; j < ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().size(); j++) {

            Text text = new Text();
            Pane pane = new Pane();
            {
                if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getColor() == Color.YELLOW)
                    pane.setStyle("-fx-background-color: #ffff00;");
                if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getColor() == Color.GREEN)
                    pane.setStyle("-fx-background-color: #00cc00;");
                if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getColor() == Color.RED)
                    pane.setStyle("-fx-background-color: #ff3300;");
                if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getColor() == Color.PURPLE)
                    pane.setStyle("-fx-background-color: #bf00ff;");
                if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getColor() == Color.BLUE)
                    pane.setStyle("-fx-background-color: #0066ff;");
            }
            {
                if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getValue() == 1)
                    text.setText("1");
                else if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getValue() == 2)
                    text.setText("2");
                else if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getValue() == 3)
                    text.setText("3");
                else if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getValue() == 4)
                    text.setText("4");
                else if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getValue() == 5)
                    text.setText("5");
                else if (ModelFX.getInstance().getMrs().getRoundSchemeCell().getRestOfStock().get(j).getValue() == 6)
                    text.setText("6");
                pane.getChildren().add(text);
            }
            if(columnindex != 0)
            {
                if (columnindex % 5 == 0){
                    rowindex++;
                    columnindex = 0;
                }
                else
                    columnindex++;
                }
            roundschemestock.add(pane, columnindex, rowindex);
            GridPane.setValignment(pane, VPos.CENTER);
            GridPane.setHalignment(pane, HPos.CENTER);
        }
    }
}

