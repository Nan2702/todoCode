package com.todocode.PruebaTecSupermercado.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hola Nancy, tu backend está vivo!";
    }

}
