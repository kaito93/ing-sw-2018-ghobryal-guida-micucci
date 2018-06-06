package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.Map;
import it.polimi.se2018.model.cell.Cell;
import it.polimi.se2018.model.exception.notValidMatrixException;
import it.polimi.se2018.network.client.message.Message;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;

public class MapsDeserializer extends Observable {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(Message.class.getName());


    private Gson gson;
    private File json;
    private BufferedReader br;
    private ArrayList<TransitionForMaps> mapsJsonJava;
    private BlankCellBuilder blank;
    private ValueCellBuilder value;
    private ColoredCellBuilder coloured;
    private ArrayList<entireMap> definitive;
    private ArrayList<entireMap> definitive2;
    private entireMap transiction;
    private ArrayList<Cell> transCell;

    public MapsDeserializer() {
        json = new File("src/main/java/it/polimi/se2018/JsonFiles/Maps.json");
        gson = new Gson();
        try {
            br = new BufferedReader(new FileReader(json));
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File non trovato", e);
        }
        mapsJsonJava = new ArrayList<>();
        blank = new BlankCellBuilder();
        value = new ValueCellBuilder();
        coloured = new ColoredCellBuilder();
        definitive = new ArrayList<>();
        transiction = new entireMap();
        transCell = new ArrayList<>();
    }

    public void Deserializing() {
        Type listJson = new TypeToken<ArrayList<TransitionForMaps>>() {
        }.getType();
        mapsJsonJava = gson.fromJson(br, listJson);
    }

    public void SetUpObservers() {
        this.addObserver(blank);
        this.addObserver(value);
        this.addObserver(coloured);
    }

    public void removeObservers() {
        this.removeObservers();
    }

    public TransitionForMaps ExtractTransition(int index) {
        return this.mapsJsonJava.get(index);
    }

    public void StartMapBuilding(TransitionForMaps element) {
        setChanged();
        notifyObservers(element);
    }

    public void SetUpArraylistMatrix() {        //parametro che indica la mappa di riferimento
        int indexOfCell;
        boolean find=false;
        ArrayList<Cell> temp = new ArrayList<>();
        for (int indexOfMap = 0; indexOfMap < definitive.size(); indexOfMap++) {
            temp.add(definitive.get(indexOfMap).getMatrix().get(0));
            for (indexOfCell = 1; indexOfCell < definitive.get(indexOfMap).getMatrix().size(); indexOfCell++) {
                int indexOfTemp=0;
                while (!find && indexOfTemp<temp.size()){
                    if (definitive.get(indexOfMap).getMatrix().get(indexOfCell).getNumberCell()<temp.get(indexOfTemp).getNumberCell())
                    {
                        temp.add(indexOfTemp,definitive.get(indexOfMap).getMatrix().get(indexOfCell));
                        find = true;
                    }
                    else
                        indexOfTemp++;
                }
                if (!find)
                    temp.add(definitive.get(indexOfMap).getMatrix().get(indexOfCell));
                find=false;
            }
            definitive.get(indexOfMap).setAllMatrix(temp);
            temp= new ArrayList<>();
        }

    }

    public ArrayList<entireMap> getDefinitive() {
        return definitive;
    }

    public ArrayList<Map> totalDeserialize() {
        ArrayList<Map> definitiveMap = new ArrayList<>();
        this.Deserializing();
        this.SetUpObservers();
        for (int index = 0; index < mapsJsonJava.size(); index++) {
            this.StartMapBuilding(this.ExtractTransition(index));
            transiction.setTitle(blank.getEntireMapTitle());
            transiction.setLevel(blank.getEntireMapLevel());
            transiction.setAllMatrix(mergeArraylist());
            // richiamare un metodo per ordinare l'arraylist di celle per numero di cella
            definitive.add(transiction);
            blank = new BlankCellBuilder();
            coloured = new ColoredCellBuilder();
            value = new ValueCellBuilder();
            transiction = new entireMap();
            this.SetUpObservers();
        }
        this.SetUpArraylistMatrix();

        for (int i = 0; i < definitive.size(); i++) {
            try {
                Map nmap = new Map(definitive.get(i).getTitle(), definitive.get(i).getLevel(), 4, 5);
                setUpMatrix(i, nmap.getCell());
                definitiveMap.add(nmap);
            } catch (notValidMatrixException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
        return definitiveMap;
    }


    public void setUpMatrix(int numberOfMap, Cell[][] matrix) {
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

    public ArrayList<Cell> mergeArraylist() {
        ArrayList<Cell> mergiato = new ArrayList<>();

        for (int i = 0; i < blank.mappaIntera.getMatrix().size(); i++) {
            mergiato.add(blank.mappaIntera.getMatrix().get(i));
        }
        for (int i = 0; i < coloured.mappaIntera.getMatrix().size(); i++) {
            mergiato.add(coloured.mappaIntera.getMatrix().get(i));
        }
        for (int i = 0; i < value.mappaIntera.getMatrix().size(); i++) {
            mergiato.add(value.mappaIntera.getMatrix().get(i));
        }
        return mergiato;
    }
}
