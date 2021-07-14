package com.z8q.interfaces;

import com.z8q.model.Client;

import java.util.List;

public interface ClientOutput {
    Client getClientById(Long clientIndex);
    List<Client> getAll();
}
