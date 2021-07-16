package com.z8q.interfaces;

import com.z8q.dto.CardDTO;
import com.z8q.properties.MyStatus;

public interface CardHandler {

    MyStatus checkCardDTO(CardDTO send);

}
