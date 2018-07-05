package it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;


public class ControllerMapChoice {
    static Stage stage = new Stage();
    ModelFX model;

    @FXML
    private static RadioButton map1 = new RadioButton("map1");
    @FXML
    private static RadioButton map2 = new RadioButton("map2");
    @FXML
    private static RadioButton map3 = new RadioButton("map3");
    @FXML
    private static RadioButton map4 = new RadioButton("map4");

    @FXML
    private static ToggleGroup maps = new ToggleGroup();


    public void init(ModelFX nodel){
        map1.setUserData(map1);
        map1.setToggleGroup(maps);
        map2.setUserData(map2);
        map2.setToggleGroup(maps);
        map3.setUserData(map3);
        map3.setToggleGroup(maps);
        map4.setUserData(map4);
        map4.setToggleGroup(maps);
        model = nodel;
    }


    public void setSelected4(ActionEvent actionEvent) {
        map4.setSelected(true);
        map3.setSelected(false);
        map2.setSelected(false);
        map1.setSelected(false);
    }

    public void setSelected3(ActionEvent actionEvent) {
        map3.setSelected(true);
        map4.setSelected(false);
        map2.setSelected(false);
        map1.setSelected(false);
    }
    public void setSelected2(ActionEvent actionEvent) {
        map2.setSelected(true);
        map3.setSelected(false);
        map4.setSelected(false);
        map1.setSelected(false);
    }
    public void setSelected1(ActionEvent actionEvent) {
        map1.setSelected(true);
        map3.setSelected(false);
        map2.setSelected(false);
        map4.setSelected(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void hasChoice(ActionEvent event) {
    }
}
