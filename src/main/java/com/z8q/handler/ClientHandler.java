package com.z8q.handler;

import com.z8q.dto.ClientDTO;
import com.z8q.dto.StatusMessage;
import com.z8q.interfaces.ClientInterface;
import com.z8q.service.CreateOperations;

public class ClientHandler  {

    StatusMessage status = new StatusMessage();

    public StatusMessage checkLastName(String lastnameInput) {
        if (!lastnameInput.matches("^[a-zA-Zа-яА-я]+$")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }

    public StatusMessage checkFirstName(String firstNameInput) {
        if (!firstNameInput.matches("^[a-zA-Zа-яА-я]+$")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }

    public StatusMessage checkMiddleName(String middlenameInput) {
        if (!middlenameInput.matches("^[a-zA-Zа-яА-я]+$")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }

    public StatusMessage checkBirthDate(String birthDateInput) {
        if (!birthDateInput.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }
    public void sendCardToIo(ClientDTO clientDTO){
        CreateOperations createOperations = new CreateOperations();
        createOperations.createClientObject(clientDTO.getLastname(), clientDTO.getFirstname(), clientDTO.getMiddlename(), clientDTO.getDate());
    }
}
