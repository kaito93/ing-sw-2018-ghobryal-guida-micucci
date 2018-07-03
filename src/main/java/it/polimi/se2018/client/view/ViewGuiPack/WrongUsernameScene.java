package it.polimi.se2018.client.view.ViewGuiPack;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WrongUsernameScene {

    private static Stage stage;
    private static TextField newUsernameField;
    private static Label newUsernameLabel;
    private static Button bottone;
    private static Scene scene;
    private static String username;
    private static VBox vertical = new VBox(10);

    public static void displayScene(Stage primaryStage){
        stage = primaryStage;
        newUsernameLabel = new Label("Lo username che mi hai dato è già presente, inseriscine un altro");
        newUsernameField = new TextField();
        bottone = new Button("Invia");
        bottone.setOnAction(e -> {
            if (newUsernameField != null){
                setUsername(newUsernameField.getText());
                stage.close();
            }
        });
        vertical.getChildren().addAll(newUsernameLabel, newUsernameField, bottone);
        scene = new Scene(vertical, 600,900);
        stage.setScene(scene);
        stage.show();
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        WrongUsernameScene.username = username;
    }
}
