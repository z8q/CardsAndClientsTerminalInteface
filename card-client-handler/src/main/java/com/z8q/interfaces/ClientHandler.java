package com.z8q.interfaces;

import com.z8q.dto.ClientDTO;
import com.z8q.properties.MyStatus;

public interface ClientHandler {

    MyStatus checkLastName(String lastnameInput);
    MyStatus checkFirstName(String firstNameInput);
    MyStatus checkMiddleName(String middlenameInput);
    MyStatus checkBirthDate(String birthDateInput);
    MyStatus checkClientDTO(ClientDTO clientDTO);
    MyStatus checkPossibilityToLinkCardToClient(String cardId, String clientId);
}
