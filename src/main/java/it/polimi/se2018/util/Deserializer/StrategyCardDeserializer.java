package it.polimi.se2018.util.Deserializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se2018.model.cards.PublicObjectiveCard;
import it.polimi.se2018.model.cards.public_objective_card_strategy.*;
import java.util.Observer;
import java.util.Observable;
import it.polimi.se2018.util.jsonTransiction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class StrategyCardDeserializer extends Observable{
        private Gson gson = new Gson();
        private File json;
        private BufferedReader br;
        ArrayList<jsonTransiction>jsontrans;


        /**
         * class constructor, deserialize all public objective card from
         * a json file
         * @throws FileNotFoundException if the json file doesn't exist
         */
        public StrategyCardDeserializer(String pathname) {
            json = new File(pathname);

            try {
                br = new BufferedReader(new FileReader(json));
            } catch (FileNotFoundException e) {
                System.out.println("file di lettura json non trovato");
            }
        }


    public ArrayList<jsonTransiction> getJsontrans() {
        return jsontrans;
    }


    public jsonTransiction getSingleTransiction(int index){
            return jsontrans.get(index);
        }

        public abstract void SetUpObserver();

        public void StartBuilding(jsonTransiction publCardsingle){
            setChanged();
            notifyObservers(publCardsingle);
        }

    public Gson getGson() {
        return gson;
    }

    public BufferedReader getBr() {
        return br;
    }
}
