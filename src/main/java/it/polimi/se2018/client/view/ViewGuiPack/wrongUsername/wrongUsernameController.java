package it.polimi.se2018.client.view.ViewGuiPack.wrongUsername;

import it.polimi.se2018.client.view.ViewGuiPack.ModelGui.ModelFX;
import it.polimi.se2018.shared.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.logging.Level;

/**
 * class to manage the behaviour of the Wrong Username FX window
 * @author Andrea Micucci
 */
public class wrongUsernameController {
    @FXML
    private TextField rightUsername;
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


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
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }
}
