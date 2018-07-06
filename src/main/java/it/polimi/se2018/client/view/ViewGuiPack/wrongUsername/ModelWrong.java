/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2018.client.view.ViewGuiPack.wrongUsername;

/**
 * model for the Wrong userame FX window
 * @author Andrea Micucci
 */
public class ModelWrong {
    
    String newUsername = null;

    /**
     * getter method for the new username of the player
     * @return a string
     */
    public String getNewUsername() {
        return newUsername;
    }

    /**
     * setter method for the new username of the player
     * @param newUsername to be setted
     */
    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
    
    
    
}