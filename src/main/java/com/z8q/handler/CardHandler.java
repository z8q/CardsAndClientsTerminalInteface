package com.z8q.handler;

import com.z8q.dto.CardDTO;
import com.z8q.dto.StatusMessage;
import com.z8q.interfaces.HandlerCardInterface;
import com.z8q.service.CreateOperations;

public class CardHandler implements HandlerCardInterface {

    StatusMessage status = new StatusMessage();

    @Override
    public StatusMessage checkPAN(String cardNumber16DigitsInput) {
        StatusMessage status = new StatusMessage();
        if (!cardNumber16DigitsInput.matches("^\\d{16}$")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }
    @Override
    public StatusMessage checkFormFactor(String realOrVirtualInput){
        if(!realOrVirtualInput.equals("REAL") && !realOrVirtualInput.equals("VIRTUAL")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }
    @Override
    public StatusMessage checkChip(String hasAChipInput){
        if(!hasAChipInput.equals("yes") && !hasAChipInput.equals("no")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }
    @Override
    public StatusMessage checkPin(String pinInput){
        if(!pinInput.matches("^\\d{4}$")) {
            status.setCompletion(false);
        } else {
            status.setCompletion(true);
        }
        return status;
    }
    @Override
    public void sendCardToIo(CardDTO cardDTO){
        CreateOperations createOperations = new CreateOperations();
        createOperations.createCardObject(cardDTO.getPan(), cardDTO.getFormFactor(), cardDTO.getChip(), cardDTO.getPinCode());
    }
}
