package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.RegistryService;
import com.sachuk.keu.entities.Registry;
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
    public List<Registry> findAll() {
        return registryService.findAll();
    }

    @GetMapping("/{id}")
    public Registry findById(@PathVariable Long id) {
        return registryService.findById(id);
    }

    @PostMapping("/")
    public Registry save(@RequestBody Registry registry) {
        return registryService.save(registry);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<Registry> registries) {
        registryService.saveAll(registries);
    }

    @DeleteMapping("/")
    public void delete(@RequestBody Registry registry) {
        registryService.delete(registry);
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
