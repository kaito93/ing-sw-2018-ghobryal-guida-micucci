/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.model;

import java.util.Random;

/**
 *
 * @author Andrea
 */
public class difficultNotValidException extends Exception {

    public difficultNotValidException(Map map) {
        super();
        Random random = new Random();
        map.difficultyLevel = random.nextInt(6) + 1;
    }
    
}
