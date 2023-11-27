package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.database.service.RankService;
import com.sachuk.keu.database.service.UserService;
import com.sachuk.keu.database.service.WorkService;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Rank;
import com.sachuk.keu.entities.User;
import com.sachuk.keu.entities.Work;
import com.sachuk.keu.entities.enums.Provided;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public MilitaryMan edit(@PathVariable("id") String id) {
        return militaryManService.findById(Long.parseLong(id)).orElseThrow(
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
