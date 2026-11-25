package org.kenny.spring.service;

import org.kenny.spring.dao.ClientDao;
import org.kenny.spring.entity.Client;

public class ClientService {
    private ClientDao clientDao;

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public void saveClient() {
        clientDao.insert();
    }
    @Override
    public String toString() {
        return "ClientService{" +
                "clientDao=" + clientDao +
                '}';
    }
}
