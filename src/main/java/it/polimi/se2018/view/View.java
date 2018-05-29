package it.polimi.se2018.view;

import it.polimi.se2018.model.Map;
import it.polimi.se2018.util.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class View {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());


    public View(){
        // inizializza la view
    }

    public void startView(){
        // mostra la view
    }

    public String request(String message){
        LOGGER.log(Level.INFO,message);

        return new Scanner(System.in).nextLine();
    }

    public Map chooseMap(ArrayList<Map> maps){
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        return (maps.get(0));
    }
}
