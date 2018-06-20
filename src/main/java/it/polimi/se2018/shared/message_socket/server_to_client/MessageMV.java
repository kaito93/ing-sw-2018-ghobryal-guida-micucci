package it.polimi.se2018.shared.message_socket.server_to_client;

import it.polimi.se2018.client.view.View;

import java.io.Serializable;

/**
 * interface that manage the message_socket between Model and View
 */

public interface MessageMV extends Serializable {
    void accept(View view);
}

