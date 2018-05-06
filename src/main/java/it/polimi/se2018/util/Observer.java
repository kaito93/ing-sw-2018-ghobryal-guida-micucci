package it.polimi.se2018.util;

public interface Observer<T> {

    public void update(T event);

}