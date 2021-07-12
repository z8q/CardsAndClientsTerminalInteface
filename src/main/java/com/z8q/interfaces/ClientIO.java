package com.z8q.interfaces;

import com.z8q.models.Client;

public interface ClientIO {
    void getClientById(Client client);
    void getAll();
    void save(Client client);
    void linkCardToClient(Client client, int cardId);
    void createClientObject(String lastnameInput, String firstnameInput, String middlenameInput, String birthDateInput);
    boolean createClientObjectWithUpdatedCardList(String idCardNumber, String clientId);
}
