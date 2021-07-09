package com.z8q.interfaces;

import com.z8q.dto.CardDTO;
import com.z8q.dto.StatusMessage;

public interface HandlerCardInterface {

    StatusMessage checkPAN(String pan);
    StatusMessage checkFormFactor(String form);
    StatusMessage checkChip(String chip);
    StatusMessage checkPin(String pin);
    void sendCardToIo(CardDTO send);

}
