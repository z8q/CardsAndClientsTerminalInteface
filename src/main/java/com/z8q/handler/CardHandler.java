package com.z8q.handler;

import com.z8q.dto.CardDTO;
import com.z8q.interfaces.CardCheck;
import com.z8q.interfaces.CardIO;
import com.z8q.propeties.MyStatus;

public class CardHandler implements CardCheck {

    CardIO cardIO;
    StringBuilder sb = new StringBuilder();

    public CardHandler(CardIO cardIO) {
        this.cardIO = cardIO;
    }

    @Override
    public MyStatus checkPAN(String cardNumber16DigitsInput) {
        MyStatus status = new MyStatus();
        if (!cardNumber16DigitsInput.matches("^\\d{16}$")) {
            status.setStatus(false);
            status.setMessage("Wrong card number\n");
            sb.append(status.getMessage());
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
            sb.append(status.getMessage());
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
            sb.append(status.getMessage());
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
            sb.append(status.getMessage());
        } else {
            status.setStatus(true);
        }
        return status;
    }
    @Override
    public void showCardList() {
        cardIO.getAll();
    }
    @Override
    public boolean saveCard(CardDTO cardDTO){
        checkPAN(cardDTO.getPan());
        checkFormFactor(cardDTO.getFormFactor());
        checkChip(cardDTO.getChip());
        checkPin(cardDTO.getPinCode());

        if (!checkPAN(cardDTO.getPan()).isStatus() || !checkFormFactor(cardDTO.getFormFactor()).isStatus() ||
                    !checkChip(cardDTO.getChip()).isStatus() || !checkPin(cardDTO.getPinCode()).isStatus()) {
            System.out.println(sb);
            return false;
        } else {
            cardIO.createCardObject(cardDTO.getPan(), cardDTO.getFormFactor(), cardDTO.getChip(), cardDTO.getPinCode());
            return true;
        }
    }
}
