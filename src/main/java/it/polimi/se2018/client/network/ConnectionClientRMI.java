package it.polimi.se2018.client.network;


import it.polimi.se2018.client.view.View;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.server.model.cards.PrivateObjectiveCard;
import it.polimi.se2018.server.model.cards.PublicObjectiveCard;
import it.polimi.se2018.server.model.cards.ToolCard;
import it.polimi.se2018.server.network.ConnectionServer;
import it.polimi.se2018.shared.Logger;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.model_shared.Dice;
import it.polimi.se2018.shared.model_shared.RoundSchemeCell;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Class ConnectionClientRMI
 * @author Anton
 */

public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient,Serializable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    private transient ConnectionServer skeleton;

    private transient View view;
    private transient String username;

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
            LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
        }
    }

    public void receiveUpdate(List<String> users, List<Cell[][]> cells, List<Boolean> useTools,
                              RoundSchemeCell[] roundSchemeMap, List<Dice> stock, List<Integer> favors){
        view.updateUsers(users, cells, useTools, roundSchemeMap, stock, favors);
    }

    public void tapWheel(){
        List<Object> obj = view.manageTap();
        //come dovrei settare quello che c'è dentro alla lista di oggetti a useTool
        //posso usare createMessageTap che si usa Vview ma non saprei se è la corretta procedura
        //aspetto spiegazioni di samu
    }

    @Override
    public void sendPosDice(Dice dice, int column, int row) {

    }

    @Override
    public void sendUseTool(String titleCardTool) {

    }

    @Override
    public void sendPassMove() {

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
