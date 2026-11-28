package org.kenny.spring.bean5;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;

@Controller
public class ClientController {
    @Resource
    private ClientService clientService;

    public void saveClient() {
        clientService.save();
    }

    @Override
    public String toString() {
        return "ClientController{" +
                "clientService=" + clientService +
                '}';
    }
}
