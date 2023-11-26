package com.sachuk.keu.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndexRestController {
    @GetMapping("/")
    public ResponseEntity<?> hello(){
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }
}
