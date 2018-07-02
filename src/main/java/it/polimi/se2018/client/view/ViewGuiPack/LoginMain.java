package it.polimi.se2018.client.view.ViewGuiPack;

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
    private static String IP;
    private static int PORT;


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
        Label port = new Label("Porta");
        Label ip = new Label("IP");
        TextField portField = new TextField(String.valueOf(PORT));
        TextField ipField = new TextField(IP);

        HBox horizontalUsername = new HBox(100);
        HBox horizontalIP = new HBox(100);
        horizontalIP.getChildren().addAll(ip,ipField);
        HBox horizontalPORT = new HBox(90);
        horizontalPORT.getChildren().addAll(port, portField);

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

                System.out.println(ipField.getText());

                assignValue(tmpUser, tmpConnections, tmpUI, ipField.getText(), portField.getText());
                toChoiceMapStage(primaryStage);
            } else {
                try {
                    this.start(primaryStage);
                } catch (Exception e1) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
        });


        horizontalConnections.getChildren().addAll(labelConnection, radioSocket, radioRMI);

        vertical.getChildren().addAll(horizontalUsername, horizontalUI, horizontalConnections, horizontalIP, horizontalPORT, invio);
        scene = new Scene(vertical, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void assignValue(String utente, String connessioni, String ui, String ipText, String portFieldText) {
        username = utente;
        connections = connessioni;
        uint = ui;
        IP = ipText;
        PORT = Integer.decode(portFieldText);

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

    public static void setIp(String IpAddress){
        IP = IpAddress;
    }

    public static void setPort(int porta){
        PORT = porta;
    }

    public static String getIp() {
        return IP;
    }

    public static int getPort() {
        return PORT;
    }

    public static void toChoiceMapStage(Stage stage){
        stage.setTitle("Scelta delle mappe di Sagrada");
        MapChoiceScene scelta = new MapChoiceScene(stage);
        Scene scena = MapChoiceScene.totalSetUp();
        stage.setScene(scena);
        stage.show();
    }
}

