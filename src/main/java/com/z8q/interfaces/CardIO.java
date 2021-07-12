package com.z8q.interfaces;

import com.z8q.models.Card;
import com.z8q.propeties.MyStatus;

public interface CardIO {
    void getCardById(Card card);
    void getAll();
    MyStatus save(Card card);
    MyStatus createCardObject(String cardNumber16DigitsInput, String formFactor, String isHasAChipArg, String pinInput);
}
