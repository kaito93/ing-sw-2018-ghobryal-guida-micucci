package it.polimi.se2018.network.client.message;

import java.io.Serializable;

public class Message implements Serializable {

    public static final int MVEVENT=0;
    public static final int CVEVENT=1;
    public static final int SYSTEMEVENT=2;

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
