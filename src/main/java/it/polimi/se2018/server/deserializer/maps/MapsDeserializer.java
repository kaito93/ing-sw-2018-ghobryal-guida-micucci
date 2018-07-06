package it.polimi.se2018.server.deserializer.maps;

import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.server.deserializer.maps.cells.BlankCellBuilder;
import it.polimi.se2018.server.deserializer.maps.cells.ColoredCellBuilder;
import it.polimi.se2018.server.deserializer.maps.cells.ValueCellBuilder;
import it.polimi.se2018.server.model.Map;
import it.polimi.se2018.shared.Deserializer;
import it.polimi.se2018.shared.model_shared.Cell;
import it.polimi.se2018.shared.exception.NotValidMatrixException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * class used to deserializer the maps from json file
 * extends observable class for observer pattern
 * @author Andrea Micucci
 */
public class MapsDeserializer extends Deserializer {

    private ArrayList<TransitionForMaps> mapsJsonJava;
    private BlankCellBuilder blank;
    private ValueCellBuilder value;
    private ColoredCellBuilder coloured;
    private ArrayList<EntireMap> definitive;
    private EntireMap transition;

    /**
     * class constructor: inizialize all the variable and the buffered reader for the json file
     * @param path of the file that has to be deserializer
     */
    public MapsDeserializer(String path) {
        super(path);
        mapsJsonJava = new ArrayList<>();
        blank = new BlankCellBuilder();
        value = new ValueCellBuilder();
        coloured = new ColoredCellBuilder();
        definitive = new ArrayList<>();
        transition = new EntireMap();
    }

    /**
     * class used to make the deserializing of the json file in a data structure
     * that contains all the string with the information about Glasswindow
     */
    public void deserializing() {
        Type listJson = new TypeToken<ArrayList<TransitionForMaps>>() {
        }.getType();
        mapsJsonJava = getGson().fromJson(getBr(), listJson);
    }

    /**
     * method that set all the observer class to build the CellBuilder object of the
     * GlassWindow
     */
    private void setUpObservers() {
        this.addObserver(blank);
        this.addObserver(value);
        this.addObserver(coloured);
    }

    /**
     * method that return a cell of the arraylist contain all the info about one map
     * in form of string
     * @param index of the cell of the arraylist, which is the index of the map
     * @return a cell of the arraylist with all the string with info about map
     */
    private TransitionForMaps extractTransition(int index) {
        return this.mapsJsonJava.get(index);
    }

    /**
     * method that notify to all observer class to build the cell of the maps
     * @param element the cell of arraylist referred to the map to build
     */
    private void startMapBuilding(TransitionForMaps element) {
        setChanged();
        notifyObservers(element);
    }

    /**
     * method that takes all the maps with the CellBuilder Arraylist and build the map object with the matrix
     */
    private void setUpArraylistMatrix() {
        int indexOfCell;
        boolean find=false;
        ArrayList<Cell> temp = new ArrayList<>();
        for (EntireMap aDefinitive : definitive) {
            temp.add(aDefinitive.getMatrix().get(0));
            for (indexOfCell = 1; indexOfCell < aDefinitive.getMatrix().size(); indexOfCell++) {
                int indexOfTemp = 0;
                while (!find && indexOfTemp < temp.size()) {
                    if (aDefinitive.getMatrix().get(indexOfCell).getNumberCell() < temp.get(indexOfTemp).getNumberCell()) {
                        temp.add(indexOfTemp, aDefinitive.getMatrix().get(indexOfCell));
                        find = true;
                    } else
                        indexOfTemp++;
                }
                if (!find)
                    temp.add(aDefinitive.getMatrix().get(indexOfCell));
                find = false;
            }
            aDefinitive.setAllMatrix(temp);
            temp = new ArrayList<>();
        }

    }

    /**
     * method that make a total deserializing of all the maps in json files
     * @return an arraylist with all the maps
     */
    public List<Map> totalDeserialize() {
        ArrayList<Map> definitiveMap = new ArrayList<>();
        this.deserializing();
        this.setUpObservers();
        for (int index = 0; index < mapsJsonJava.size(); index++) {
            this.startMapBuilding(this.extractTransition(index));
            transition.setTitle(blank.getEntireMapTitle());
            transition.setLevel(blank.getEntireMapLevel());
            transition.setAllMatrix(mergeArraylist());
            // richiamare un metodo per ordinare l'arraylist di celle per numero di cella
            definitive.add(transition);
            blank = new BlankCellBuilder();
            coloured = new ColoredCellBuilder();
            value = new ValueCellBuilder();
            transition = new EntireMap();
            this.setUpObservers();
        }
        this.setUpArraylistMatrix();

        for (int i = 0; i < definitive.size(); i++) {
            try {
                Map nmap = new Map(definitive.get(i).getTitle(), definitive.get(i).getLevel(), 4, 5);
                setUpMatrix(i, nmap.getCells());
                definitiveMap.add(nmap);
            } catch (NotValidMatrixException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
        return definitiveMap;
    }


    /**
     * method that make a matrix of cell from the arraylist of cell
     * @param numberOfMap of which has to make matrix
     * @param matrix of cell that has to made
     */
    private void setUpMatrix(int numberOfMap, Cell[][] matrix) {
        int indexOfMatrix;
        int sizeOfColumn = matrix[0].length;
        int indexOfColumn;
        int indexOfLine = 0;
        int indexOfArrayList = definitive.get(numberOfMap).getMatrix().size();
        for (indexOfMatrix = 0; indexOfMatrix < indexOfArrayList; indexOfMatrix++) {
            indexOfColumn = 0;
            if(indexOfMatrix != 0)
                indexOfMatrix--;
            do {
                matrix[indexOfLine][indexOfColumn] = definitive.get(numberOfMap).getCellOfMatrix(indexOfMatrix);
                indexOfColumn++;
                indexOfMatrix++;
            } while (indexOfColumn % sizeOfColumn != 0);
            indexOfLine++;
        }
    }

    /**
     * method that take all the object created by the observer and create the arraylist of
     * ordered cell
     * @return an arraylist of cell
     */
    private List<Cell> mergeArraylist() {
        ArrayList<Cell> mergiato = new ArrayList<>();

        mergiato.addAll(blank.getMappaIntera().getMatrix());
        mergiato.addAll(coloured.getMappaIntera().getMatrix());
        mergiato.addAll(value.getMappaIntera().getMatrix());
        return mergiato;
    }
}
