package com.z8q.handler;

import com.z8q.dto.ClientDTO;
import com.z8q.interfaces.ClientCheck;
import com.z8q.interfaces.ClientIO;
import com.z8q.propeties.MyStatus;

public class ClientHandler implements ClientCheck {

    ClientIO clientIO;
    StringBuilder sb = new StringBuilder();

    public ClientHandler(ClientIO clientIO) {
        this.clientIO = clientIO;
    }

    @Override
    public MyStatus checkLastName(String lastnameInput) {
        MyStatus status = new MyStatus();
        if (!lastnameInput.matches("^[a-zA-Zа-яА-я]+$")) {
            status.setStatus(false);
            status.setMessage("Wrong lastname\n");
            sb.append(status.getMessage());
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
            sb.append(status.getMessage());
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
            sb.append(status.getMessage());
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
            sb.append(status.getMessage());
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
//    public void showClientList() {
//        clientIO.getAll();
//    }

    @Override
    public boolean addCardToClient(String cardId, String clientId) {
        boolean statusLinkCardToClient;
        if (!clientIO.createClientObjectWithUpdatedCardList(cardId, clientId)) {
            statusLinkCardToClient = false;
            System.out.println("Card not linked");
        } else {
            statusLinkCardToClient = true;
        }
        return statusLinkCardToClient;
    }

    @Override
    public boolean checkClientDTO(ClientDTO clientDTO){
        MyStatus lastname = checkLastName(clientDTO.getLastname());
        MyStatus firstname = checkFirstName(clientDTO.getFirstname());
        MyStatus middlename = checkMiddleName(clientDTO.getMiddlename());
        MyStatus birthdate = checkBirthDate(clientDTO.getDate());

        if (!lastname.isStatus() || !firstname.isStatus() ||
                !middlename.isStatus() || !birthdate.isStatus()) {
            System.out.println(sb);
            return false;
        } else {
            return clientIO.createClientObject(clientDTO.getLastname(), clientDTO.getFirstname(),
                    clientDTO.getMiddlename(), clientDTO.getDate()).isStatus();
        }
    }
}
