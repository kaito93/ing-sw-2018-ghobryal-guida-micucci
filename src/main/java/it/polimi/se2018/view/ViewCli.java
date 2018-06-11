package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;

import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.util.Logger;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
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
            addLog("E' il tuo turno. Scegli che mossa fare: \n 1 - Posizionare un dado dalla riserva alla tua carta schema " +
                    "\n 2 - Usare una carta utensile \n 3 - Non fare niente in questa mossa \n 4 - Visualizza la tua mappa");
            String choose = scanner.nextLine();
            map = false;
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
                            chooseTool = Integer.decode(scanner.nextLine()) - 1;
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

            if (!map && !valid) {
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
            System.out.println("Usata in precedenza: " + (gameStatus.getUseTools().get(i) ? "Sì" : "No"));
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
    public ArrayList<Object> manageCER() {
        // Dice, RowDest, ColumnDect, rowMit, ColumnMit
        ArrayList<Object> obj = new ArrayList<>();
        ArrayList<Integer> obj2 = askDiceMap();
        addLog("Seleziona dove vuoi piazzare il dado scelto:");
        ArrayList<Integer> obj3 = askRowColumn();
        obj.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice());
        obj.add(obj3.get(0));
        obj.add(obj3.get(1));
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    @Override
    public Dice managefluxRemove() {
        //Dice dice
        return null;
    }

    @Override
    public Dice manageGrozing1() {
        // Dice
        printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
        return (gameStatus.getStock().get(askDiceStock()));
    }

    @Override
    public int manageGrozing2(int minus, int major) {
        addLog("Scegli il nuovo valore che deve avere il dado");
        if (minus == 0)
            return major;
        else if (major == 0)
            return minus;
        addLog(String.valueOf(minus) + " o " + String.valueOf(major) + " ?");
        return (Integer.decode(scanner.nextLine()));
    }

    public ArrayList<Integer> manageGrozing3() {
        ArrayList<Integer> obj = new ArrayList<>();
        addLog("Seleziona dove inserire il dado");
        return askRowColumn();
    }

    @Override
    public ArrayList<Object> manageGrinding() {
        return null;
    }

    public ArrayList<Object> manageLathekin() {
        //int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList<Dice> dices
        ArrayList<Object> obj = new ArrayList<>();
        ArrayList<Dice> dices = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            addLog("Scegli il " + (i + 1) + "° dado da riposizionare:");
            ArrayList<Integer> obj2 = askDiceMap();
            obj.add(obj2.get(0));
            obj.add(obj2.get(1));
            dices.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice());
            addLog("Scegli dove riposizionare il dado scelto:");
            ArrayList<Integer> obj3 = askDiceMap();
            obj.add(obj3.get(0));
            obj.add(obj3.get(1));
        }
        obj.add(dices);
        return obj;
    }

    @Override
    public ArrayList<Object> manageLens() {
        //Dice dicStock2,Dice diceRound, int numberRound
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(gameStatus.getStock().get(askDiceStock()));
        ArrayList<Object> obj2 = askDiceRound();
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    @Override
    public ArrayList<Object> manageTap() {
        //Dice diceRound, Arraylist Dice (dice1, Dice dice2), int row1, int row2, int column1, int column2
        return null;
    }

    @Override
    public ArrayList<Object> manageCork() {
        // Dice, row, column
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(gameStatus.getStock().get(askDiceStock()));
        addLog("Seleziona le coordinate dove posizionare il dado:");
        ArrayList<Integer> obj2 = askRowColumn();
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    public int askDiceStock() {
        addLog("Dadi disponibili:");
        printDicesStock(gameStatus.getStock());
        addLog("Quale dado vuoi posizionare?");
        return Integer.decode(scanner.nextLine()) - 1;
    }

    public ArrayList<Integer> askDiceMap() {
        addLog("La tua mappa:");
        addLog("Inserisci le cordinate:");
        return askRowColumn();
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

    public ArrayList<Object> askDiceRound() {
        ArrayList<Object> obj = new ArrayList<>();
        addLog("Seleziona il dado dallo schema dei round:");
        printSchemeRounds();
        addLog("Seleziona il round dal quale prendere il dado:");
        int round = Integer.decode(scanner.nextLine()) - 1;
        addLog("Seleziona il dado da questo round:");
        printSchemeRound(round);
        obj.add(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(Integer.decode(scanner.nextLine())));
        obj.add(round);
        return obj;
    }

    public void printSchemeRounds() {
        // cicla i round
        for (int round = 0; round < gameStatus.getRoundSchemeMap().length; round++) {
            // cicla i dadi all'interno di ogni round
            printSchemeRound(round);
        }
    }

    public void printSchemeRound(int round) {
        for (int dice = 0; dice < gameStatus.getRoundSchemeMap()[round].getRestOfStock().size(); dice++) {
            printColor(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(dice).getColor().toString(),
                    String.valueOf(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(dice).getValue()));
        }
    }

    @Override
    public ArrayList<Object> managefluxBrush() {
        // dice dicebefore, dice diceafter, int rowdest, int columndest
        ArrayList<Object> obj = new ArrayList<>();
        Dice diceBefore = gameStatus.getStock().get(askDiceStock());
        Dice diceAfter = null;
        try {
            diceAfter = diceBefore.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        diceAfter.throwDice();
        addLog("Ecco il risultato del lancio del dado precedentemente scelto: ");
        printColor(diceAfter.getColor().toString(), String.valueOf(diceAfter.getValue()));
        addLog("Scegli dove posizionare il dado: ");
        ArrayList<Integer> obj2 = askRowColumn();
        obj.add(diceBefore);
        obj.add(diceAfter);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }


    @Override
    public ArrayList<Object> manageFlueRemove2(Dice dice) {
        int valore = 0;
        boolean ok = false;
        while (!ok) {
            addLog("Hai estratto un dado di colore " + dice.getColor().toString() + "\n Scegli il valore da assegnare a questo dado:");
            valore = Integer.decode(scanner.nextLine());
            if (valore < 7 && valore > 0)
                ok = true;
            else
                addLog("Hai inserito un valore errato per questo dado");
        }
        try {
            dice.setValue(valore);
        } catch (InvalidValueException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        ArrayList<Integer> obj2 = askRowColumn();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }
}
