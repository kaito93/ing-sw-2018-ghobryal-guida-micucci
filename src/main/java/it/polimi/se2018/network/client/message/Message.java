package it.polimi.se2018.network.client.message;


import java.io.Serializable;

public class Message implements Serializable {

    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMEVENT=2;
    private static final long serialVersionUID = 392490809315164554L;

    private int type;
    private Object event;

    /**
     * method constructor
     * @param type the type of event
     * @param event the event/message
     */
    public Message(int type, Object event){

        this.event=event;
        this.type=type;

    }

    /**
     * method that get the message
     * @return message
     */
    public Object getEvent(){
        return event;
    }

    /**
     * method that get the type of the message
     * @return an integer
     */
    public int getType(){
        return type;
    }
}
