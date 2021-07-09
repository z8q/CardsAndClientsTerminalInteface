package com.z8q.interfaces;

import com.z8q.models.Card;

public interface CardInterface {
    void getCardById(Card card);
    void getAll(Card card);
    void save(Card card);
}
