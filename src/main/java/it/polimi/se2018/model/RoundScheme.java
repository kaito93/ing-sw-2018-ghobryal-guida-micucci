package it.polimi.se2018.model;

import java.util.ArrayList;

public class RoundScheme {
    int roundNum;
    ArrayList<Integer> roundScheme=new ArrayList <Integer>();

    private static RoundScheme ourInstance = new RoundScheme();

    public static RoundScheme getInstance() {
        return ourInstance;
    }

    private RoundScheme() {
    }
}

