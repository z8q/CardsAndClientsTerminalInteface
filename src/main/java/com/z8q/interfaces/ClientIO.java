package com.z8q.interfaces;

import com.z8q.models.Client;
import com.z8q.propeties.MyStatus;

import java.util.ArrayList;
import java.util.List;

public interface ClientIO {
    void getClientById(Client client);
    void getAll();
    MyStatus save(Client client);
    MyStatus linkCardToClient(Client client, int cardId);
    MyStatus createClientObject(String lastnameInput, String firstnameInput, String middlenameInput, String birthDateInput);
    boolean createClientObjectWithUpdatedCardList(String idCardNumber, String clientId);
}
