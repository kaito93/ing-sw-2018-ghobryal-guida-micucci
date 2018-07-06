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
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class ControllerSagradaGui {

    @FXML
    private MenuBar matchMenu;

    @FXML
    private GridPane round;

    @FXML
    private Button RoundSchemeButton;

    @FXML
    private MenuItem disconnessione;

    @FXML
    private MenuItem closeRequest;

    @FXML
    private GridPane glassWindow;

    @FXML
    private GridPane priv;

    @FXML
    private GridPane publicCards;

    @FXML
    private GridPane toolCards;

    @FXML
    private Button SaltaTurno;

    @FXML
    private GridPane reserve;

    @FXML
    private TextFlow output;

    @FXML
    private Pane logger;

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
                    pane.getChildren().add(text);
                    glassWindow.add(text, j, i);
                    GridPane.setValignment(pane, VPos.CENTER);
                    GridPane.setHalignment(pane, HPos.CENTER);
                }

            }
        }

        Text titlePriv = new Text();
        titlePriv.setText(ModelFX.getInstance().getGbp().getPrivateCardTitle());
        Text descriptionPriv = new Text();
        descriptionPriv.setText(ModelFX.getInstance().getGbp().getPrivateCardDescription());
        Pane privPane = new Pane();
        privPane.getChildren().addAll(titlePriv, descriptionPriv);

        ArrayList<Text> card1 = new ArrayList<>();
        ArrayList<Text> card2 = new ArrayList<>();
        ArrayList<Text> card3 = new ArrayList<>();

        Text temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getTitlePub().get(0));
        card1.add(temp);
        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getDescriptionPub().get(0));
        card1.add(temp);
        temp = new Text();
        temp.setText(String.valueOf(ModelFX.getInstance().getGbp().getScorePub().get(0)));
        card1.add(temp);

        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getTitlePub().get(1));
        card2.add(temp);
        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getDescriptionPub().get(1));
        card2.add(temp);
        temp = new Text();
        temp.setText(String.valueOf(ModelFX.getInstance().getGbp().getScorePub().get(1)));
        card2.add(temp);

        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getTitlePub().get(2));
        card3.add(temp);
        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getDescriptionPub().get(2));
        card3.add(temp);
        temp = new Text();
        temp.setText(String.valueOf(ModelFX.getInstance().getGbp().getScorePub().get(2)));
        card3.add(temp);

        Pane car1 = new Pane();
        Pane car2 = new Pane();
        Pane car3 = new Pane();

        car1.getChildren().addAll(card1);
        car2.getChildren().addAll(card2);
        car3.getChildren().addAll(card3);
        publicCards.getChildren().add(car1);
        publicCards.getChildren().add(car2);
        publicCards.getChildren().add(car3);

        ArrayList<Text> card1Tool = new ArrayList<>();
        ArrayList<Text> card2Tool = new ArrayList<>();
        ArrayList<Text> card3Tool = new ArrayList<>();

        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getTitleTools().get(0));
        card1Tool.add(temp);
        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getDescriptionTool().get(0));
        card1Tool.add(temp);
        temp = new Text();
        temp.setText("Usato: " + String.valueOf(ModelFX.getInstance().getGbp().getUsageTool().get(0)));
        card1Tool.add(temp);

        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getTitleTools().get(1));
        card2Tool.add(temp);
        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getDescriptionTool().get(1));
        card2Tool.add(temp);
        temp = new Text();
        temp.setText("Usato: " + String.valueOf(ModelFX.getInstance().getGbp().getUsageTool().get(1)));
        card2Tool.add(temp);

        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getTitleTools().get(2));
        card3Tool.add(temp);
        temp = new Text();
        temp.setText(ModelFX.getInstance().getGbp().getDescriptionTool().get(2));
        card3Tool.add(temp);
        temp = new Text();
        temp.setText("Usato: " + String.valueOf(ModelFX.getInstance().getGbp().getUsageTool().get(2)));
        card3Tool.add(temp);

        Pane car1Tool = new Pane();
        Pane car2Tool = new Pane();
        Pane car3Tool = new Pane();

        car1Tool.getChildren().addAll(card1Tool);
        car2Tool.getChildren().addAll(card2Tool);
        car3Tool.getChildren().addAll(card3Tool);
        toolCards.getChildren().add(car1);
        toolCards.getChildren().add(car2);
        toolCards.getChildren().add(car3);

        List<Dice> stok = ModelFX.getInstance().getGbp().getStock();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Text text = new Text();
                Pane pane = new Pane();
                {
                    if (stok.get(i).getColor() == Color.YELLOW)
                            pane.setStyle("-fx-background-color: #ffff00;");
                    if (stok.get(i).getColor() == Color.GREEN)
                        pane.setStyle("-fx-background-color: #00cc00;");
                    if (stok.get(i).getColor() == Color.RED)
                        pane.setStyle("-fx-background-color: #ff3300;");
                    if (stok.get(i).getColor() == Color.PURPLE) {
                        pane.setStyle("-fx-background-color: #bf00ff;");
                    }
                    if (stok.get(i).getColor() == Color.BLUE)
                        pane.setStyle("-fx-background-color: #0066ff;");
                    glassWindow.add(pane, j, i);
                }
                {
                    if (stok.get(i).getValue() == 1)
                        text.setText("1");
                    else if (stok.get(i).getValue() == 2)
                        text.setText("2");
                    else if (stok.get(i).getValue() == 3)
                        text.setText("3");
                    else if (stok.get(i).getValue() == 4)
                        text.setText("4");
                    else if (stok.get(i).getValue() == 5)
                        text.setText("5");
                    else if (stok.get(i).getValue() == 6)
                        text.setText("6");
                    pane.getChildren().add(text);
                    glassWindow.add(text, j, i);
                    GridPane.setValignment(pane, VPos.CENTER);
                    GridPane.setHalignment(pane, HPos.CENTER);
                }

            }

        }

        RoundSchemeCell[] roundscheme = ModelFX.getInstance().getGbp().getSchemeRound();

    }

    public void OpenRound(ActionEvent event) {
        FxmlOpener.getInstance().openFX("/roundSchemeStock.fxml");
    }
}