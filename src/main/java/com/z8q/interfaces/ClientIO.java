package com.z8q.interfaces;

import com.z8q.models.Client;
import com.z8q.propeties.MyStatus;

import java.util.ArrayList;
import java.util.List;

public interface ClientIO {
    Client getClientById(Long clientIndex);
    List<Client> getAll();
    MyStatus save(Client client);
    MyStatus linkCardToClient(Client client, int cardId);
    MyStatus createClientObject(String lastnameInput, String firstnameInput, String middlenameInput, String birthDateInput);
    MyStatus createClientObjectWithUpdatedCardList(String idCardNumber, String clientId);
}
