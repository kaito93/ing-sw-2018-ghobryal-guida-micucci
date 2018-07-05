package it.polimi.se2018.client.view.ViewGuiPack;

import java.net.MalformedURLException;
import java.net.URL;

public class pathFXML {

    private final String loginFXML = "src/main/resources/LoginPageFXML.fxml";
    private final String gamingBoard = "/it/polimi/se2018/client/view/ViewGuiPack/gameBoardPage/gamingBoard.fxml";
    private final String mapChoice ="/it/polimi/se2018/client/view/ViewGuiPack/MapChoicePage/SceltaMappe.fxml";
    private final String wrongUsername = "/it/polimi/se2018/client/view/ViewGuiPack/wrongUsername/wrongUsername.fxml";

    public String getGamingBoard() {
        return gamingBoard;
    }

    public String getLoginFXML() {
        return loginFXML;
    }

    public String getMapChoice() {
        return mapChoice;
    }

    public String getWrongUsername() {
        return wrongUsername;
    }
    
}
