package com.sachuk.keu.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainRestController {
    @GetMapping("/")
    public ResponseEntity<?> hello(){
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }
}
