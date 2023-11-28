package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.database.service.RankService;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.database.service.WorkService;
import com.sachuk.keu.entities.MilitaryMan;
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

    @GetMapping("/{id}")
    public MilitaryMan edit(@PathVariable("id") Long id) {
        return militaryManService.findById(id).orElseThrow(
                () -> new IllegalArgumentException("military man with id: " + id + " is not found"));
    }

    @PostMapping("/")
    public MilitaryMan saveMilitaryMan(@RequestBody MilitaryMan militaryMan) {
        if (!militaryMan.isRegistrated()){
            // find superbabka and send notification
        }
        return militaryManService.save(militaryMan);
    }

    @PutMapping("/{id}")
    public MilitaryMan editMilitaryMan(@RequestBody MilitaryMan militaryMan, @PathVariable("id") Long id) {
        return militaryManService.save(militaryMan);
    }

}
