package com.z8q.interfaces;

import com.z8q.models.Client;

public interface ClientInterface {
    void getClientById(Client client);
    void getAll(Client client);
    void save(Client client);
    void linkCardToClient(Client client, int cardId);
}
