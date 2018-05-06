package it.polimi.se2018.Util;

public interface Observer<T> {

    public void update(T event);

}