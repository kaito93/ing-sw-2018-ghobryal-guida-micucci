package it.polimi.se2018.client.view.ViewGuiPack.HostesPage;

import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * class to see in GUI the maps of the other players
 */
public class HostesFxmlController {

    @FXML
    private Button annulla;

    @FXML
    private GridPane map3;

    @FXML
    private GridPane map1;

    @FXML
    private GridPane map2;

    @FXML
    private Label avv1;

    @FXML
    private Label fav1;

    @FXML
    private Label avv2;

    @FXML
    private Label fav2;

    @FXML
    private Label avv3;

    @FXML
    private Label fav3;

    @FXML
    /**
     * method to close the window
     */
    void close(ActionEvent event) {
        try {
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    /**
     * inizialize method for the javaFX stage with FXML
     */
    void initialize(){
        avv1.setText(ModelFX.getInstance().getGbp().getUsers().get(0));
        avv2.setText(ModelFX.getInstance().getGbp().getUsers().get(1));
        avv3.setText(ModelFX.getInstance().getGbp().getUsers().get(2));
        fav1.setText(ModelFX.getInstance().getGbp().getFavors().get(0));
        fav2.setText(ModelFX.getInstance().getGbp().getFavors().get(1));
        fav3.setText(ModelFX.getInstance().getGbp().getFavors().get(2));
        setUpGlassWindow(map1, ModelFX.getInstance().getGbp().getMaps().get(0));
        setUpGlassWindow(map2, ModelFX.getInstance().getGbp().getMaps().get(1));
        setUpGlassWindow(map3, ModelFX.getInstance().getGbp().getMaps().get(2));
    }

    /**
     * method for the initialization of the grafical GlassWindow
     * @param grid to be designed
     * @param cell from whinch has to take the info
     */
    public void setUpGlassWindow(GridPane grid, Cell[][] cell) {
        for (int i = 0; i < 4; i++) {
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
        }
    }

}
