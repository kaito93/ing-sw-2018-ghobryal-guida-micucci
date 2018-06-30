package it.polimi.se2018.client.view.ViewGuiPack.choice_window_pack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Controller {

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


    public void hasChoice(ActionEvent actionEvent) {
        if(map1.isSelected())
            System.out.println(map1.getText());
        else if(map2.isSelected())
            System.out.println(map2.getText());
        else if(map3.isSelected())
            System.out.println(map3.getText());
        else if(map4.isSelected())
            System.out.println(map4.getText());

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
}