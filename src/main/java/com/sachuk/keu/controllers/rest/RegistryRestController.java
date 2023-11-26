package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.RegistryService;
import com.sachuk.keu.entities.Entry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registry")
public class RegistryRestController {
    private final RegistryService registryService;

    public RegistryRestController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @GetMapping("/")
    public List<Entry> findAll() {
        return registryService.findAll();
    }

    @GetMapping("/{id}")
    public Entry findById(@PathVariable Long id) {
        return registryService.findById(id);
    }

    @PostMapping("/")
    public Entry save(@RequestBody Entry entry) {
        return registryService.save(entry);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<Entry> registries) {
        registryService.saveAll(registries);
    }

    @DeleteMapping("/")
    public void delete(@RequestBody Entry entry) {
        registryService.delete(entry);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        registryService.deleteById(id);
    }


    @DeleteMapping("/all")
    public void deleteAll() {
        registryService.deleteAll();
    }
}
