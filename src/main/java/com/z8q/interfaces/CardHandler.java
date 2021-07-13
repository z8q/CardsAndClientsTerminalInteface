package com.z8q.interfaces;

import com.z8q.dto.CardDTO;
import com.z8q.propeties.MyStatus;

public interface CardHandler {

    MyStatus checkPAN(String pan);
    MyStatus checkFormFactor(String form);
    MyStatus checkChip(String chip);
    MyStatus checkPin(String pin);
//    void showCardList();
    MyStatus checkCardDTO(CardDTO send);

}
