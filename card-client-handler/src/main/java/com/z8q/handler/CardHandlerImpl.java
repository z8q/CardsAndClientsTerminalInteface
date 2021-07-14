package com.z8q.handler;

import com.z8q.dto.CardDTO;
import com.z8q.interfaces.CardHandler;
import com.z8q.interfaces.CardInput;
import com.z8q.properties.MyStatus;

public class CardHandlerImpl implements CardHandler {

    CardInput cardInput;

    public CardHandlerImpl(CardInput cardInput) {
        this.cardInput = cardInput;
    }

    @Override
    public MyStatus checkPAN(String cardNumber16DigitsInput) {
        MyStatus status = new MyStatus();
        if (!cardNumber16DigitsInput.matches("^\\d{16}$")) {
            status.setStatus(false);
            status.setMessage("Card number contains invalid characters\n");
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public MyStatus checkFormFactor(String realOrVirtualInput){
        MyStatus status = new MyStatus();
        if(!realOrVirtualInput.equals("1") && !realOrVirtualInput.equals("2")) {
            status.setStatus(false);
            status.setMessage("Answer to FormFactor question contains invalid characters\n");
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
            status.setMessage("Answer to chip question contains invalid characters\n");
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
            status.setMessage("PIN contains invalid characters\n");
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
            checkCardStatus.setStatus(false);
            checkCardStatus.setMessage(cvs);
        } else {
            cardInput.createCardObject(cardDTO);
            checkCardStatus.setStatus(true);
        }
        return checkCardStatus;
    }
}
