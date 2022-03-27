package com.z8q.handler;

import com.z8q.dto.CardDTO;
import com.z8q.interfaces.CardHandler;
import com.z8q.interfaces.CardInput;
import com.z8q.properties.MyStatus;

import java.util.regex.Pattern;

public class CardHandlerImpl implements CardHandler {

    CardInput cardInput;

    private final static Pattern CHECK_PAN_PATTERN = Pattern.compile("^\\d{16}$");
    private final static Pattern CHECK_PIN_PATTERN = Pattern.compile("^\\d{4}$");

    public CardHandlerImpl(CardInput cardInput) { this.cardInput = cardInput; }

    private boolean checkPAN(String cardNumber16DigitsInput, StringBuilder builder) {
        boolean success = CHECK_PAN_PATTERN.matcher(cardNumber16DigitsInput).matches();
        if (!success) {
            builder.append("Card number contains invalid characters\n");
        }
        return success;
    }

    private boolean checkFormFactor(String realOrVirtualInput ,StringBuilder builder){
        boolean success = false;
        if(!realOrVirtualInput.equals("1") && !realOrVirtualInput.equals("2")) {
            builder.append("Answer to FormFactor question contains invalid characters\n");
        } else {
            success = true;
        }
        return success;
    }

    private boolean checkChip(String hasAChipInput, StringBuilder builder){
        boolean success = false;
        if(!hasAChipInput.equals("yes") && !hasAChipInput.equals("no")) {
            builder.append("Answer to chip question contains invalid characters\n");
        } else {
            success = true;
        }
        return success;
    }

    public boolean checkPin(String pinInput, StringBuilder builder){
        boolean success = CHECK_PIN_PATTERN.matcher(pinInput).matches();
        if (!success) {
            builder.append("PIN contains invalid characters\n");
        }
        return success;
    }

    @Override
    public MyStatus checkCardDTO(CardDTO cardDTO){
        MyStatus checkCardStatus = new MyStatus();
        StringBuilder sb = new StringBuilder();

        boolean pan = checkPAN(cardDTO.getPan(), sb);
        boolean formFactor = checkFormFactor(cardDTO.getFormFactor(), sb);
        boolean chip = checkChip(cardDTO.getChip(), sb);
        boolean pin = checkPin(cardDTO.getPinCode(), sb);

        if (!pan || !formFactor ||
                    !chip || !pin) {
            String cvs = sb.toString();
            checkCardStatus.setStatus(false);
            checkCardStatus.setMessage(cvs);
        } else {
            cardInput.createCardObject(cardDTO);
            checkCardStatus.setStatus(true);
        }
        return checkCardStatus;
    }
}
