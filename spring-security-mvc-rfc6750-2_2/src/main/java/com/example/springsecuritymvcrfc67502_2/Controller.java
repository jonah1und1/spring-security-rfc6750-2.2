package com.example.springsecuritymvcrfc67502_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

    @PostMapping(path = "/post")
    public ResponseEntity<String> postSecurely() {
        return ResponseEntity.ok("Hello Post World");
    }

    @GetMapping(path = "/get")
    public ResponseEntity<String> getSecurely() {
        return ResponseEntity.ok("Hello Get World");
    }
}
