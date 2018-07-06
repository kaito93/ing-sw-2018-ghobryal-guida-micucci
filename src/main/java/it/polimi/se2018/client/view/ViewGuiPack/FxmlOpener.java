package it.polimi.se2018.client.view.ViewGuiPack;

import it.polimi.se2018.client.view.ViewGuiPack.LoginPage.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import it.polimi.se2018.client.view.ViewGuiPack.LoginPage.*;
import java.io.File;
import java.io.IOException;

import java.net.URL;
import javafx.embed.swing.JFXPanel;

/**
 * class used to load the FXML file for the
 * FX project
 * @author Andrea Micucci
 */
public class FxmlOpener {

    private static FxmlOpener fxmlOpener;

    /**
     * empty class constructor
     */
    private FxmlOpener() {
    }

    /**
     * get instance method (like singleton pattern)
     * @return an instance of the class
     */
    public static FxmlOpener getInstance() {
        if (fxmlOpener == null) {
            fxmlOpener = new FxmlOpener();
        }
        return fxmlOpener;
    }

    /**
     * method that open an FXML file from a path
     * @param path of the FXML file to be opened
     */
    public void openFX(String path) {
        URL location = FxmlOpener.class.getResource(path);
        JFXPanel dte = new JFXPanel();
        Platform.runLater(() -> {
            ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(FxmlOpener.class.getClassLoader());
            try {
                Parent root;
                Platform.setImplicitExit(false);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(location);
                fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
                root = (Parent) fxmlLoader.load(location.openStream());
                root.requestLayout();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                Thread.currentThread().setContextClassLoader(currentClassLoader);
            }
        });
    }
    
    
    
}
