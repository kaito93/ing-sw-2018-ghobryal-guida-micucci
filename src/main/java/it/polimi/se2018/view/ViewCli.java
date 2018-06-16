package it.polimi.se2018.view;

import it.polimi.se2018.model.Dice;

import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.InvalidValueException;
import it.polimi.se2018.network.client.message.Message;
import it.polimi.se2018.util.Logger;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

public class ViewCli extends View {

    Scanner scanner = new Scanner(System.in);
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());

    public ViewCli(int timer) {
        super(timer);
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
        try {
            while (!valid) {

                if (scanner!=null){
                    // SELEZIONE MOSSA
                    addLog(" ");
                    printBold("E' il tuo turno. Scegli che mossa fare: ");
                    addLog("1 - Posizionare un dado dalla riserva alla tua carta schema " +
                            "\n 2 - Usare una carta utensile \n 3 - Non fare niente in questa mossa \n 4 - Visualizza il tuo stato" +
                            "\n 5 - Visualizza lo stato degli avversari" + "\n 6 - Visualizza le informazioni generali della partita");
                    String choose = scanner.nextLine();
                    map = false;
                    addLog(" ");
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
                                            a = true;
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
                                if (gameStatus.getFavUser().get(gameStatus.getYourIndex()) > 0) {
                                    printBold("Carte utensili utilizzabili:");
                                    printTools();
                                    printBold("Quali delle carte utensili vuoi usare?");
                                    chooseTool = Integer.decode(scanner.nextLine()) - 1;
                                    if (chooseTool > gameStatus.getUseTools().size() || chooseTool < 0)
                                        addError("Non hai selezionato una carta utensile corretta");
                                    else {
                                        valid = true;
                                        tool = true;
                                        client.sendUseTool(gameStatus.getTitleTools().get(chooseTool));
                                        a = true;
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
                        printBold("Confermi di voler passare il turno? [Si/No]");
                        String finalChoose = scanner.nextLine();
                        if (finalChoose.equalsIgnoreCase("Si")) {
                            client.sendPassMove();
                            a = true;
                            valid = true;
                        }

                    }

                    if (choose.equalsIgnoreCase("4")) {
                        printBold("Carta obiettivo privata: ");
                        printCardPrivate(gameStatus.getTitlePrivateObjective(),gameStatus.getDescriptionPrivateObjective());
                        addLog(" ");
                        printBold("Ecco la tua mappa: ");
                        printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
                        addLog(" ");
                        printBold("Segnalini favore rimanenti: " + String.valueOf(gameStatus.getFavUser().get(gameStatus.getYourIndex())));
                        map = true;
                    }

                    if (choose.equalsIgnoreCase("5")) {

                        for (int i = 0; i < gameStatus.getCells().size(); i++) {
                            if (i == gameStatus.getYourIndex())
                                continue;
                            else {
                                addLog(" ");
                                printBold("Giocatore " + (i + 1) + " : " + gameStatus.getUsers().get(i));
                                addLog("Punti favore rimanenti: " + String.valueOf(gameStatus.getFavUser().get(i)));
                                printSchemeMap(gameStatus.getCells().get(i));
                            }
                        }
                        addLog(" ");
                        map = true;
                    }
                    if (choose.equalsIgnoreCase("6")) {
                        printBold("Carte obiettivo pubbliche: ");
                        for (int i=0;i<gameStatus.getTitlePublicObjective().size();i++){
                            addLog("");
                            printCors("Carta obiettivo pubblica "+ String.valueOf(i+1)+ ":");
                            printCardPublic(gameStatus.getTitlePublicObjective().get(i),gameStatus.getDescriptionPublicObjective().get(i)
                                    , gameStatus.getScorePublicObjective().get(i));
                        }
                        addLog(" ");
                        printBold("Schema dei round attuale:");
                        printSchemeRounds();
                        addLog(" ");
                        printBold("Riserva attuale: ");
                        printDicesStock();
                        addLog(" ");



                        map = true;
                    }

                    if (!map && !valid && !isA()) {

                        addError("Non hai selezionato una valida mossa");
                    }
                }
                else
                    valid=true;

            }

        }
        catch (IndexOutOfBoundsException | NoSuchElementException|IllegalStateException|NullPointerException e){
            // se si entra qui dentro è perchè il giocatore prima si era disconnesso.
            addLog("Chiusura mossa precedente");
        }
    }

    public String doString(String nome) {
        StringBuilder sb = new StringBuilder(19);
        sb.append(nome);
        for (int i = sb.length(); i < sb.capacity(); i++)
            sb.append(" ");
        return sb.toString();
    }

    public int chooseSingleMap(ArrayList<Cell[][]> maps, ArrayList<String> names, ArrayList<Integer> fav) {


        int val = 9;
        while ((val > maps.size()) || (val < 1)) {
            System.out.println();
            for (int i = 0; i < names.size(); i++) {
                System.out.print("      Mappa " + (i + 1) + "                                      ");
            }
            System.out.println();
            for (int i = 0; i < names.size(); i++) {

                System.out.print("Nome: " + (doString(names.get(i))) + "                         ");
            }

            System.out.println("  ");
            System.out.print("  ");

            for (int j = 0; j < maps.get(0)[0].length; j++) {
                // scrivo il numero della colonna
                System.out.print("| " + (j + 1) + " ");
            }
            System.out.print("                             ");
            for (int j = 0; j < maps.get(1)[0].length; j++) {
                // scrivo il numero della colonna
                System.out.print("| " + (j + 1) + " ");
            }

            System.out.println("");
            System.out.print("__");
            System.out.print(" ");
            for (int j = 0; j < maps.get(0)[0].length; j++) {
                // scrivo il numero della colonna
                System.out.print(" _  ");

            }
            System.out.print("                          ");
            System.out.print("__ ");
            for (int j = 0; j < maps.get(0)[0].length; j++) {
                // scrivo il numero della colonna
                System.out.print(" _  ");
            }

            // esamino le righe
            for (int i = 0; i < maps.get(0).length; i++) {
                // mostra il numero di riga
                System.out.println("");
                System.out.print(i + 1);
                // esamino le colonne
                for (int j = 0; j < maps.get(0)[i].length; j++) {
                    System.out.print(" | ");
                    if (maps.get(0)[i][j].getDice() != null) {
                        // SE LA CASELLA HA GIA' UN DADO, STAMPA LE CARATTERISTICHE DEL DADO

                        printColor(maps.get(0)[i][j].getDice().getColor().toString(), maps.get(0)[i][j].getDice().toString());
                    } else {
                        // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                        printColor(maps.get(0)[i][j].getColor().toString(), maps.get(0)[i][j].toString());
                    }

                }

                System.out.print("                            ");
                System.out.print(i + 1);
                for (int j = 0; j < maps.get(1)[i].length; j++) {
                    System.out.print(" | ");
                    if (maps.get(1)[i][j].getDice() != null) {
                        // SE LA CASELLA HA GIA' UN DADO, STAMPA LE CARATTERISTICHE DEL DADO

                        printColor(maps.get(1)[i][j].getDice().getColor().toString(), maps.get(1)[i][j].getDice().toString());
                    } else {
                        // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                        printColor(maps.get(1)[i][j].getColor().toString(), maps.get(1)[i][j].toString());
                    }

                }

            }
            System.out.println();
            System.out.println();
            for (int i = 0; i < fav.size(); i++) {
                System.out.print("    Difficoltà: " + String.valueOf(fav.get(i)) + "                                ");
            }

            System.out.println();
            System.out.println();
            System.out.println("Quale mappa scegli?");
            val = Integer.decode(scanner.nextLine());
            if (val > (maps.size()) || val < 1) {
                addError("Hai inserito un valore errato");
            }
        }


        return val - 1;
    }

    public void printDicesStock() {
        for (int i = 0; i < gameStatus.getStock().size(); i++) {
            System.out.print((i + 1) + " - ");
            printColor(gameStatus.getStock().get(i).getColor().toString(), String.valueOf(gameStatus.getStock().get(i).getValue()));
            System.out.println("");
        }
    }

    public void printTools() {
        for (int i = 0; i < gameStatus.getUseTools().size(); i++) {
            printCors(i + 1 + " - Titolo: " + gameStatus.getTitleTools().get(i));
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

                    printColor(cells[i][j].getDice().getColor().toString(), cells[i][j].getDice().toString());
                } else {
                    // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                    printColor(cells[i][j].getColor().toString(), cells[i][j].toString());
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
        boolean sec = false;
        String user="";
        while(sec){
            addLog("Inserisci il tuo username:");
            user = scanner.nextLine();
            if (user!="")
                sec=true;
            else
                addLog("Non puoi inserire un username vuoto!");
        }
        return user;


    }

    @Override
    public void addError(String message) {
        Logger.error("ATTENZIONE - " + message);
    }

    @Override
    public ArrayList<Object> manageCE() {
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


    public ArrayList<Object> manageLathekin() {
        //int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList<Dice> dices
        ArrayList<Object> obj = new ArrayList<>();
        ArrayList<Dice> dices = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            addLog("Scegli il " + (i + 1) + "° dado da riposizionare:");
            ArrayList<Integer> obj2 = askDiceMap();
            obj.add(obj2.get(0));
            obj.add(obj2.get(1));
            try {
                if (gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice()!=null)
                    dices.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice());
            } catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
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
        //Dice dicStock2,Dice diceRound, int numberRound,row,column
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(gameStatus.getStock().get(askDiceStock()));
        ArrayList<Object> obj2 = askDiceRound();
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        ArrayList<Integer> obj3 = askDiceMap();
        obj.add(obj3.get(0));
        obj.add(obj3.get(1));
        return obj;
    }

    @Override
    public ArrayList<Object> manageTap() {
        //Dice diceRound,  int row1, int column1, int row2, int column2,Arraylist Dice (dice1, Dice dice2), posizione dado
        // in roundscheme
        ArrayList<Object> obj = new ArrayList<>();
        ArrayList<Dice> obj2 = new ArrayList<>();
        ArrayList<Object> obj3;
        obj3 = askDiceRound();
        obj.add(obj3.get(0));
        int a = 0;
        while (a < 2) {
            addLog("Seleziona il dado da spostare: ");
            ArrayList<Integer> obj4 = askDiceMap();
            obj2.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj4.get(0)][obj4.get(1)].getDice());
            obj.add(obj4.get(0));
            obj.add(obj4.get(1));
            boolean sec=false;
            while (sec){
                addLog("Vuoi selezionare un altro dado? [Si/No]");
                String choose = scanner.nextLine();
                if (choose.equalsIgnoreCase("si")){
                    a++;
                    sec=true;
                }

                else if (choose.equalsIgnoreCase("no")){
                    a=3;
                    sec=true;
                }
            }

        }
        if (a == 2)
            addError("Non puoi selezionare un ulteriore dado! Verranno inviati quelli selezionati precedentemente");

        obj.add(obj2);
        obj.add(obj3.get(1));
        return obj;
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
        printDicesStock();
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

    public void printBold(String text){
        System.out.println("\033[1m"+text);
    }

    public void printCors(String text){
        System.out.println("\033[4m"+text);
    }


    public ArrayList<Object> askDiceRound() {
        ArrayList<Object> obj = new ArrayList<>();
        addLog("Seleziona il dado dallo schema dei round:");
        printSchemeRounds();
        addLog("Seleziona il round dal quale prendere il dado:");
        int round = Integer.decode(scanner.nextLine()) - 1;
        addLog("Seleziona il dado da questo round:");
        printSchemeRound(round);
        obj.add(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(Integer.decode(scanner.nextLine()) - 1));
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
        addLog("Round "+String.valueOf(round+1)+":");
        for (int dice = 0; dice < gameStatus.getRoundSchemeMap()[round].getRestOfStock().size(); dice++) {
            System.out.print(" - ");
            printColor(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(dice).getColor().toString(),
                    gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(dice).toString());
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
        printColor(diceAfter.getColor().toString(), diceAfter.toString());
        addLog("Scegli dove posizionare il dado: ");
        ArrayList<Integer> obj2 = askRowColumn();
        obj.add(diceBefore);
        obj.add(diceAfter);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }


    @Override
    public ArrayList<Object> manageFluxRemove2(Dice dice) {
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

    @Override
    public String reconnect() {
        Scanner scanner2 = new Scanner(System.in);
        try{
            addLog("Per riconnetterti scrivi qualsiasi cosa:");
            scanner.close();
            scanner = null;
            return (scanner2.nextLine());
        }
        catch (NoSuchElementException e){
            return "C'è vita dietro lo schermo";
        }
    }

    public void printCardPrivate(String title, String description){
        addLog("Titolo: "+title);
        addLog("Descrizione: "+description);
    }
    public void printCardPublic(String title, String description, int score){
        printCardPrivate(title,description);
        addLog("Punteggio aggiuntivo: +" +score);
    }
}