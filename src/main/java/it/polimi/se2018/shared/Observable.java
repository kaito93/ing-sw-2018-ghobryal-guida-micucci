package it.polimi.se2018.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * method that manage an observable class
 * @param <T> the observed object
 * @author Samuele Guida
 */
public abstract class Observable<T> {

    private List<Observer> observers; // lista di oggetti osservati

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
    public void addObservers(Observer obs)
    {
        this.observers.add(obs); // aggiungo un osservato

    }

    /**
     * method that call the method update of the observers
     * @param event the event observed
     */
    public void notifyObservers (T event){
        for (Observer observer : this.observers) observer.update(event);  // notifico un evento

    }


}
