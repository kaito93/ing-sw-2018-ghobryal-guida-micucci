package it.polimi.se2018.client.view.ViewGuiPack;

import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.shared.model_shared.Cell;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MapChoiceScene {

    private static VBox intMap1;
    private static VBox intMap2;
    private static VBox intMap3;
    private static VBox intMap4;
    static Stage stage;
    static Scene scenaScelta;
    static Button map1Button;
    static Button map2Button;
    static Button map3Button;
    static Button map4Button;
    static ArrayList<Rectangle> map1 = new ArrayList<>();
    static ArrayList<Rectangle> map2 = new ArrayList<>();
    static ArrayList<Rectangle> map3= new ArrayList<>();
    static ArrayList<Rectangle> map4 = new ArrayList<>();
    static VBox defMap1;
    static VBox defMap2;
    static VBox defMap3;
    static VBox defMap4;
    static int chosenMap;

    public MapChoiceScene(Stage primaryStage){
        stage = primaryStage;
        map1Button = new Button("map1");
        map1Button.setOnAction( e -> {
            setChosenMap(1);
        });
        map2Button = new Button("map2");
        map2Button.setOnAction( e -> {
            setChosenMap(2);
        });
        map3Button = new Button("map3");
        map3Button.setOnAction( e -> {
            setChosenMap(3);
        });
        map4Button = new Button("map4");
        map4Button.setOnAction( e -> {
            setChosenMap(4);
        });
        for(int index = 0; index<20; index++){
            map1.add(new Rectangle(50,50));
            map2.add(new Rectangle(50,50));
            map3.add(new Rectangle(50,50));
            map4.add(new Rectangle(50,50));
        }
    }

    public static VBox SetUpMap(ArrayList<Rectangle> tex){
        VBox map = new VBox(0);
        HBox line1 = new HBox(0);
        HBox line2 = new HBox(0);
        HBox line3 = new HBox(0);
        HBox line4 = new HBox(0);
        int index;
        for (index = 0; index < 20; index++)        //ordinamento arraylist mappa
        {
            if(index<5)
                line1.getChildren().add(tex.get(index));
            else if ((4<index) && (index<10))
                line2.getChildren().add(tex.get(index));
            else if ((9<index) && (index<15))
                line3.getChildren().add(tex.get(index));
            else if ((14<index))
                line4.getChildren().add(tex.get(index));
        }
        map.getChildren().addAll(line1,line2,line3,line4);
        return map;
    }

    public static VBox setUpChoice(VBox kit, Button tiger){
        VBox definitiveMap = new VBox(20);
        definitiveMap.getChildren().addAll(tiger, kit);
        return definitiveMap;
    }

    public static VBox setUpScene(VBox firstmap, VBox secondmap, VBox thirdmap, VBox fourthmap){
        VBox total = new VBox(50);
        HBox coupleMap = new HBox(120);
        HBox coupleMap2 = new HBox(120);
        coupleMap.getChildren().addAll(firstmap,secondmap);
        coupleMap2.getChildren().addAll(thirdmap,fourthmap);
        total.getChildren().addAll(coupleMap,coupleMap2);
        return total;
    }

    public static Scene totalSetUp(){
        intMap1 = MapChoiceScene.SetUpMap(map1);
        intMap2 = MapChoiceScene.SetUpMap(map2);
        intMap3 = MapChoiceScene.SetUpMap(map3);
        intMap4 = MapChoiceScene.SetUpMap(map4);
        defMap1 = setUpChoice(intMap1,map1Button);
        defMap2 = setUpChoice(intMap2,map2Button);
        defMap3 = setUpChoice(intMap3,map3Button);
        defMap4 = setUpChoice(intMap4,map4Button);
        return scenaScelta = new Scene(MapChoiceScene.setUpScene(defMap1,defMap2,defMap3,defMap4), 600, 900);
    }


    public static void assignMap(List<Cell[][]> maps, List<String> names, List<Integer> fav){
        for (int rowindex = 0; rowindex<5; rowindex ++){
            for (int colindex = 0; colindex < 6; colindex++){
               // if (fromModel.getCell(rowindex,colindex).
            }
        }
    }

    public static void playingSceneTransfer(Stage stage, VBox chosenMap){
        new playingBoardScene(stage, chosenMap);
        playingBoardScene.totalSetUp();
    }

    public static void toChoiceMapStage(Stage sts){
        sts.setTitle("Scelta delle mappe di Sagrada");
        new MapChoiceScene(sts);
        Scene scena = MapChoiceScene.totalSetUp();
        sts.setScene(scena);
        sts.show();
    }

    public static void setChosenMap(int map){
        chosenMap = map;
        stage.close();
    }

    public static int getChosenMap() {
        return chosenMap;
    }
}
