package com.z8q.handler;

import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.ClientHandler;
import com.z8q.interfaces.ClientInput;
import com.z8q.properties.MyStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ClientHandlerImpl implements ClientHandler {

    ClientInput clientInput;

    private final static Pattern CHECK_NAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z '-]*$");

    public ClientHandlerImpl(ClientInput clientInput) {
        this.clientInput = clientInput;
    }

    private boolean checkLastName(String lastnameInput, StringBuilder builder) {
        boolean success = CHECK_NAME_PATTERN.matcher(lastnameInput).matches();
        if (!success) {
            builder.append("Lastname contains invalid characters\n");
        }
        return success;
    }
    private boolean checkFirstName(String firstnameInput, StringBuilder builder) {
        boolean success = CHECK_NAME_PATTERN.matcher(firstnameInput).matches();
        if (!success) {
            builder.append("Firstname contains invalid characters\n");
        }
        return success;
    }
    private boolean checkMiddleName(String middlenameInput, StringBuilder builder) {
        boolean success = CHECK_NAME_PATTERN.matcher(middlenameInput).matches();
        if (!success) {
            if(middlenameInput.equals("")) {
                success = true;
            } else  {
                builder.append("Middlename contains invalid characters\n");
            }
        }
        return success;
    }
    private boolean checkBirthDate(String birthDateInput, StringBuilder builder) {
        boolean success;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        date.setLenient(false);

        try {
            Date javaDate = date.parse(birthDateInput);
        } catch (ParseException e) {
            builder.append("Wrong date format\n");
        }
        success = true;
        return success;
    }

    @Override
    public MyStatus checkClientDTO(ClientDTO clientDTO){
        MyStatus checkClientStatus = new MyStatus();
        StringBuilder sb = new StringBuilder();

        boolean lastname = checkLastName(clientDTO.getLastname(), sb);
        boolean firstname = checkFirstName(clientDTO.getFirstname(), sb);
        boolean middlename = checkMiddleName(clientDTO.getMiddlename(), sb);
        boolean birthdate = checkBirthDate(clientDTO.getDate(), sb);


        if (!lastname || !firstname ||
                !middlename || !birthdate) {
            String cvs = sb.toString();

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
        MyStatus status = null;
        if ((cardId.matches("[-+]?\\d+")) && (clientId.matches("[-+]?\\d+"))) {
            status = clientInput.createClientObjectWithUpdatedCardList(cardId, clientId);
            if (!status.isStatus()) {
                status.setMessage("Wrong id format");
            }
        }
        return status;
    }
}
