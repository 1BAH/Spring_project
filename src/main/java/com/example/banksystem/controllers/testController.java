package com.example.banksystem.controllers;

import com.example.banksystem.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class testController {
    @GetMapping("/try")
    @ResponseBody
    public Map<String, String> sendGetRequest() {
        Map<String, String> response = new LinkedHashMap<>();

        response.put("1", String.valueOf(Foo.getBar()));
        response.put("2", "2");
        response.put("3", "6");
        response.put("4", "24");

        return response;
    }

    @GetMapping("/test")
    public  String r(Model model) {
        model.addAttribute("title", "test");
        return "test";
    }
}
