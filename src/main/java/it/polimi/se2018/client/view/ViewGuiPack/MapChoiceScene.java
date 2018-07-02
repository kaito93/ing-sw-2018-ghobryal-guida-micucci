package it.polimi.se2018.client.view.ViewGuiPack;

import com.sun.org.apache.xpath.internal.SourceTree;
import it.polimi.se2018.server.model.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MapChoiceScene {

    private static VBox intMap1;
    private static VBox intMap2;
    private static VBox intMap3;
    private static VBox intMap4;
    Stage stage;
    static Scene scenaScelta;
    static RadioButton map1Button;
    static RadioButton map2Button;
    static RadioButton map3Button;
    static RadioButton map4Button;
    ToggleGroup maps;
    static Button scelto;
    static ArrayList<Rectangle> map1 = new ArrayList<>();
    static ArrayList<Rectangle> map2 = new ArrayList<>();
    static ArrayList<Rectangle> map3= new ArrayList<>();
    static ArrayList<Rectangle> map4 = new ArrayList<>();
    static VBox defMap1;
    static VBox defMap2;
    static VBox defMap3;
    static VBox defMap4;

    public MapChoiceScene(Stage primaryStage){
        stage = primaryStage;
        scelto = new Button("Scelto!");
        maps = new ToggleGroup();
        map1Button = new RadioButton("map1");
        map1Button.setToggleGroup(maps);
        map2Button = new RadioButton("map2");
        map2Button.setToggleGroup(maps);
        map3Button = new RadioButton("map3");
        map3Button.setToggleGroup(maps);
        map4Button = new RadioButton("map4");
        map4Button.setToggleGroup(maps);
        for(int index = 0; index<20; index++){
            map1.add(new Rectangle(50,50));
            map2.add(new Rectangle(50,50));
            map3.add(new Rectangle(50,50));
            map4.add(new Rectangle(50,50));
        }
        scelto.setOnAction( e ->{
            System.out.println("hai scelto la mappa");
            Platform.exit();
        });
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

    public static VBox setUpChoice(VBox kit, RadioButton tiger){
        VBox definitiveMap = new VBox(20);
        definitiveMap.getChildren().addAll(tiger, kit);
        return definitiveMap;
    }

    public static VBox SetUpScene(VBox firstmap, VBox secondmap, VBox thirdmap, VBox fourthmap){
        VBox total = new VBox(50);
        HBox coupleMap = new HBox(120);
        HBox coupleMap2 = new HBox(120);
        coupleMap.getChildren().addAll(firstmap,secondmap);
        coupleMap2.getChildren().addAll(thirdmap,fourthmap);
        total.getChildren().addAll(coupleMap,coupleMap2, scelto);
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
        return scenaScelta = new Scene(MapChoiceScene.SetUpScene(defMap1,defMap2,defMap3,defMap4), 600, 900);
    }


    public static void AssignMap1(Map fromModel, RadioButton radio, ArrayList<Rectangle> yama){
        for (int rowindex = 0; rowindex<5; rowindex ++){
            for (int colindex = 0; colindex < 6; colindex++){
               // if (fromModel.getCell(rowindex,colindex).
            }
        }
    }

    public static void playingSceneTransfer(Stage stage, ArrayList<Rectangle> chosenMap){

    }
}
