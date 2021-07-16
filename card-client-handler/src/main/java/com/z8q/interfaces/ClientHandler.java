package com.z8q.interfaces;

import com.z8q.dto.ClientDTO;
import com.z8q.properties.MyStatus;

public interface ClientHandler {

    MyStatus checkClientDTO(ClientDTO clientDTO);
    MyStatus checkPossibilityToLinkCardToClient(String cardId, String clientId);
}
