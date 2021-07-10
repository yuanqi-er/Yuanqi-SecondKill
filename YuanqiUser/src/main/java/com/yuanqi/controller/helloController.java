package com.yuanqi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class helloController {

    @GetMapping
    public String toHelloPage() {
        return "viewUser";
    }
}
