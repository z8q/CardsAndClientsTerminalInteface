package com.z8q.handler;

import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.ClientHandler;
import com.z8q.interfaces.ClientInput;
import com.z8q.properties.MyStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandlerImpl implements ClientHandler {

    ClientInput clientInput;

    public ClientHandlerImpl(ClientInput clientInput) {
        this.clientInput = clientInput;
    }

    @Override
    public MyStatus checkLastName(String lastnameInput) {
        MyStatus status = new MyStatus();
        if (!lastnameInput.matches("^[a-zA-Z][a-zA-Z '-]*$")) {
            status.setStatus(false);
            status.setMessage("Wrong lastname\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkFirstName(String firstNameInput) {
        MyStatus status = new MyStatus();
        if (!firstNameInput.matches("^[a-zA-Z][a-zA-Z '-]*$")) {
            status.setStatus(false);
            status.setMessage("Wrong firstname\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkMiddleName(String middlenameInput) {
        MyStatus status = new MyStatus();
        if (!middlenameInput.matches("^[a-zA-Z][a-zA-Z '-]*$")) {
            status.setStatus(false);
            status.setMessage("Wrong middlename\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkBirthDate(String birthDateInput) {
        MyStatus status = new MyStatus();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        date.setLenient(false);
        try {
            Date javaDate = date.parse(birthDateInput);
        } catch (ParseException e) {
            status.setStatus(false);
            status.setMessage("Wrong date format");
            return status;
        }
        status.setStatus(true);
        return status;
    }
    @Override
    public MyStatus checkClientDTO(ClientDTO clientDTO){
        MyStatus checkClientStatus = new MyStatus();
        StringBuilder sb = new StringBuilder();

        MyStatus lastname = checkLastName(clientDTO.getLastname());
        MyStatus firstname = checkFirstName(clientDTO.getFirstname());
        MyStatus middlename = checkMiddleName(clientDTO.getMiddlename());
        MyStatus birthdate = checkBirthDate(clientDTO.getDate());


        if (!lastname.isStatus() || !firstname.isStatus() ||
                !middlename.isStatus() || !birthdate.isStatus()) {
            sb.append(lastname.getMessage());
            sb.append(firstname.getMessage());
            sb.append(middlename.getMessage());
            sb.append(birthdate.getMessage());
            String cvs = sb.toString().replaceAll("null", "");
            System.out.println(cvs);
            checkClientStatus.setStatus(false);
            checkClientStatus.setMessage(cvs);
        } else {
            clientInput.createClientObject(clientDTO);
            checkClientStatus.setStatus(true);
        }
        return checkClientStatus;
    }
    @Override
    public MyStatus checkPossibilityToLinkCardToClient(String cardId, String clientId){
        MyStatus status = new MyStatus();
        if ((cardId.matches("[-+]?\\d+")) && (clientId.matches("[-+]?\\d+"))) {
            status.setStatus(clientInput.createClientObjectWithUpdatedCardList(cardId, clientId).isStatus());
            return status;
        } else {
            status.setStatus(false);
            status.setMessage("Wrong id format");
            return status;
        }
    }
}
