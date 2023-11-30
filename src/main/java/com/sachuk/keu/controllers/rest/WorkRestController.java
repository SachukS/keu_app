package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.WorkService;
import com.sachuk.keu.entities.Work;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/works")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WorkRestController {
    private final WorkService workService;


    @GetMapping("/")
    public List<Work> findAll() {
        return workService.findAll();
    }

    @GetMapping("/{id}")
    public Work findById(@PathVariable Long id) {
        return workService.findById(id);
    }

    @PostMapping("/")
    public Work save(@RequestBody Work quota) {
        return workService.save(quota);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<Work> quotas) {
        workService.saveAll(quotas);
    }

//    @DeleteMapping("/")
//    public void delete(@RequestBody Quota quota) {
//        quotaService.delete(quota);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        quotaService.deleteById(id);
//    }
//
//
//    @DeleteMapping("/all")
//    public void deleteAll() {
//        quotaService.deleteAll();
//    }
}

