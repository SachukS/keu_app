package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.services.notification.MessengerNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndexRestController {
    @Autowired
    public MessengerNotificationService messengerNotificationService;

    @GetMapping("/")
    public ResponseEntity<?> hello(){
        messengerNotificationService.sendNotification("+380932072704", "Вітаємо, раді повідомити вам, що ви піднялися на одну позицію в загальній черзі!!!");
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
