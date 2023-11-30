package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.QuotaService;
import com.sachuk.keu.entities.Quota;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/quotas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuotaRestController {
    private final QuotaService quotaService;


    @GetMapping("/")
    public List<Quota> findAll() {
        return quotaService.findAll();
    }

    @GetMapping("/{id}")
    public Quota findById(@PathVariable Long id) {
        return quotaService.findById(id);
    }

    @PostMapping("/")
    public Quota save(@RequestBody Quota quota) {
        return quotaService.save(quota);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<Quota> quotas) {
        quotaService.saveAll(quotas);
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
