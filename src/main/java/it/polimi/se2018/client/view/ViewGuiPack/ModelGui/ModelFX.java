package it.polimi.se2018.client.view.ViewGuiPack.ModelGui;

import it.polimi.se2018.client.view.ViewGuiPack.HostesPage.HostesModel;
import it.polimi.se2018.client.view.ViewGuiPack.LoginPage.ModelLogin;
import it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage.ModelChoiceMap;
import it.polimi.se2018.client.view.ViewGuiPack.gameBoardPage.ModelGameBoardPage;
import it.polimi.se2018.client.view.ViewGuiPack.roundSchemeStockPage.ModelRoundScheme;
import it.polimi.se2018.client.view.ViewGuiPack.wrongUsername.ModelWrong;

import java.util.ArrayList;

/**
 * Class that has all the model for FX class
 * @author Andrea Micucci
 */
public class ModelFX {

    ArrayList<String> Modelmaps;
    private ModelLogin login = new ModelLogin();
    private ModelWrong wrong = new ModelWrong();
    private ModelChoiceMap choice = new ModelChoiceMap();
    private ModelGameBoardPage gbp = new ModelGameBoardPage();
    private ModelRoundScheme mrs = new ModelRoundScheme();
    private HostesModel hostesModel = new HostesModel();

    
    private static ModelFX pippo;

    /**
     * get instance method for singleton pattern
     * @return return the object model fx
     */
    public static ModelFX getInstance(){
        if (pippo == null)
            pippo = new ModelFX();
        return pippo;
    }

    /**
     * empty class constructor
     */
    private ModelFX(){
         
    }


    /**
     * getter method for the login model
     * @return Model login
     */
    public ModelLogin getLogin() {
        return login;
    }

    /**
     * setter method for the Model Login
     * @param login model to be setted
     */
    public void setLogin(ModelLogin login) {
        this.login = login;
    }

    /**
     * getter method to get the model for wrong username given
     * @return the model of wrong Username FX page
     */
    public ModelWrong getWrong() {
        return wrong;
    }

    /**
     * setter method model of wrong FX window username
     * @param wrong model with all the data about the login
     */
    public void setWrong(ModelWrong wrong) {
        this.wrong = wrong;
    }

    /**
     * getter method for the Choice map model
     * @return the choice map model
     */
    public ModelChoiceMap getChoice() {
        return choice;
    }

    /**
     * getter method for the GameBoardPage model
     * @return a game board page model
     */
    public ModelGameBoardPage getGbp() {
        return gbp;
    }

    /**
     * setter method for the Game board page
     * @param gbp to be setted
     */
    public void setGbp(ModelGameBoardPage gbp) {
        this.gbp = gbp;
    }

    /**
     * getter method for the model of RoundScheme
     * @return aa model Round Scheme
     */
    public ModelRoundScheme getMrs() {
        return mrs;
    }

    /**
     * setter method for the model round scheme
     * @param mrs model round scheme
     */
    public void setMrs(ModelRoundScheme mrs) {
        this.mrs = mrs;
    }

    /**
     * getter method for the hostes model
     * @return an Hostes model
     */
    public HostesModel getHostesModel() {
        return hostesModel;
    }

    /**
     * setter method for hostes model
     * @param hostesModel to be setted
     */
    public void setHostesModel(HostesModel hostesModel) {
        this.hostesModel = hostesModel;
    }
}
