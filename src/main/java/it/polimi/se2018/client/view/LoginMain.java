package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.logging.Level;

public class LoginMain extends Application {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static String username;
    private static String connections;
    private static String uint;


    public LoginMain() {
        super();

    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene;
        primaryStage.setTitle("Pagina iniziale Sagrada");
        TextField textUser;
        RadioButton radioSocket;
        RadioButton radioRMI;
        RadioButton radioCLI;
        RadioButton radioGUI;
        Label labelGui;
        Label labelConnection;

        HBox horizontalUsername = new HBox(100);

        HBox horizontalUI = new HBox(3);
        HBox horizontalConnections = new HBox(3);
        VBox vertical = new VBox(10);
        vertical.setPadding(new Insets(20, 20, 20, 20));
        Label labelUsername = new Label("Username:");
        textUser = new TextField();
        horizontalUsername.getChildren().addAll(labelUsername, textUser);

        ToggleGroup ui = new ToggleGroup();
        labelGui = new Label("quale UI vuoi scegliere?");
        radioCLI = new RadioButton("CLI");
        radioCLI.setToggleGroup(ui);
        radioGUI = new RadioButton("GUI");
        radioGUI.setToggleGroup(ui);

        horizontalUI.getChildren().addAll(labelGui, radioCLI, radioGUI);

        ToggleGroup connectionsToggle = new ToggleGroup();
        labelConnection = new Label("quale connessione vuoi scegliere?");
        radioSocket = new RadioButton("Socket");
        radioSocket.setToggleGroup(connectionsToggle);
        radioRMI = new RadioButton("RMI");
        radioRMI.setToggleGroup(connectionsToggle);

        Button invio = new Button("invio");
        invio.setOnAction(e -> {
            if ((!textUser.getText().isEmpty())) {
                String tmpUser = textUser.getText();
                String tmpUI;
                String tmpConnections;
                if (radioCLI.isSelected())
                    tmpUI = radioCLI.getText();
                else
                    tmpUI = radioGUI.getText();


                if (radioSocket.isSelected())
                    tmpConnections = radioSocket.getText();
                else
                    tmpConnections = radioRMI.getText();

                assignValue(tmpUser, tmpConnections, tmpUI);
                Platform.exit();
            } else {
                try {
                    this.start(primaryStage);
                } catch (Exception e1) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
        });


        horizontalConnections.getChildren().addAll(labelConnection, radioSocket, radioRMI);

        vertical.getChildren().addAll(horizontalUsername, horizontalUI, horizontalConnections, invio);
        scene = new Scene(vertical, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void assignValue(String utente, String connessioni, String ui) {
        username = utente;
        connections = connessioni;
        uint = ui;

    }

    public static String getUsername() {
        return username;
    }

    public static String getConnections() {
        return connections;
    }

    public static String getUint() {
        return uint;
    }

}

