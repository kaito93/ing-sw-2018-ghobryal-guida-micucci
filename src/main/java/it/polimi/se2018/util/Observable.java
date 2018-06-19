package it.polimi.se2018.util;

import java.util.ArrayList;
import java.util.List;

/**
 * method that manage an observable class
 * @param <T> the observed object
 */
public abstract class Observable<T> {

    private List<it.polimi.se2018.util.Observer> observers; // lista di oggetti osservati

    /**
     * constructor class
     */
    public Observable(){
        this.observers= new ArrayList<>();

    }

    /**
     * method that add an observer for this observable object
     * @param obs an observer
     */
    public void addObservers(it.polimi.se2018.util.Observer obs)
    {
        this.observers.add(obs); // aggiungo un osservato

    }

    /**
     * method that call the method update of the observers
     * @param event the event observed
     */
    public void notifyObservers (T event){
        for (int i=0; i<this.observers.size();i++)  // per ogni osservatore
            this.observers.get(i).update(event);  // notifico un evento

    }


}
