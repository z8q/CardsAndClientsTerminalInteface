package com.z8q.handler;

import com.z8q.dto.CardDTO;
import com.z8q.interfaces.CardHandler;
import com.z8q.propeties.MyStatus;

public class CardHandlerImpl implements CardHandler {

    @Override
    public MyStatus checkPAN(String cardNumber16DigitsInput) {
        MyStatus status = new MyStatus();
        if (!cardNumber16DigitsInput.matches("^\\d{16}$")) {
            status.setStatus(false);
            status.setMessage("Wrong card number\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkFormFactor(String realOrVirtualInput){
        MyStatus status = new MyStatus();
        if(!realOrVirtualInput.equals("REAL") && !realOrVirtualInput.equals("VIRTUAL")) {
            status.setStatus(false);
            status.setMessage("Wrong answer to FormFactor question\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkChip(String hasAChipInput){
        MyStatus status = new MyStatus();
        if(!hasAChipInput.equals("yes") && !hasAChipInput.equals("no")) {
            status.setStatus(false);
            status.setMessage("Wrong answer to question about chip\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkPin(String pinInput){
        MyStatus status = new MyStatus();
        if(!pinInput.matches("^\\d{4}$")) {
            status.setStatus(false);
            status.setMessage("Wrong type of pin\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkCardDTO(CardDTO cardDTO){
        MyStatus checkCardStatus = new MyStatus();
        StringBuilder sb = new StringBuilder();

        MyStatus pan = checkPAN(cardDTO.getPan());
        MyStatus formFactor = checkFormFactor(cardDTO.getFormFactor());
        MyStatus chip = checkChip(cardDTO.getChip());
        MyStatus pin = checkPin(cardDTO.getPinCode());

        if (!pan.isStatus() || !formFactor.isStatus() ||
                    !chip.isStatus() || !pin.isStatus()) {
            sb.append(pan.getMessage());
            sb.append(formFactor.getMessage());
            sb.append(chip.getMessage());
            sb.append(pin.getMessage());
            String cvs = sb.toString().replaceAll("null", "");
            System.out.println(cvs);
            checkCardStatus.setStatus(false);
        } else {
            checkCardStatus.setStatus(true);
        }
        return checkCardStatus;
    }
}
