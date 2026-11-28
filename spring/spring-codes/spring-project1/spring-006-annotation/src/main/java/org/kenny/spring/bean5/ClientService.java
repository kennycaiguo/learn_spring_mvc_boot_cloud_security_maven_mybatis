package org.kenny.spring.bean5;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Resource(name = "clientDao")
    private ClientDao clientDao;

    public void save() {
        clientDao.insert();
    }

    @Override
    public String toString() {
        return "ClientService{" +
                "clientDao=" + clientDao +
                '}';
    }
}
