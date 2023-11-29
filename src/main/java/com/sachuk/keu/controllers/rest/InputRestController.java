package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.*;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Registry;
import com.sachuk.keu.entities.payload.InputPayload;
import com.sachuk.keu.services.notification.MessengerNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

// for admin //
@RestController
@Transactional
@AllArgsConstructor
@RequestMapping("/api/v1/input")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InputRestController {
    public MilitaryManService militaryManService;
    public UserService userService;
    public RankService rankService;
    public WorkService workService;
    public RegistryService registryService;
    public MessengerNotificationService messengerNotificationService;

    @GetMapping("/{id}")
    public MilitaryMan edit(@PathVariable("id") Long id) {
        return militaryManService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("military man with id: " + id + " is not found"));
    }

    @PostMapping("/")
    public MilitaryMan saveMilitaryMan(@RequestBody InputPayload inputPayload) {
        MilitaryMan mm = militaryManService.save(inputPayload.getMilitaryMan());
        if (inputPayload.getRegistry() != null) {
            Registry registry = inputPayload.getRegistry();
            registry.setMilitaryMan(mm);
            registry.setFlatFileNumber(mm.getApartmentFileNumber());
            registryService.save(registry);
            messengerNotificationService.sendNotification("+380932072704", "Вітаємо, раді повідомити вам");
        }
        return mm;
    }

}
