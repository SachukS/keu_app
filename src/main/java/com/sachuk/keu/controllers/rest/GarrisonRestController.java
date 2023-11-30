package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.GarrisonService;
import com.sachuk.keu.entities.Garrison;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/garrisons")
public class GarrisonRestController {
    private final GarrisonService garrisonService;


    @GetMapping("/")
    public List<Garrison> findAll() {
        return garrisonService.findAll();
    }

    @GetMapping("/{id}")
    public Garrison findById(@PathVariable Long id) {
        return garrisonService.findById(id);
    }

    @GetMapping("/{region}")
    public Garrison findById(@PathVariable String region) {
        return garrisonService.findByRegion(region);
    }

    @PostMapping("/")
    public Garrison save(@RequestBody Garrison garrison) {
        return garrisonService.save(garrison);
    }

//    @PostMapping("/all")
//    public void saveAll(Iterable<Garrison> garrisons) {
//        garrisonService.saveAll(garrisons);
//    }
//
//    @DeleteMapping("/")
//    public void delete(@RequestBody Garrison garrison) {
//        garrisonService.delete(garrison);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        garrisonService.deleteById(id);
//    }
//
//
//    @DeleteMapping("/all")
//    public void deleteAll() {
//        garrisonService.deleteAll();
//    }

}
