package com.pnayavu.lab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
}
