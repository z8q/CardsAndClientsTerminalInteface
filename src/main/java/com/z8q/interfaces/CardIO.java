package com.z8q.interfaces;

import com.z8q.models.Card;

public interface CardIO {
    void getCardById(Card card);
    void getAll();
    void save(Card card);
    void createCardObject(String cardNumber16DigitsInput, String formFactor, String isHasAChipArg, String pinInput);
}
