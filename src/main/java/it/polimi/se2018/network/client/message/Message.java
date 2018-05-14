package it.polimi.se2018.network.client.message;

import java.io.Serializable;

public class Message implements Serializable {

    public static final int mvEvent=0;
    public static final int cvEvent=1;
    public static final int systemMessage=2;

    int type;
    Object event;

    public Message(int type, Object event){

        this.event=event;
        this.type=type;

    }
    public Object getEvent(){
        return event;
    }

    public int getType(){
        return type;
    }
}
