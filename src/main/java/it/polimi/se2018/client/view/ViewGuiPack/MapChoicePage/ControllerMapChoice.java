package it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage;

import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class ControllerMapChoice {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


    @FXML
    private RadioButton map1 = new RadioButton("map1");
    @FXML
    private RadioButton map2 = new RadioButton("map2");
    @FXML
    private RadioButton map3 = new RadioButton("map3");
    @FXML
    private RadioButton map4 = new RadioButton("map4");

    @FXML
    private static ToggleGroup maps = new ToggleGroup();


    @FXML
    private GridPane grid1;

    @FXML
    private GridPane grid2;

    @FXML
    private GridPane grid3;

    @FXML
    private GridPane grid4;

    @FXML
    private Label fav1;

    @FXML
    private Label fav2;

    @FXML
    private Label fav3;

    @FXML
    private Label fav4;

    Cell[][] mapDefinitive;


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


    public void hasChoice(ActionEvent event) {
        if ((map1.isSelected()) || (map2.isSelected()) || (map3.isSelected()) || (map4.isSelected())) {
            if (map1.isSelected()) {
                ModelFX.getInstance().getGbp().setDefinitiveMap(ModelFX.getInstance().getChoice().getMaps().get(0));
                ModelFX.getInstance().getGbp().setFav(ModelFX.getInstance().getChoice().getFavour().get(0));
                ModelFX.getInstance().getGbp().setName(ModelFX.getInstance().getChoice().getMapName().get(0));
                ModelFX.getInstance().getChoice().setGridDefinitive(grid1);
                ModelFX.getInstance().getChoice().setIndexOfDefinitiveMap(0);
            }
            if (map2.isSelected()) {
                ModelFX.getInstance().getGbp().setDefinitiveMap(ModelFX.getInstance().getChoice().getMaps().get(1));
                ModelFX.getInstance().getGbp().setFav(ModelFX.getInstance().getChoice().getFavour().get(1));
                ModelFX.getInstance().getGbp().setName(ModelFX.getInstance().getChoice().getMapName().get(1));
                ModelFX.getInstance().getChoice().setIndexOfDefinitiveMap(1);
                ModelFX.getInstance().getChoice().setGridDefinitive(grid2);
            }
            if (map3.isSelected()) {
                ModelFX.getInstance().getGbp().setDefinitiveMap(ModelFX.getInstance().getChoice().getMaps().get(2));
                ModelFX.getInstance().getGbp().setFav(ModelFX.getInstance().getChoice().getFavour().get(2));
                ModelFX.getInstance().getGbp().setName(ModelFX.getInstance().getChoice().getMapName().get(2));
                ModelFX.getInstance().getChoice().setGridDefinitive(grid3);
                ModelFX.getInstance().getChoice().setIndexOfDefinitiveMap(2);
            }
            if (map4.isSelected()) {
                ModelFX.getInstance().getGbp().setDefinitiveMap(ModelFX.getInstance().getChoice().getMaps().get(3));
                ModelFX.getInstance().getGbp().setFav(ModelFX.getInstance().getChoice().getFavour().get(3));
                ModelFX.getInstance().getGbp().setName(ModelFX.getInstance().getChoice().getMapName().get(3));
                ModelFX.getInstance().getChoice().setGridDefinitive(grid4);
                ModelFX.getInstance().getChoice().setIndexOfDefinitiveMap(3);
            }

            try {
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    public void favourBuilding(List<Integer> favour) {
        fav1.setText(String.valueOf(favour.get(0)));
        fav2.setText(String.valueOf(favour.get(1)));
        fav3.setText(String.valueOf(favour.get(2)));
        fav4.setText(String.valueOf(favour.get(3)));
    }

    public void mapNameBuilding(List<String> mapName) {
        map1.setText(mapName.get(0));
        map2.setText(mapName.get(1));
        map3.setText(mapName.get(2));
        map4.setText(mapName.get(3));
    }

    public void setUpGlassWindow(GridPane grid, Cell[][] cell) {
        /*for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                Text text = new Text();
                Pane pane = new Pane();
                if (cell[i][j].getColor() == Color.NULL && cell[i][j].getValue() == 0)
                    pane.setStyle("-fx-background-color: #ffffff;");
                else if (cell[i][j].getColor() != Color.NULL) {
                    if (cell[i][j].getColor() == Color.YELLOW)
                        pane.setStyle("-fx-background-color: #ffff00;");
                    if (cell[i][j].getColor() == Color.GREEN)
                        pane.setStyle("-fx-background-color: #00cc00;");
                    if (cell[i][j].getColor() == Color.RED)
                        pane.setStyle("-fx-background-color: #ff3300;");
                    if (cell[i][j].getColor() == Color.PURPLE)
                        pane.setStyle("-fx-background-color: #bf00ff;");
                    if (cell[i][j].getColor() == Color.BLUE)
                        pane.setStyle("-fx-background-color: #0066ff;");
                    grid.add(pane, j, i);
                } else if (cell[i][j].getValue() != 0) {
                    if (cell[i][j].getValue() == 1)
                        text.setText("1");
                    else if (cell[i][j].getValue() == 2)
                        text.setText("2");
                    else if (cell[i][j].getValue() == 3)
                        text.setText("3");
                    else if (cell[i][j].getValue() == 4)
                        text.setText("4");
                    else if (cell[i][j].getValue() == 5)
                        text.setText("5");
                    else if (cell[i][j].getValue() == 6)
                        text.setText("6");
                    grid.add(text, j, i);
                    GridPane.setValignment(text, VPos.CENTER);
                    GridPane.setHalignment(text, HPos.CENTER);
                }

            }
        }*/
    }

    @FXML
    void initialize() {
        favourBuilding(ModelFX.getInstance().getChoice().getFavour());
        mapNameBuilding(ModelFX.getInstance().getChoice().getMapName());
        setUpGlassWindow(grid1, ModelFX.getInstance().getChoice().getMaps().get(0));
        setUpGlassWindow(grid2, ModelFX.getInstance().getChoice().getMaps().get(1));
        setUpGlassWindow(grid3, ModelFX.getInstance().getChoice().getMaps().get(2));
        setUpGlassWindow(grid4, ModelFX.getInstance().getChoice().getMaps().get(3));
    }
}
