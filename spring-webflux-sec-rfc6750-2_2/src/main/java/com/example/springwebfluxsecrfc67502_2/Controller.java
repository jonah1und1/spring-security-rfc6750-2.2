package com.example.springwebfluxsecrfc67502_2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class Controller {

    @PostMapping(path = "/post")
    public Mono<ResponseEntity<String>> postSecurely() {
        return Mono.just(ResponseEntity.ok("Hello Post World"));
    }

    @GetMapping(path = "/get")
    public Mono<ResponseEntity<String>> getSecurely() {
        return Mono.just(ResponseEntity.ok("Hello Get World"));
    }


}
