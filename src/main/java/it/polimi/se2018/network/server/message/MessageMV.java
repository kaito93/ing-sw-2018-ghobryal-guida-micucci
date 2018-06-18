package it.polimi.se2018.network.server.message;

import it.polimi.se2018.view.View;

import java.io.Serializable;

/**
 * interface that manage the message between Model and View
 */

public interface MessageMV extends Serializable {
    void accept(View view);
}

