package it.polimi.se2018.client.view.ViewGuiPack.gameBoardPage;

import com.sun.org.apache.xpath.internal.operations.Mod;
import it.polimi.se2018.client.view.ViewGuiPack.FxmlOpener;
import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Color;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * class for manage the fx components of the gameboard
 * @author Andrea Micucci
 */
public class ControllerSagradaGui {

    @FXML
    private MenuBar matchMenu;

    @FXML
    private MenuItem disconnessione;

    @FXML
    private MenuItem closeRequest;

    @FXML
    private GridPane glassWindow;

    @FXML
    private Button SaltaTurno;

    @FXML
    private GridPane reserve;

    @FXML
    private TextFlow output;

    @FXML
    private Button RoundSchemeButton;

    @FXML
    private Button hostesButton;


    @FXML
    void handleCloseMenu(ActionEvent event) {

    }

    @FXML
    void handleDisconnectButton(ActionEvent event) {

    }

    @FXML
    void handleLoggerPress(KeyEvent event) {

    }

    @FXML
    void handleSaltaClick(ActionEvent event) {

    }



    @FXML
    /**
     * inizialize method for the gameboard
     */
    void initialize() {
        Cell[][] cell = ModelFX.getInstance().getGbp().getDefinitiveMap();
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
                    glassWindow.add(pane, j, i);
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
                    glassWindow.add(text, j, i);
                    GridPane.setValignment(text, VPos.CENTER);
                    GridPane.setHalignment(text, HPos.CENTER);
                }

            }
        }
    }

    /**
     * method to open the roundscheme
     * @param event mouse click on button
     */
    public void OpenRound(ActionEvent event) {
        FxmlOpener.getInstance().openFX("/roundSchemeStock.fxml");
    }

    /**
     * method to show the other map
     * @param event click on button
     */
    public void showHostes(ActionEvent event) {
    FxmlOpener.getInstance().openFX("/hostesFxml.fxml");
    }

    /**
     * method to show the public cards
     * @param event click on button
     */
    public void showpubliccards(ActionEvent event) {
            try {
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (Exception ex) {
                ex.printStackTrace();

        }
        FxmlOpener.getInstance().openFX("Publiccards.fxml");
    }

    /**
     * method to show the tool cards
     * @param event click on button
     */
    public void showToolcards(ActionEvent event) {
    }

    /**
     * method to show the private cards
     * @param event click on button
     */
    public void ShowPrivatecards(ActionEvent event) {
    }
}