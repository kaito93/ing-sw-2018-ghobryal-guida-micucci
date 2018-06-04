package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cell.Cell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Observable;

public class MapsDeserializer extends Observable{

    private Gson gson;
    private File json;
    private BufferedReader br;
    private ArrayList<TransitionForMaps> mapsJsonJava;
    private BlankCellBuilder blank;
    private ValueCellBuilder value;
    private ColoredCellBuilder coloured;
    ArrayList<entireMap> definitive;

    public MapsDeserializer(){
        json = new File("src/main/java/it/polimi/se2018/JsonFiles/Maps.json");
        try{
            br = new BufferedReader(new FileReader(json));
        } catch(FileNotFoundException e){
            System.out.println("non ho trovato il file");
        }
    }

    public void Deserializing(){
        Type listJson = new TypeToken<ArrayList<TransitionForMaps>>(){}.getType();
        mapsJsonJava = gson.fromJson(br, listJson);
    }

    public void SetUpObservers(){
        this.addObserver(blank);
        this.addObserver(value);
        this.addObserver(coloured);
    }

    public TransitionForMaps ExtractTransition(int index){
        return this.mapsJsonJava.get(index);
    }

    public void StartMapBuilding(TransitionForMaps element){
        setChanged();
        notifyObservers(element);
    }

    public void SetUpArraylistMatrix(){        //parametro che indica la mappa di riferimento
        int vi = 0;     //value index
        int bi = 0;     // blank index
        int ci = 0;     // coloured index
        while((blank.maps.size() != 0) && (value.maps.size()!=0) && (coloured.maps.size()!=0)){
            if ((blank.maps.get(bi).getNumberCell() < value.maps.get(vi).getNumberCell()) && (blank.maps.get(bi).getNumberCell() < coloured.maps.get(ci).getNumberCell())){
                    definitive.add(blank.mappaIntera.getMatrixCell(bi));
                    bi++;
                }
            else if (value.maps.get(vi).getNumberCell() < blank.maps.get(bi).getNumberCell() && (value.maps.get(vi).getNumberCell() < coloured.maps.get(ci).getNumberCell())) {
                    definitive.add(value.mappaIntera.getMatrixCell(vi));
                    vi++;
                }
            else if (coloured.maps.get(ci).getNumberCell() < blank.maps.get(bi).getNumberCell() && (value.maps.get(vi).getNumberCell() > coloured.maps.get(ci).getNumberCell())) {
                    definitive.add(coloured.mappaIntera.getMatrixCell(ci));
                    ci++;
                }
            }
        }

    public ArrayList<entireMap> getDefinitive() {
        return definitive;
    }

    public void totalDeserialize(){
        this.Deserializing();
        this.SetUpObservers();
        for(int index=0; index < mapsJsonJava.size(); index++)
            this.StartMapBuilding(this.ExtractTransition(index));
        this.SetUpArraylistMatrix();
    }

    public Cell[][] setUpMatrix(int numberOfMap, Cell[][] matrix){
       int indexOfMatrix;
        int sizeOfColumn = matrix[0].length -1;
        int indexOfColumn;
        int indexOfLine = 0;
       int indexOfArrayList = definitive.get(numberOfMap).getMatrix().size();
       for(indexOfMatrix = 0; indexOfMatrix <= indexOfArrayList; indexOfMatrix++){
           indexOfColumn = 0;
           do{
               matrix[indexOfColumn][indexOfLine] = definitive.get(numberOfMap).getCellOfMatrix(indexOfMatrix);
               indexOfColumn++;
               indexOfMatrix++;
           } while (indexOfColumn%sizeOfColumn != 0);
       }
       return matrix;
    }
}
