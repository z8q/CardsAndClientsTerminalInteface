package com.z8q.handler;

import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.ClientHandler;
import com.z8q.propeties.MyStatus;

public class ClientHandlerImpl implements ClientHandler {

//    ClientIO clientIO;
//
//    public ClientHandler(ClientIO clientIO) {
//        this.clientIO = clientIO;
//    }

    @Override
    public MyStatus checkLastName(String lastnameInput) {
        MyStatus status = new MyStatus();
        if (!lastnameInput.matches("^[a-zA-Zа-яА-я]+$")) {
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
        if (!firstNameInput.matches("^[a-zA-Zа-яА-я]+$")) {
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
        if (!middlenameInput.matches("^[a-zA-Zа-яА-я]+$")) {
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
        if (!birthDateInput.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
            status.setStatus(false);
            status.setMessage("Wrong birth date\n");
        } else {
            status.setStatus(true);
        }
        return status;
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateInput);
//            if (!birthDateInput.equals(date)) {
//                statusBirthDate = false;
//            } else {
//                statusBirthDate = true;
//            }
//            return statusBirthDate;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
//    @Override
//    public MyStatus addCardToClient(String cardId, String clientId) {
//        MyStatus statusLinkCardToClient = new MyStatus();
//        if (!clientIO.createClientObjectWithUpdatedCardList(cardId, clientId)) {
//            statusLinkCardToClient.setStatus(false);
//            System.out.println("Card not linked");
//        } else {
//            statusLinkCardToClient.setStatus(true);
//        }
//        return statusLinkCardToClient;
//    }
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
        } else {
            checkClientStatus.setStatus(true);
        }
        return checkClientStatus;
    }
}
