package it.polimi.se2018.client.view.ViewGuiPack.roundSchemeStockPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class RoundSchemeController {

    @FXML
    private GridPane roundschemestock;

    public void returnOnGameboard(ActionEvent event) {

        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void initialize(){

    }
}
