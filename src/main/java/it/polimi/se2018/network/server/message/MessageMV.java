package it.polimi.se2018.network.server.message;

import it.polimi.se2018.view.View;

import java.io.Serializable;

public interface MessageMV extends Serializable {
    void accept(View view);
}

