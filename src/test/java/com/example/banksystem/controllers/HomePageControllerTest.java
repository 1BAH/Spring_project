package com.example.banksystem.controllers;

import com.example.banksystem.models.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    HomePageController homePageController = new HomePageController();

    @Test
    void mainPage() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setServerPort(port);

        ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(attrs);

        Client client = new Client();
        Assertions.assertEquals(client, homePageController.mainPage("test"));
    }
}
