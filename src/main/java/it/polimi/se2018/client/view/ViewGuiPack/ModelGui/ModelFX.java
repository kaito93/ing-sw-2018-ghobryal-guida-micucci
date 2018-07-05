package it.polimi.se2018.client.view.ViewGuiPack.ModelGui;

import it.polimi.se2018.client.view.ViewGuiPack.LoginPage.ModelLogin;
import it.polimi.se2018.client.view.ViewGuiPack.MapChoicePage.ModelChoiceMap;
import it.polimi.se2018.client.view.ViewGuiPack.wrongUsername.ModelWrong;

import java.util.ArrayList;

public class ModelFX {

    ArrayList<String> Modelmaps;
    private ModelLogin login = new ModelLogin();
    private ModelWrong wrong = new ModelWrong();
    private ModelChoiceMap choice = new ModelChoiceMap();

    
    private static ModelFX pippo;
    
    public static ModelFX getInstance(){
        if (pippo == null)
            pippo = new ModelFX();
        return pippo;
    }
    
    private ModelFX(){
         
    }


    public ModelLogin getLogin() {
        return login;
    }

    public void setLogin(ModelLogin login) {
        this.login = login;
    }

    public ModelWrong getWrong() {
        return wrong;
    }

    public void setWrong(ModelWrong wrong) {
        this.wrong = wrong;
    }
    
    

}
