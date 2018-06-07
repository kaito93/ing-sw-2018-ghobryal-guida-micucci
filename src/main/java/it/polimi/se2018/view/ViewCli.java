package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;

import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.util.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class ViewCli extends View {

    Scanner scanner = new Scanner(System.in);
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    public ViewCli() {
        super();
    }

    @Override
    public void addLog(String message) {
        System.out.println("");
        Logger.information(message);
    }

    @Override
    public void myTurn(boolean posDice, boolean useTools) {
        boolean valid = false;
        boolean map = false;
        int chooseDice;
        int chooseColumn = 0;
        int chooseRow = 0;
        int chooseTool;

        while (!valid) {

            // SELEZIONE MOSSA
            addLog("E' il tuo turno. Scegli che mossa fare: \n 1 - posizionare un dado dalla riserva alla tua carta schema " +
                    "\n 2 - Usare una carta utensile \n 3 - Non fare niente in questa mossa \n 4 - Visualizza la tua mappa");
            String choose = scanner.nextLine();
            map=false;
            if (choose.equalsIgnoreCase("1")) {
                // SI VUOLE POSIZIONARE UN DADO
                if (!posDice) {
                    valid = true;
                    boolean cho = false;
                    while (!cho) {
                        // SELEZIONE DEL DADO
                        chooseDice = askDiceStock();
                        if (chooseDice > gameStatus.getStock().size() - 1 || chooseDice < 0)

                            addError("Non hai selezionato un dado della lista");
                        else {
                            ArrayList<Integer> inta = askRowColumn();
                            chooseRow = inta.get(0);
                            chooseColumn = inta.get(1);
                            boolean finals = false;
                            while (!finals) {
                                addLog("Confermi di voler inserire il dado: Colore: " + gameStatus.getStock().get(chooseDice).getColor().toString() +
                                        " e valore: " + gameStatus.getStock().get(chooseDice).getValue() + " in colonna " + (chooseColumn + 1) +
                                        " e riga " + (chooseRow + 1) + " ? [Si/No]");
                                String finalChoose = scanner.nextLine();
                                if (finalChoose.equalsIgnoreCase("si")) {
                                    finals = true;
                                    cho = true;
                                    client.sendPosDice(gameStatus.getStock().get(chooseDice), chooseColumn, chooseRow);
                                } else if (finalChoose.equalsIgnoreCase("no")) {
                                    finals = true;
                                }

                            }

                        }
                    }

                } else
                    addError("Hai già posizionato un dado in precedenza");


            }
            if (choose.equalsIgnoreCase("2")) {
                if (!useTools) {
                    // IL GIOCATORE HA SCELTO DI USARE UNA CARTA TOOLS
                    boolean tool = false;
                    while (!tool) {
                        if (gameStatus.getFavor() > 0) {
                            addLog("Carte utensili utilizzabili:");
                            printTools();
                            addLog("Quali delle carte utensili vuoi usare?");
                            chooseTool = Integer.decode(scanner.nextLine());
                            if (chooseTool > gameStatus.getUseTools().size() || chooseTool < 0)
                                addError("Non hai selezionato una carta utensile corretta");
                            else {
                                valid = true;
                                tool = true;
                                client.sendUseTool(gameStatus.getTitleTools().get(chooseTool));
                            }

                        } else {
                            addError("Hai 0 segnalini favore!");
                            tool = true;
                        }


                    }


                } else
                    addError("Hai già usato una carta utensile in precedenza");
            }
            if (choose.equalsIgnoreCase("3")) {
                addLog("Confermi di voler passare il turno? [Si/No]");
                String finalChoose = scanner.nextLine();
                if (finalChoose.equalsIgnoreCase("Si")) {
                    client.sendPassMove();
                    valid = true;
                }

            }

            if (choose.equalsIgnoreCase("4")) {
                printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
                map = true;
            }

            if (!map) {
                if (!valid)
                    addError("Non hai selezionato una valida mossa");
            }
        }


    }

    public Cell[][] chooseMap(ArrayList<Cell[][]> maps, String username) {
        // BISOGNERA' CHIEDERE ALL'UTENTE QUALE MAPPA SCEGLIERE
        gameStatus.setMyUsername(username);
        return (maps.get(0));
    }

    public void printDicesStock(ArrayList<Dice> dices) {
        for (int i = 0; i < dices.size(); i++) {
            System.out.print(i + 1 + " - ");
            printColor(dices.get(i).getColor().toString(), String.valueOf(dices.get(i).getValue()));
            System.out.println("");
        }
    }

    public void printTools() {
        for (int i = 0; i < gameStatus.getUseTools().size(); i++) {
            System.out.println(i + 1 + " - Titolo: " + gameStatus.getTitleTools().get(i));
            System.out.println("Descrizione: " + gameStatus.getDescriptionTools().get(i));
            System.out.println("Usata in precedenza: " + gameStatus.getUseTools().get(i).toString());
        }
    }

    public void printSchemeMap(Cell[][] cells) {
        System.out.print("  ");
        for (int j = 0; j < cells[0].length; j++) {
            // scrivo il numero della colonna
            System.out.print("| " + (j + 1) + " ");

        }
        System.out.println("");
        System.out.print("__");
        System.out.print(" ");
        for (int j = 0; j < cells[0].length; j++) {
            // scrivo il numero della colonna
            System.out.print(" _  ");

        }
        // esamino le righe
        for (int i = 0; i < cells.length; i++) {
            // mostra il numero di riga
            System.out.println("");
            System.out.print(i + 1);
            // esamino le colonne
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(" | ");
                if (cells[i][j].getDice() != null) {
                    // SE LA CASELLA HA GIA' UN DADO, STAMPA LE CARATTERISTICHE DEL DADO

                    printColor(cells[i][j].getDice().getColor().toString(), String.valueOf(cells[i][j].getDice().getValue()));
                    System.out.print(" ");
                } else {
                    // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                    printColor(cells[i][j].getColor().toString(), String.valueOf(cells[i][j].getValue()));
                }

            }

        }
        System.out.println("");

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

    @Override
    public void addError(String message) {
        Logger.error("ATTENZIONE - " + message);
    }

    @Override
    public ArrayList<Object> manageCCEFR() {
        // DIce, row, column
        return null;
    }

    @Override
    public ArrayList<Object> managefluxRemove() {
        //Dice dice, int value
        return null;
    }

    @Override
    public Dice manageGG() {
        //Dice
        return null;
    }


    public ArrayList<Object> manageLathekin() {
        //int row1,int column1, int row2, int column2, ArrayList<Dice> dices
        return null;
    }

    @Override
    public ArrayList<Dice> manageLens() {
        //Dice dice1,Dice dice2)
        return null;
    }

    @Override
    public ArrayList<Object> manageTap() {
        //Dice diceRound, Dice dice1, Dice dice2, int row1, int row2, int column1, int column2
        return null;
    }

    public int askDiceStock() {
        addLog("Dadi disponibili:");
        printDicesStock(gameStatus.getStock());
        addLog("Quale dado vuoi posizionare?");
        return Integer.decode(scanner.nextLine())-1;
    }

    public int askDiceMap() {
        addLog("La tua mappa:");
        printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
        addLog("Inserisci le cordinate del dado");
        ArrayList<Integer> inta = askRowColumn();

        return Integer.decode(scanner.nextLine());
    }

    public ArrayList<Integer> askRowColumn() {
        int chooseColumn = 0, chooseRow = 0;
        boolean column = false;
        while (!column) {
            // SELEZIONE DELLA COLONNA
            printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
            addLog("Seleziona la colonna ");
            chooseColumn = Integer.decode(scanner.nextLine()) - 1;
            if (chooseColumn > gameStatus.getCells().get(gameStatus.getYourIndex()).length || chooseColumn < 0)
                LOGGER.log(Level.WARNING, "Attenzione! Non hai selezionato una colonna corretta");
            else {
                column = true;
                // SELEZIONE DELLA RIGA
                boolean row = false;
                while (!row) {
                    addLog("Seleziona la riga");
                    chooseRow = Integer.decode(scanner.nextLine()) - 1;
                    if (chooseRow > gameStatus.getCells().get(gameStatus.getYourIndex()).length || chooseRow < 0)
                        LOGGER.log(Level.WARNING, "Attenzione! Non hai selezionato una riga corretta");
                    else {
                        row = true;
                    }
                }
            }
        }
        ArrayList<Integer> inta = new ArrayList<>();
        inta.add(chooseRow);
        inta.add(chooseColumn);
        return inta;
    }

    public void printColor(String color, String number) {
        switch (color) {
            case "red":
                Logger.red(number);
                break;
            case "green":
                Logger.green(number);
                break;
            case "blue":
                Logger.blue(number);
                break;
            case "yellow":
                Logger.yellow(number);
                break;
            case "purple":
                Logger.purple(number);
                break;
            case "null":
                System.out.print(number);
                break;
            default:
                break;
        }
    }
}
