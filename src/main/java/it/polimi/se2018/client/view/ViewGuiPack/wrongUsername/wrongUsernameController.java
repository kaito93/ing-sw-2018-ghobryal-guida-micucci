package it.polimi.se2018.client.view.ViewGuiPack.wrongUsername;

import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * class to manage the behaviour of the Wrong Username FX window
 * @author Andrea Micucci
 */
public class wrongUsernameController {
    @FXML
    private TextField rightUsername;

    /**
     * method that send the new username to the model
     * @param event a clicked button
     */
    public void newUsername(ActionEvent event) {
        if (!rightUsername.getText().isEmpty()) {
            ModelFX.getInstance().getWrong().setNewUsername(rightUsername.getText());
            System.out.println(ModelFX.getInstance().getWrong().getNewUsername());
            try {
                ((Node) event.getSource()).getScene().getWindow().hide();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
