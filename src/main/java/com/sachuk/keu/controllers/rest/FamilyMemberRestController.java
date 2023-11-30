package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.FamilyMemberService;
import com.sachuk.keu.entities.FamilyMember;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/familyMember")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FamilyMemberRestController {
    private final FamilyMemberService familyMemberService;

    public FamilyMemberRestController(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }


//    @GetMapping("/")
//    public List<FamilyMember> findAll() {
//        return familyMemberService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public FamilyMember findById(@PathVariable Long id) {
//        return familyMemberService.findById(id);
//    }
//
//    @PostMapping("/")
//    public FamilyMember save(@RequestBody FamilyMember familyMember) {
//        return familyMemberService.save(familyMember);
//    }
//
//    @PostMapping("/all")
//    public void saveAll(Iterable<FamilyMember> familyMembers) {
//        familyMemberService.saveAll(familyMembers);
//    }
//
//    @DeleteMapping("/")
//    public void delete(@RequestBody FamilyMember familyMember) {
//        familyMemberService.delete(familyMember);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Long id) {
//        familyMemberService.deleteById(id);
//    }
//
//
//    @DeleteMapping("/all")
//    public void deleteAll() {
//        familyMemberService.deleteAll();
//    }
}
