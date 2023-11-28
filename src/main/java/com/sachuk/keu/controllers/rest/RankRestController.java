package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.RankService;
import com.sachuk.keu.entities.Rank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ranks")
public class RankRestController {
    private final RankService rankService;


    @GetMapping("/")
    public List<Rank> findAll() {
        return rankService.findAll();
    }

    @GetMapping("/{id}")
    public Rank findById(@PathVariable Long id) {
        return rankService.findById(id);
    }

    @PostMapping("/")
    public Rank save(@RequestBody Rank rank) {
        return rankService.save(rank);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<Rank> ranks) {
        rankService.saveAll(ranks);
    }

    @DeleteMapping("/")
    public void delete(@RequestBody Rank rank) {
        rankService.delete(rank);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        rankService.deleteById(id);
    }


    @DeleteMapping("/all")
    public void deleteAll() {
        rankService.deleteAll();
    }
}
