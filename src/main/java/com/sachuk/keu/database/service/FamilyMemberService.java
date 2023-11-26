package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.FamilyMemberRepository;
import com.sachuk.keu.entities.FamilyMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberService {
    private final FamilyMemberRepository familyMemberRepository;

    public FamilyMemberService(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    public List<FamilyMember> findAll() {
        return familyMemberRepository.findAll();
    }

    public FamilyMember findById(Long id) {
        return familyMemberRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Family member with id: " + id + " is not found"));
    }

    public FamilyMember save(FamilyMember familyMember) {
        return familyMemberRepository.save(familyMember);
    }

    public void delete(FamilyMember familyMember) {
        familyMemberRepository.delete(familyMember);
    }

    public boolean existsById(Long id) {
        return familyMemberRepository.existsById(id);
    }

    public void deleteById(Long id) {
        familyMemberRepository.deleteById(id);
    }

    public FamilyMember saveAndFlush(FamilyMember familyMember) {
        return familyMemberRepository.saveAndFlush(familyMember);
    }

    public void flush() {
        familyMemberRepository.flush();
    }

    public void saveAll(Iterable<FamilyMember> familyMembers) {
        familyMemberRepository.saveAll(familyMembers);
    }

    public long count() {
        return familyMemberRepository.count();
    }

    public void deleteAll() {
        familyMemberRepository.deleteAll();
    }
}
