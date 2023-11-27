package com.sachuk.keu.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public ResponseEntity<?> fromDiiaSign() {
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }

    @GetMapping("/diia/documents")
    public ResponseEntity<?> docs(){
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }

    @PostMapping("/diia/documents")
    public ResponseEntity<?> fromDiiaDocs() {
        class Test{
            public boolean success = true;
        }
        return ResponseEntity.ok(new Test());
    }
}
