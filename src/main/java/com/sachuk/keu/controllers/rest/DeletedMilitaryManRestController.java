package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.DeletedMilitaryManService;
import com.sachuk.keu.entities.DeletedMilitaryMan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deletedMilitaryMen")
public class DeletedMilitaryManRestController {

    private final DeletedMilitaryManService deletedMilitaryManService;

    public DeletedMilitaryManRestController(DeletedMilitaryManService deletedMilitaryManService) {
        this.deletedMilitaryManService = deletedMilitaryManService;
    }

    @GetMapping("/")
    public List<DeletedMilitaryMan> findAll() {
        return deletedMilitaryManService.findAll();
    }

    @GetMapping("/{id}")
    public DeletedMilitaryMan findById(@PathVariable Long id) {
        return deletedMilitaryManService.findById(id);
    }

    @PostMapping("/")
    public DeletedMilitaryMan save(@RequestBody DeletedMilitaryMan deletedMilitaryMan) {
        return deletedMilitaryManService.save(deletedMilitaryMan);
    }

    @PostMapping("/all")
    public void saveAll(Iterable<DeletedMilitaryMan> deletedMilitaryMen) {
        deletedMilitaryManService.saveAll(deletedMilitaryMen);
    }

    @DeleteMapping("/")
    public void delete(@RequestBody DeletedMilitaryMan deletedMilitaryMan) {
        deletedMilitaryManService.delete(deletedMilitaryMan);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        deletedMilitaryManService.deleteById(id);
    }


    @DeleteMapping("/all")
    public void deleteAll() {
        deletedMilitaryManService.deleteAll();
    }
}
