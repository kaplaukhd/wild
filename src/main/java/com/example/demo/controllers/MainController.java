package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wildberries/")
public class MainController {

    @GetMapping("products")
    public String getMainPage() {
        return "index";
    }

    @GetMapping("cards")
    public String cardPage() {
        return "products";
    }
}
