package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.RegistryService;
import com.sachuk.keu.entities.Registry;
import com.sachuk.keu.services.queue.QueueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registry")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RegistryRestController {
    private final RegistryService registryService;

    public RegistryRestController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @GetMapping("/")
    public List<Registry> findAll() {
        return registryService.findAll();
    }

    @GetMapping("/{garrison}/received/{receivedType}")
    public Page<Registry> getReceivedQueue(@PathVariable String garrison,
                                           @PathVariable String receivedType,
                                           @RequestParam(required = false, defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "50") int size) {
        List<Registry> queue = QueueService.getReceivedQueue(garrison, receivedType);
        Pageable pageable;
        if (size == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), queue.size());

        List<Registry> pageContent = queue.subList(start, end);
        return new PageImpl<>(pageContent, pageable, queue.size());
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
