package org.example.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        String message = "Hello";
        return ResponseEntity.ok(message);
    }
}
