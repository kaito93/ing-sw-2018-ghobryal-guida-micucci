package it.polimi.se2018.client.view.ViewGuiPack.LoginPage;

import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class LoginController {

    @FXML
    private Button Login;

    @FXML
    private TextField username;

    @FXML
    private TextField port;

    @FXML
    private TextField ip;

    @FXML
    private RadioButton socket;

    @FXML
    private ToggleGroup Connection;

    @FXML
    private RadioButton rmi;


    @FXML
    void loginAction(ActionEvent event) {
        if (!((username.getText().isEmpty()) && (ip.getText().isEmpty()) && (port.getText().isEmpty()) && (Connection.getSelectedToggle().isSelected() != true))) {
            ModelFX.getInstance().getLogin().setUsername(username.getText());
            ModelFX.getInstance().getLogin().setiP(ip.getText());
            ModelFX.getInstance().getLogin().setPort(Integer.decode(port.getText()));
            if(socket.isSelected())
                ModelFX.getInstance().getLogin().setConnectivity("socket");
            else
                ModelFX.getInstance().getLogin().setConnectivity("RMI");
            System.out.println(ModelFX.getInstance().getLogin().getConnectivity());
            try {
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
    }

    @FXML
    void initialize() {
        port.setText(String.valueOf(ModelFX.getInstance().getLogin().getPort()));
        ip.setText(ModelFX.getInstance().getLogin().getiP());
        socket.setToggleGroup(Connection);
        rmi.setToggleGroup(Connection);
    }

}
