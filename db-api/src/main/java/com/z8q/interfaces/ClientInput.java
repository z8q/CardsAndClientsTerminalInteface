package com.z8q.interfaces;

import com.z8q.dto.ClientDTO;
import com.z8q.model.Client;
import com.z8q.properties.MyStatus;

public interface ClientInput {
    MyStatus save(Client client);
    void linkCardToClient(Client client, int cardId);
    void createClientObject(ClientDTO clientDTO);
    MyStatus createClientObjectWithUpdatedCardList(String cardId, String clientId);
}
