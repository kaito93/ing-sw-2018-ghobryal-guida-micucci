package it.polimi.se2018.client.view.ViewGuiPack;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class playingBoardScene {

    private static HBox publicCards;
    private static HBox toolCards;
    private static HBox cardBox;
    private static HBox roundSchemeBox;
    //per il logger che faccio?
    private static ArrayList<Rectangle> roundScheme;
    private static ArrayList<Rectangle> publicCardsArraylist;
    private static ArrayList<Rectangle> toolCardsArraylist;
    private static ArrayList<Rectangle> reserve;
    private static VBox reserveBox;
    private static HBox mapBoard;
    private static Rectangle privCard;
    private static VBox map;
    private static VBox board;
    private static Stage stage;
    private static Button saltaTurno;


    public playingBoardScene(Stage primaryStage, VBox chosenMap){
    map = chosenMap;
    board = new VBox(20);
    stage = primaryStage;
    publicCards = new HBox(50);
    toolCards = new HBox(50);
    cardBox = new HBox(30);
    roundSchemeBox = new HBox(0);
    roundScheme = new ArrayList<>();
    for(int index = 0; index<9; index++)
            roundScheme.add(new Rectangle(100, 100));
    reserve = new ArrayList<>();
    for(int index = 0; index<8; index++)
            reserve.add(new Rectangle(50, 50));
    publicCardsArraylist = new ArrayList<>();
    for(int index = 0; index<3; index++)
            publicCardsArraylist.add(new Rectangle(100,150));
    toolCardsArraylist = new ArrayList<>();
        for(int index = 0; index<3; index++)
            toolCardsArraylist.add(new Rectangle(100,150));
    reserveBox = new VBox(0);
    privCard = new Rectangle(100, 150);
    saltaTurno = new Button("Salta il turno");
    saltaTurno.setOnAction( e ->{
        Platform.exit();
    });
    mapBoard = new HBox(30);
    }

    public static void setUpReserve(){
        HBox line1 = new HBox(0);
        HBox line2 = new HBox(0);
        HBox line3 = new HBox(0);
        for(int index = 0; index<8; index++)
        {
            if (index < 3)
                line1.getChildren().add(reserve.get(index));
            else if ((index > 2) && (index < 6))
                line2.getChildren().add(reserve.get(index));
            else if (index > 5)
                line3.getChildren().add(reserve.get(index));
        }
        reserveBox.getChildren().addAll(line1,line2,line3);
    }

    public static void setUpCards(){
        for (int index=0; index <3; index++)
        {
            publicCards.getChildren().add(publicCardsArraylist.get(index));
            toolCards.getChildren().add(toolCardsArraylist.get(index));
        }

        cardBox.getChildren().addAll(publicCards,toolCards);
    }

    public static void setUpRoundScheme(){
        for(int index = 0; index<roundScheme.size(); index++)
        {
            roundSchemeBox.getChildren().add(roundScheme.get(index));
        }
    }

    public static void setUoMapReserve(){
        mapBoard.getChildren().addAll(reserveBox,map,privCard);
    }

    public static void totalSetUp(){
        setUpReserve();
        setUpCards();
        setUpRoundScheme();
        setUoMapReserve();
        board.getChildren().addAll(roundSchemeBox,cardBox,mapBoard, saltaTurno);
        stage.setScene(new Scene(board, 1280,720));
        stage.show();
    }


}
