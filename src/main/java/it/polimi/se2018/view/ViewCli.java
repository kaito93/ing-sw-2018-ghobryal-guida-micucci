package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;

import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.Message;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class ViewCli extends View {
    Scanner scanner = new Scanner(System.in);
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    @Override
    public void addLog(String message) {
        LOGGER.log(Level.INFO, message);
    }

    @Override
    public void myTurn(boolean posDice, boolean useTools) {
        boolean valid = false;
        int chooseDice=0;
        int chooseColumn=0;
        int chooseRow=0;
        int chooseTool=0;

        while (!valid) {

            // SELEZIONE MOSSA
            addLog("E' il tuo turno. Scegli che mossa fare: \n 1 - posizionare un dado dalla riserva alla tua carta schema " +
                    "\n 2 - Usare una carta utensile");
            String choose = scanner.nextLine();
            if (choose.equalsIgnoreCase("1")) {
                // SI VUOLE POSIZIONARE UN DADO
                if (posDice == false) {
                    valid = true;
                    boolean cho=false;
                    while (!cho){
                        // SELEZIONE DEL DADO
                        addLog("Dadi disponibili:");
                        printDices(gameStatus.getStock());
                        addLog("Quale dado vuoi posizionare?");
                         chooseDice = Integer.decode(scanner.nextLine());
                        if (chooseDice>gameStatus.getStock().size()-1|| chooseDice<0)
                            LOGGER.log(Level.WARNING, "Attenzione! Non hai selezionato un dado della lista");
                        else
                        {
                            boolean column=false;
                            while (!column){
                                // SELEZIONE DELLA COLONNA
                                printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
                                addLog("Seleziona la colonna ");
                                 chooseColumn = Integer.decode(scanner.nextLine());
                                if (chooseColumn>gameStatus.getCells().get(gameStatus.getYourIndex()).length || chooseColumn<0)
                                    LOGGER.log(Level.WARNING, "Attenzione! Non hai selezionato una colonna corretta");
                                else{
                                    column=true;
                                    // SELEZIONE DELLA RIGA
                                    boolean row=false;
                                    while(!row){
                                        addLog("Seleziona la riga");
                                         chooseRow = Integer.decode(scanner.nextLine());
                                        if (chooseRow>gameStatus.getCells().get(gameStatus.getYourIndex()).length || chooseRow<0)
                                            LOGGER.log(Level.WARNING, "Attenzione! Non hai selezionato una riga corretta");
                                        else{
                                            row=true;
                                        }
                                    }
                                }
                            }
                            boolean finals=false;
                            while (!finals){
                                addLog("Confermi di voler inserire il dado: Colore: "+gameStatus.getStock().get(chooseDice).getColor().toString() +
                                        " e valore: "+ gameStatus.getStock().get(chooseDice).getValue() + "in colonna " +chooseColumn +
                                        "e riga " +chooseRow + " ? [Si/No]" );
                                String finalChoose = scanner.nextLine();
                                if (finalChoose.equalsIgnoreCase("si")) {
                                    finals = true;
                                    cho=true;
                                    client.sendPosDice(gameStatus.getStock().get(chooseDice),chooseColumn,chooseRow);
                                }
                                else
                                    if (finalChoose.equalsIgnoreCase("no")){
                                        finals=true;
                                    }

                            }

                        }
                    }

                } else
                    LOGGER.log(Level.WARNING, "Attenzione! Hai già posizionato un dado in precedenza");


            }
            if (choose.equalsIgnoreCase("2")) {
                if (!useTools) {
                    // IL GIOCATORE HA SCELTO DI USARE UNA CARTA TOOLS
                    boolean tool=false;
                    while (!tool){
                        if (gameStatus.getFavor()>0){
                            addLog("Carte utensili utilizzabili:");
                            printTools();
                            addLog("Quali delle carte utensili vuoi usare?");
                            chooseTool = Integer.decode(scanner.nextLine());
                            if (chooseTool>gameStatus.getUseTools().size()||chooseTool<0)
                                LOGGER.log(Level.WARNING, "Non hai selezionato una carta utensile corretta");
                            else{
                                valid = true;
                                tool=true;
                                client.sendUseTool(gameStatus.getTitleTools().get(chooseTool));
                            }

                        }
                        else{
                            LOGGER.log(Level.WARNING, "Hai 0 segnalini favore!");
                            tool=true;
                        }


                    }



                } else
                    LOGGER.log(Level.WARNING, "Attenzione! Hai già usato una carta utensile in precedenza");
            }
            if (!valid)
                LOGGER.log(Level.WARNING, "Attenzione! Non hai selezionato una valida mossa");
        }


    }

    public Cell[][] chooseMap(ArrayList<Cell[][]> maps, String username) {
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        gameStatus.setMyUsername(username);
        return (maps.get(0));
    }

    public void printDices(ArrayList<Dice> dices){
        for (int i=0; i<dices.size();i++){
            System.out.println(i + " - Dado " +dices.get(i).getColor().toString() + " - Valore: "+dices.get(i).getValue());
        }
    }

    public void printTools (){
        for (int i=0; i<gameStatus.getUseTools().size();i++){
            System.out.println(i + " - Titolo: " + gameStatus.getTitleTools().get(i));
            System.out.println("Descrizione: " + gameStatus.getDescriptionTools().get(i));
            System.out.println("Usata in precedenza: " + gameStatus.getUseTools().get(i).toString());
        }
    }

    public void printSchemeMap (Cell[][] cells ){

        // esamino le righe
        for (int i=0; i<cells.length;i++){
            // esamino le colonne
            System.out.println("");
            for (int j=0; j<cells[i].length; j++){
                // scrivo il numero della colonna
                System.out.print("|       " + j);
            }
            for (int j=0; j<cells[i].length; j++){
                    if (cells[i][j].getDice() != null) {
                        // SE LA CASELLA HA GIA' UN DADO, STAMPA LE CARATTERISTICHE DEL DADO
                        System.out.print(" | Colore: " + cells[i][j].getDice().getColor() + ", Valore:" + cells[i][j].getDice().getValue());
                    }
                    else{
                        // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                        System.out.print(" | Colore: " + cells[i][j].getColor().toString() + ", Valore:" + cells[i][j].getValue());
                    }

            }
            // mostra il numero di riga
            System.out.println(i);
        }

    }

    @Override
    public void startView() {

        client.setUsername(askNewUsername());
    }

    @Override
    public String askNewUsername() {
        addLog("Inserisci il tuo username:");
        return scanner.nextLine();
    }
}
