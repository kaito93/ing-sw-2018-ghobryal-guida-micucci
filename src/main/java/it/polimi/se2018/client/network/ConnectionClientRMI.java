package it.polimi.se2018.client.network;


import it.polimi.se2018.client.view.View;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.network.ConnectionServer;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;

/**
 * Class ConnectionClientRMI
 * @author Anton
 */

public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient,Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static final String REMOTEERROR = "Errore di connessione: {0} !";

    private transient ConnectionServer skeleton;

    private transient View view;
    private transient String username;

    private Dice a;

    public ConnectionClientRMI(View view, String username) throws RemoteException {
        super();
        this.view=view;
        this.username=username;
    }


    @Override
    public void run() {
        //non lo uso per RMI
    }

    public void setSkeleton(ConnectionServer skeleton) {
        this.skeleton = skeleton;
    }

    public int receiveMapConn(List<Cell[][]> cells, List<String> names, List<Integer> fav){
        Cell[][] mapPlayer = view.chooseMap(cells, username, names, fav);
        return cells.indexOf(mapPlayer);
    }

    public void viewPrivateCard(PrivateObjectiveCard privateObjectiveCard){
        view.setPrivateInformation(privateObjectiveCard.getTitle(), privateObjectiveCard.getDescription());
    }

    public void viewPublicInformation(List<String> titlePublic, List<String> descriptionPublic, List<String> titleTool, List<String> descriptionTool, List<Integer> publicScore){
        view.setPublicInformation(titlePublic, descriptionPublic, titleTool, descriptionTool, publicScore);
    }

    public void viewScore(List<Integer> finalScore){
        view.seeScore(finalScore);
    }

    public void isTurn(boolean dice, boolean tool){
        view.updateFavor(dice, tool);
        view.turn();
    }

    public void requestNewUsernameRMI() {
        ConnectionClientRMI newClient = null;
        try {
            newClient = new ConnectionClientRMI(view, username);
            newClient.setUsername(view.askNewUsername());
            newClient.setSkeleton(skeleton);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void receiveUpdate(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                              RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors){
        view.updateUsers(users, cells, useTools, roundSchemeMap, stock, favors);
    }

    public void tapWheel(String title){
        List<Object> obj = view.manageTap();
        try {
            skeleton.tapSet(title, (Dice) obj.get(0), (List<Dice>) obj.get(9), (int) obj.get(1), (int) obj.get(5),
                    (int) obj.get(2), (int) obj.get(6), (int) obj.get(3), (int)obj.get(7), (int)obj.get(4),
                    (int)obj.get(8), (int)obj.get(10));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void copperFoil(String title){
        List<Object> obj = view.manageCE();
        try {
            skeleton.coppperSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2), (int) obj.get(3), (int) obj.get(4));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void corkbacked(String title){
        List<Object> obj = view.manageCork();
        try {
            skeleton.corkSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void eglomise(String title){
        List<Object> obj = view.manageCE();
        try {
            skeleton.eglomiseSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2), (int) obj.get(3), (int) obj.get(4));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void fluxBrush(String title){
        List<Object> obj = view.managefluxBrush();
        try {
            skeleton.fluxBrushSet(title, (Dice) obj.get(1), (int) obj.get(2), (int) obj.get(3), (Dice) obj.get(0));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void grinding(String title){
        List<Object> obj = view.manageGrinding();
        try {
            skeleton.grindingSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2), (Dice) obj.get(3));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void grozing(String title){
        List<Object> obj = view.manageGrozing();
        try {
            skeleton.grozingSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void lathekin(String title){
        List<Object> obj = view.manageLathekin();
        try {
            skeleton.lathekinSet(title, (int) obj.get(0), (int) obj.get(4), (int) obj.get(1), (int) obj.get(5),
                    (int) obj.get(2), (int) obj.get(3), (List<Dice>) obj.get(8), (int) obj.get(6), (int) obj.get(7));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void lens(String title){
        List<Object> obj = view.manageLens();
        try {
            skeleton.lensSet(title, (Dice) obj.get(0), (int) obj.get(2), (int) obj.get(3), (int) obj.get(4), (Dice) obj.get(1));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void running(String title){
        List<Object> obj = view.manageCork();
        try {
            skeleton.runningSet(title, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    public void fluxRemover(String title, boolean firstMessage){
        if(!firstMessage){
            a=view.managefluxRemove();
            try {
                skeleton.manageFluxRemover2(a, title);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
            }
        }else {
            List<Object> obj = view.manageFluxRemove2(a);
            try {
                skeleton.fluxRemoverSet(title, true, (Dice) obj.get(0), (int) obj.get(1), (int) obj.get(2));
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
            }
        }
    }

    public void handleError(String error){
        view.manageError(error);
    }

    @Override
    public void sendPosDice(Dice dice, int column, int row) {
        try {
            skeleton.posDice(dice, column, row);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendUseTool(String titleCardTool) {
        try {
            skeleton.useTool(titleCardTool);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendPassMove() {
        try {
            skeleton.passTurn();
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, REMOTEERROR, e.getMessage());
        }
    }

    @Override
    public void sendReconnect() {

    }

    @Override
    public void sendDisconnect() {

    }

    /**
     * method that set the username of this player
     * @param username username's player
     */
    public void setUsername(String username){
        this.username= username;
    }

    public String getUsername() {
        return username;
    }

    public View getView() {
        return view;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
