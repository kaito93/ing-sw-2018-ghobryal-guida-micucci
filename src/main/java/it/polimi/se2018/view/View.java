package it.polimi.se2018.view;

import it.polimi.se2018.model.Map;

import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public View(){
        // inizializza la view
    }

    public void startView(){
        // mostra la view
    }

    public String request(String message){
        System.out.println(message);
        String username = new Scanner(System.in).nextLine();
        return username;
    }

    public Map chooseMap(ArrayList<Map> maps){
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        return (maps.get(0));
    }
}
