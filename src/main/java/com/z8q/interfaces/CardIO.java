package com.z8q.interfaces;

import com.z8q.models.Card;
import com.z8q.propeties.MyStatus;

import java.util.List;

public interface CardIO {
    Card getCardById(Long cardIndex);
    List<Card> getAll();
    MyStatus save(Card card);
    MyStatus createCardObject(String cardNumber16DigitsInput, String formFactor, String isHasAChipArg, String pinInput);
}
