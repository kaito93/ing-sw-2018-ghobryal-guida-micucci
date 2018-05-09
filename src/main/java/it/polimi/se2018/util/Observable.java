package it.polimi.se2018.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public abstract class Observable<T> {

    List<it.polimi.se2018.util.Observer> observers; // lista di oggetti osservati

    public Observable(){
        this.observers= new ArrayList<>();

    }

    public void addObservers(it.polimi.se2018.util.Observer obs)
    {
        this.observers.add(obs); // aggiungo un osservato

    }

    public void removeObserver(it.polimi.se2018.util.Observer obs){
        this.observers.remove(obs); // rimuovo un osservato
    }

    public void notifyObservers (T event){
        for (int i=0; i<this.observers.size();i++)  // per ogni osservatore
            this.observers.get(i).update(event);  // notifico un evento

    }


}
