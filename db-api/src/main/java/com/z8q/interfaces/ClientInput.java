package com.z8q.interfaces;

import com.z8q.dto.ClientDTO;
import com.z8q.model.Card;
import com.z8q.model.Client;
import com.z8q.properties.MyStatus;

import java.util.List;

public interface ClientInput {
    MyStatus save(Client client);
    MyStatus linkCardToClient(Client client, int cardId);
    MyStatus createClientObject(ClientDTO clientDTO);
    MyStatus createClientObjectWithUpdatedCardList(String cardId, String clientId);
}
