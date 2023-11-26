package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.ProvidedFlatService;
import com.sachuk.keu.entities.ProvidedFlat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/providedFlat")
public class ProvidedFlatRestController {

    private final ProvidedFlatService providedFlatService;

    public ProvidedFlatRestController(ProvidedFlatService providedFlatService) {
        this.providedFlatService = providedFlatService;
    }

    @GetMapping("/")
    public List<ProvidedFlat> findAll() {
        return providedFlatService.findAll();
    }

    @GetMapping("/{id}")
    public ProvidedFlat findById(@PathVariable Long id) {
        return providedFlatService.findById(id);
    }

    @PostMapping("/")
    public ProvidedFlat save(@RequestBody ProvidedFlat providedFlat) {
        return providedFlatService.save(providedFlat);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<ProvidedFlat> providedFlats) {
        providedFlatService.saveAll(providedFlats);
    }

    @DeleteMapping("/")
    public void delete(@RequestBody ProvidedFlat providedFlat) {
        providedFlatService.delete(providedFlat);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        providedFlatService.deleteById(id);
    }


    @DeleteMapping("/all")
    public void deleteAll() {
        providedFlatService.deleteAll();
    }
}
