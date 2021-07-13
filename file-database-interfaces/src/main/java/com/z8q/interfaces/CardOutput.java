package com.z8q.interfaces;

import com.z8q.model.Card;

import java.util.List;

public interface CardOutput {
    Card getCardById(Long cardIndex);
    List<Card> getAll();
}
