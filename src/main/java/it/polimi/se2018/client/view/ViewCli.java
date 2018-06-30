package it.polimi.se2018.client.view;

import it.polimi.se2018.shared.model_shared.Dice;

import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.exception.InvalidValueException;
import it.polimi.se2018.shared.Logger;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Class that extends the view. Implement the Command Line Interface
 *
 * @author Samuele Guida
 */
public class ViewCli extends View {

    private transient Scanner scanner = new Scanner(System.in);
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Logger.class.getName());

    /**
     * class constructor
     *
     * @param timer integer
     */
    public ViewCli(int timer) {
        super(timer);
    }

    /**
     * method that print a system information
     *
     * @param message the string printed
     */
    @Override
    public void addLog(String message) {
        System.out.println("");
        Logger.information(message);
    }


    /**
     * method that manage the turn of this player
     */
    @Override
    public void myTurn() {
        boolean valid = false;
        boolean map = false;
        int chooseDice;
        int chooseColumn = 0;
        int chooseRow = 0;
        int chooseTool;
        try {
            while (!valid) {

                if (scanner != null) {
                    // SELEZIONE MOSSA
                    addLog(" ");
                    printBold("E' il tuo turno. Scegli che mossa fare: ");
                    addLog("1 - Posizionare un dado dalla riserva alla tua carta schema " +
                            "\n2 - Usare una carta utensile \n3 - Non fare niente in questa mossa \n4 - Visualizza il tuo stato" +
                            "\n5 - Visualizza lo stato degli avversari" + "\n6 - Visualizza le informazioni generali della partita");
                    String choose = scanner.nextLine();
                    map = false;
                    addLog(" ");
                    if (choose.equalsIgnoreCase("1")) {
                        // SI VUOLE POSIZIONARE UN DADO
                        if (!gameStatus.isPosDice()) {
                            valid = true;
                            boolean cho = false;
                            while (!cho) {
                                // SELEZIONE DEL DADO
                                printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
                                chooseDice = askDiceStock();
                                if (chooseDice > gameStatus.getStock().size() - 1 || chooseDice < 0)

                                    addError("Non hai selezionato un dado della lista");
                                else {
                                    List<Integer> inta = askRowColumn();
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
                        if (!gameStatus.isUseTool()) {
                            // IL GIOCATORE HA SCELTO DI USARE UNA CARTA TOOLS
                            boolean tool = false;
                            while (!tool) {
                                if (gameStatus.getFavUser().get(gameStatus.getYourIndex()) > 0) {
                                    printBold("Carte utensili utilizzabili:");
                                    printTools();
                                    addLog(" ");
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
                        printCardPrivate(gameStatus.getTitlePrivateObjective(), gameStatus.getDescriptionPrivateObjective());
                        addLog(" ");
                        printBold("Ecco la tua mappa: ");
                        printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
                        addLog(" ");
                        printBold("Segnalini favore rimanenti: " + gameStatus.getFavUser().get(gameStatus.getYourIndex()));
                        map = true;
                    }

                    if (choose.equalsIgnoreCase("5")) {

                        for (int i = 0; i < gameStatus.getCells().size(); i++) {
                            if (i != gameStatus.getYourIndex()) {
                                addLog(" ");
                                printBold("Giocatore " + (i + 1) + " : " + gameStatus.getUsers().get(i));
                                addLog("Punti favore rimanenti: " + gameStatus.getFavUser().get(i));
                                printSchemeMap(gameStatus.getCells().get(i));
                            }
                        }
                        addLog(" ");
                        map = true;
                    }
                    if (choose.equalsIgnoreCase("6")) {
                        printBold("Carte obiettivo pubbliche: ");
                        for (int i = 0; i < gameStatus.getTitlePublicObjective().size(); i++) {
                            addLog("");
                            printUnderline("Carta obiettivo pubblica " + (i + 1) + ":");
                            printCardPublic(gameStatus.getTitlePublicObjective().get(i), gameStatus.getDescriptionPublicObjective().get(i)
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
                } else
                    valid = true;

            }

        } catch (IndexOutOfBoundsException | NoSuchElementException | IllegalStateException | NullPointerException e) {
            // se si entra qui dentro è perchè il giocatore prima si era disconnesso.
            addLog("Chiusura mossa precedente");
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Errore di connessione: {0} !", e.getMessage());
        }
    }

    /**
     * method that build a string
     *
     * @param name the name of this map
     * @return a string with 19 character
     */
    private String doString(String name) {
        StringBuilder sb = new StringBuilder(19);
        sb.append(name);
        for (int i = sb.length(); i < sb.capacity(); i++)
            sb.append(" ");
        return sb.toString();
    }

    /**
     * method that ask the map to the player
     *
     * @param maps  list of maps
     * @param names names of maps
     * @param fav   the difficult of maps
     * @return
     */
    public int chooseSingleMap(List<Cell[][]> maps, List<String> names, List<Integer> fav) {

        int val = 9;
        while ((val > maps.size()) || (val < 1)) {
            for (int rig = 0; rig < 2; rig++) {
                System.out.println();
                for (int i = 0; i < names.size()/2; i++) {
                    System.out.print("      Mappa " + (i + 1 +2*rig) + "                                      ");
                }
                System.out.println();
                for (int i = 0; i < names.size()/2; i++) {

                    System.out.print("Nome: " + (doString(names.get((i +2*rig)))) + "                         ");
                }

                System.out.println("  ");
                System.out.print("  ");

                for (int j = 0; j < maps.get(2*rig)[0].length; j++) {
                    // scrivo il numero della colonna della prima mappa
                    System.out.print("| " + (j+1) + " ");
                }
                System.out.print("                             ");
                for (int j = 0; j < maps.get(1 +2*rig)[0].length; j++) {
                    // scrivo il numero della colonna della seconda mappa
                    System.out.print("| " + (j+1) + " ");
                }

                System.out.println("");
                System.out.print("__");
                System.out.print(" ");
                for (int j = 0; j < maps.get(2*rig)[0].length; j++) {
                    // scrivo un "_" per separare la intenstazione della prima tabella
                    System.out.print(" _  ");

                }
                System.out.print("                          ");
                System.out.print("__ ");
                for (int j = 0; j < maps.get(1 +2*rig)[0].length; j++) {
                    // scrivo un "_" per separare la intenstazione della seconda tabella
                    System.out.print(" _  ");
                }

                // esamino le righe
                for (int i = 0; i < maps.get(2*rig).length; i++) {
                    // mostra il numero di riga
                    System.out.println("");
                    System.out.print(i + 1);
                    // esamino le colonne
                    for (int j = 0; j < maps.get(2*rig)[i].length; j++) {
                        System.out.print(" | ");
                        if (maps.get((2*rig))[i][j].getDice() != null) {
                            // SE LA CASELLA HA GIA' UN DADO, STAMPA LE CARATTERISTICHE DEL DADO

                            printColor(maps.get(2*rig)[i][j].getDice().getColor().toString(), maps.get(2*rig)[i][j].getDice().toString());
                        } else {
                            // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                            printColor(maps.get(2*rig)[i][j].getColor().toString(), maps.get(2*rig)[i][j].toString());
                        }

                    }

                    System.out.print("                            ");
                    System.out.print(i + 1);
                    for (int j = 0; j < maps.get(1 +2*rig)[i].length; j++) {
                        System.out.print(" | ");
                        if (maps.get((1 +2*rig))[i][j].getDice() != null) {
                            // SE LA CASELLA HA GIA' UN DADO, STAMPA LE CARATTERISTICHE DEL DADO

                            printColor(maps.get(1 +2*rig)[i][j].getDice().getColor().toString(), maps.get(1 +2*rig)[i][j].getDice().toString());
                        } else {
                            // SE LA CASELLA NON HA ANCORA UN DADO, STAMPA LE CARATTERISTICHE DELLA CASELLA
                            printColor(maps.get(1 +2*rig)[i][j].getColor().toString(), maps.get(1 +2*rig)[i][j].toString());
                        }

                    }

                }
                System.out.println();
                System.out.println();
                for (int i=0;i<names.size()/2;i++) {
                    System.out.print("    Difficoltà: " + fav.get(i+2*rig) + "                                ");
                }

                System.out.println();
                System.out.println();

            }
            System.out.println("Quale mappa scegli?");
            val = Integer.decode(scanner.nextLine());
            if (val > (maps.size()) || val < 1) {
                addError("Hai inserito un valore errato");
            }

        }


        return val - 1;
    }

    /**
     * method that print the stock
     */
    private void printDicesStock() {
        for (int i = 0; i < gameStatus.getStock().size(); i++) {
            System.out.print((i + 1) + " - ");
            printColor(gameStatus.getStock().get(i).getColor().toString(), String.valueOf(gameStatus.getStock().get(i).getValue()));
            System.out.println("");
        }
    }

    /**
     * method that print the tool cards
     */
    private void printTools() {
        for (int i = 0; i < gameStatus.getUseTools().size(); i++) {
            printUnderline(i + 1 + " - Titolo: " + gameStatus.getTitleTools().get(i));
            System.out.println("Descrizione: " + gameStatus.getDescriptionTools().get(i));
            System.out.println("Usata in precedenza: " + (gameStatus.getUseTools().get(i) ? "Sì" : "No"));
        }
    }

    /**
     * method that print the scheme map of this player
     *
     * @param cells the matrix of cell to print
     */
    private void printSchemeMap(Cell[][] cells) {
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

    /**
     * method that ask the username
     *
     * @return the username
     */
    @Override
    public String askNewUsername() {
        boolean sec = true;
        String user = "";
        while (sec) {
            addLog("Inserisci il tuo username:");
            user = scanner.nextLine();
            if (!user.equalsIgnoreCase(""))
                sec = false;
            else
                addLog("Non puoi inserire un username vuoto!");
        }
        return user;


    }

    /**
     * method that print an error
     *
     * @param message the string printed
     */
    @Override
    public void addError(String message) {
        Logger.error("ATTENZIONE - " + message);
    }

    /**
     * method that manage the tool cards "copper foil burnisher" and "Eglomise Brush"
     *
     * @return a list of object: Dice, RowDest, ColumnDest, rowMit, ColumnMit
     */
    @Override
    public List<Object> manageCE() {
        // Dice, RowDest, ColumnDect, rowMit, ColumnMit
        ArrayList<Object> obj = new ArrayList<>();
        List<Integer> obj2 = askDiceMap();
        addLog("Seleziona dove vuoi piazzare il dado scelto:");
        List<Integer> obj3 = askRowColumn();
        obj.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice());
        obj.add(obj3.get(0));
        obj.add(obj3.get(1));
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    /**
     * method that manage the first part of tool card "Grozing pliers"
     *
     * @return the dice choose
     */
    @Override
    public Dice manageGrozing1() {
        // Dice
        printSchemeMap(gameStatus.getCells().get(gameStatus.getYourIndex()));
        return (gameStatus.getStock().get(askDiceStock()));
    }

    /**
     * method that manage the second part of tool card "Grozing Pliers"
     *
     * @param minus the value-1
     * @param major the value+1
     * @return the value choose
     */
    @Override
    public int manageGrozing2(int minus, int major) {
        addLog("Scegli il nuovo valore che deve avere il dado");
        if (minus == 0)
            return major;
        else if (major == 0)
            return minus;
        addLog(String.valueOf(minus) + " o " + major + " ?");
        return (Integer.decode(scanner.nextLine()));
    }

    /**
     * method that manage the third part of tool card "Grozing Pliers"
     *
     * @return a list of integer: row, column
     */
    public List<Integer> manageGrozing3() {
        addLog("Seleziona dove inserire il dado");
        return askRowColumn();
    }

    /**
     * abstract method that manage the tool card "Lathekin"
     *
     * @return a list of object: int row1,int column1,
     * row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList dices
     */
    public List<Object> manageLathekin() {
        //int row1,int column1, row1dest, column1dest, int row2, int column2, row2dest, column2dest, ArrayList<Dice> dices
        ArrayList<Object> obj = new ArrayList<>();
        ArrayList<Dice> dices = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            addLog("Scegli il " + (i + 1) + "° dado da riposizionare:");
            List<Integer> obj2 = askDiceMap();
            obj.add(obj2.get(0));
            obj.add(obj2.get(1));
            try {
                if (gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice() != null)
                    dices.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj2.get(0)][obj2.get(1)].getDice());
            } catch (ArrayIndexOutOfBoundsException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            addLog("Scegli dove riposizionare il dado scelto:");
            List<Integer> obj3 = askDiceMap();
            obj.add(obj3.get(0));
            obj.add(obj3.get(1));
        }
        obj.add(dices);
        return obj;
    }

    /**
     * abstract method that manage the tool card "Lens Cutter"
     *
     * @return a list of object: Dice dicStock2,Dice diceRound, int numberRound,row,column
     */
    @Override
    public List<Object> manageLens() {
        //Dice dicStock2,Dice diceRound, int numberRound,row,column
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(gameStatus.getStock().get(askDiceStock()));
        List<Object> obj2 = askDiceRound();
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        List<Integer> obj3 = askDiceMap();
        obj.add(obj3.get(0));
        obj.add(obj3.get(1));
        return obj;
    }

    /**
     * abstract method that manage the tool card "Tap Wheel"
     *
     * @return a list of object: Dice diceRound,  int row1, int column1, int row2, int column2,
     * Arraylist Dice (dice1, Dice dice2), posizione dado in round scheme
     */
    @Override
    public List<Object> manageTap() {
        //Dice diceRound,  int row1Mit, int column1Mit, int row1Dest, int column1Dest, int row2Mit, int column2Mit, int row2Dest, int column2Dest,
        // Arraylist Dice (dice1, Dice dice2), posizione dado in roundscheme
        ArrayList<Object> obj = new ArrayList<>();
        ArrayList<Dice> obj2 = new ArrayList<>();
        List<Object> obj3;
        obj3 = askDiceRound();
        obj.add(obj3.get(0));
        int a = 0;
        while (a < 2) {
            addLog("Seleziona il dado da spostare: ");
            List<Integer> obj4 = askDiceMap();
            obj2.add(gameStatus.getCells().get(gameStatus.getYourIndex())[obj4.get(0)][obj4.get(1)].getDice());
            obj.add(obj4.get(0));
            obj.add(obj4.get(1));
            addLog("Seleziona dove vuoi posizionare il dado selezionato: ");
            List<Integer> obj5 = askRowColumn();
            obj.add(obj5.get(0));
            obj.add(obj5.get(1));
            boolean sec = true;
            while (sec) {
                addLog("Vuoi selezionare un altro dado? [Si/No]");
                String choose = scanner.nextLine();
                if (choose.equalsIgnoreCase("si")) {
                    a++;
                    if (a == 2)
                        addError("Non puoi selezionare un ulteriore dado! Verranno inviati quelli selezionati precedentemente");
                    sec = false;
                } else if (choose.equalsIgnoreCase("no")) {
                    a = 3;
                    sec = false;
                }
            }
        }
        obj.add(obj2);
        obj.add(obj3.get(1));
        return obj;
    }

    /**
     * abstract method that manage the tool card "Corkbacked Straightedge"
     *
     * @return a list of object: Dice, row, column
     */
    @Override
    public List<Object> manageCork() {
        // Dice, row, column
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(gameStatus.getStock().get(askDiceStock()));
        addLog("Seleziona le coordinate dove posizionare il dado:");
        List<Integer> obj2 = askRowColumn();
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    /**
     * method that ask a dice from the stock
     *
     * @return the index of dice in the stock
     */
    private int askDiceStock() {
        addLog("Dadi disponibili:");
        printDicesStock();
        addLog("Quale dado vuoi posizionare?");
        return Integer.decode(scanner.nextLine()) - 1;
    }

    /**
     * method that ask coordinates of a cell from the map
     *
     * @return a list: row,column
     */
    private List<Integer> askDiceMap() {
        addLog("La tua mappa:");
        addLog("Inserisci le cordinate:");
        return askRowColumn();
    }

    /**
     * method that ask the row and the column of a cell from the map
     *
     * @return a list: row,column
     */
    private List<Integer> askRowColumn() {
        int chooseColumn = 0;
        int chooseRow = 0;
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

    /**
     * method that print a number with a color
     *
     * @param color  the color choose
     * @param number the number to be printed
     */
    private void printColor(String color, String number) {
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

    /**
     * method that print a text in bold
     *
     * @param text the text to be printed
     */
    private void printBold(String text) {
        System.out.println("\033[1m" + text + "\033[0m");
    }

    /**
     * method that print a text underlined
     *
     * @param text the text to be printed
     */
    private void printUnderline(String text) {
        System.out.println("\033[4m" + text + "\033[0m");
    }

    /**
     * methot that ask a dice from the round scheme
     *
     * @return a list of object: Dice dice choose, int round
     */
    private List<Object> askDiceRound() {
        ArrayList<Object> obj = new ArrayList<>();
        addLog("Seleziona il dado dallo schema dei round:");
        printSchemeRounds();
        addLog("Seleziona il round dal quale prendere il dado:");
        int round = Integer.decode(scanner.nextLine()) - 1;
        addLog("Seleziona il dado da questo round:");
        printSchemeRound(round);
        addLog("");
        obj.add(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(Integer.decode(scanner.nextLine()) - 1));
        obj.add(round);
        return obj;
    }

    /**
     * method that print the scheme round
     */
    private void printSchemeRounds() {
        // cicla i round
        for (int round = 0; round < gameStatus.getRoundSchemeMap().length; round++) {
            // cicla i dadi all'interno di ogni round
            printSchemeRound(round);
        }
    }

    /**
     * method that print the dice in a specific round of the round scheme map
     *
     * @param round an integer
     */
    private void printSchemeRound(int round) {
        addLog("Round " + (round + 1) + ":");
        for (int dice = 0; dice < gameStatus.getRoundSchemeMap()[round].getRestOfStock().size(); dice++) {
            System.out.print(" - ");
            printColor(gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(dice).getColor().toString(),
                    gameStatus.getRoundSchemeMap()[round].getRestOfStock().get(dice).toString());
        }
    }

    /**
     * method that manage the tool card "Flush Brush"
     *
     * @return a list of object: dice dicebefore, dice diceafter, int rowdest, int columndest
     */
    @Override
    public List<Object> managefluxBrush() {
        // dice dicebefore, dice diceafter, int rowdest, int columndest
        ArrayList<Object> obj = new ArrayList<>();
        Dice diceBefore = gameStatus.getStock().get(askDiceStock());
        Dice diceAfter = null;
        try {
            diceAfter = diceBefore.clone();
            diceAfter.throwDice();
        } catch (CloneNotSupportedException | NullPointerException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        addLog("Ecco il risultato del lancio del dado precedentemente scelto: ");
        assert diceAfter != null;
        printColor(diceAfter.getColor().toString(), diceAfter.toString());
        addLog("Scegli dove posizionare il dado: ");
        List<Integer> obj2 = askRowColumn();
        obj.add(diceBefore);
        obj.add(diceAfter);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    /**
     * method that manage the second part of tool card "Flux Remover"
     *
     * @param dice dice choose
     * @return a list of object: dice row column
     */
    @Override
    public List<Object> manageFluxRemove2(Dice dice) {
        int valore = 0;
        boolean ok = false;
        while (!ok) {
            addLog("Hai estratto un dado di colore " + dice.getColor().toString() + "\nScegli il valore da assegnare a questo dado:");
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
        List<Integer> obj2 = askRowColumn();
        ArrayList<Object> obj = new ArrayList<>();
        obj.add(dice);
        obj.add(obj2.get(0));
        obj.add(obj2.get(1));
        return obj;
    }

    /**
     * method that manage the reconnection of a player
     *
     * @return a not null string
     */
    @Override
    public String reconnect() {
        Scanner scanner2 = new Scanner(System.in);
        try {
            addLog("Per riconnetterti scrivi qualsiasi cosa:");
            scanner.close();
            scanner = null;
            return (scanner2.nextLine());
        } catch (NoSuchElementException e) {
            return "C'è vita dietro lo schermo";
        }
    }

    /**
     * method that print the scores of all player at the end of the game
     *
     * @param scores list of scores
     */
    @Override
    public void seeScore(List<Integer> scores) {
        addLog("Ecco i punteggi di tutti i giocatori: ");
        for (int i = 0; i < scores.size(); i++) {
            printBold("\"Giocatore 1 - \" + gameStatus.getUsers().get(i)");
            addLog("Punteggio finale: " + scores.get(i));
        }
    }

    /**
     * method that print the information about private objective card
     *
     * @param title       title of the private objective card
     * @param description description of the private objective card
     */
    private void printCardPrivate(String title, String description) {
        addLog("Titolo: " + title);
        addLog("Descrizione: " + description);
    }

    /**
     * method that print the information about a public objective card
     *
     * @param title       title of the public objective card
     * @param description description of the public objective card
     * @param score       score of a singole combination of this public card
     */
    private void printCardPublic(String title, String description, int score) {
        printCardPrivate(title, description);
        addLog("Punteggio aggiuntivo: +" + score);
    }
}