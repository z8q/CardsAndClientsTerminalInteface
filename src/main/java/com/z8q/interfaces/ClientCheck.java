package com.z8q.interfaces;

import com.z8q.dto.ClientDTO;
import com.z8q.propeties.MyStatus;

public interface ClientCheck {

    MyStatus checkLastName(String lastnameInput);
    MyStatus checkFirstName(String firstNameInput);
    MyStatus checkMiddleName(String middlenameInput);
    MyStatus checkBirthDate(String birthDateInput);
//    void showClientList();
    boolean addCardToClient(String cardId, String clientId);
    boolean checkClientDTO(ClientDTO clientDTO);
}
