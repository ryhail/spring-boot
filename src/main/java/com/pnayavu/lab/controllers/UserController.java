package com.pnayavu.lab.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value="/", produces = "application/json")
    public String userHome() {
        return "secured user method";
    }
}
