package com.example.banksystem.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerTest {
    @LocalServerPort
    private int localPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @InjectMocks
    private ClientController clientController = new ClientController();

    @Test
    void getClient(){
        ClientController clientController = new ClientController();
        clientController.addClient("name", "surname", "passport", "address");
        assertNull(restTemplate.getForObject("http://localhost:" + localPort + "/profile/data/0", AccountController.class));

        final HttpHeaders headers = new HttpHeaders();
        headers.set("test1", "test2");

        final HttpEntity<AccountController> entity = new HttpEntity<>(headers);

        Assertions.assertEquals(clientController, restTemplate.exchange("http://localhost:" + localPort + "/profile/data/0", HttpMethod.GET, entity, AccountController.class).getBody());
    }
}
