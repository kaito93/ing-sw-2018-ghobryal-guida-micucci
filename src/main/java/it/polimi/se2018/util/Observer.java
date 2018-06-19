package it.polimi.se2018.util;

/**
 * Class that manage the observers
 * @param <T> the observed object
 */
public interface Observer<T> {

    /**
     * method called by notify
     * @param event the object observed
     */
    void update(T event);

}