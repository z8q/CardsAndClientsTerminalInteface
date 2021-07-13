package com.z8q.interfaces;

import com.z8q.dto.CardDTO;
import com.z8q.model.Card;
import com.z8q.properties.MyStatus;

import java.util.List;

public interface CardInput {
    MyStatus save(Card card);
    MyStatus createCardObject(CardDTO cardDTO);
}
