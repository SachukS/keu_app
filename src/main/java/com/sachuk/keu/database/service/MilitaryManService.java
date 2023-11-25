package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.MilitaryManRepository;
import com.sachuk.keu.entities.MilitaryMan;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MilitaryManService {

//    private final MilitaryManRepository militaryManRepository;
//
//    public List<MilitaryMan> findAll() {
//        return militaryManRepository.findAll();
//    }
//
//    public Page<MilitaryMan> findAll(Pageable pageable) {
//        return militaryManRepository.findAll(pageable);
//    }
//
//    public MilitaryMan findById(Long id) {
//        return militaryManRepository.getOne(id);
//    }
//
//    public List<MilitaryMan> findByIdSet(List<Long> customerID) {
//        return customerID.stream().map(this::findById).collect(Collectors.toList());
//    }
//
//    public Page<MilitaryMan> freeFind(String query, Pageable pageable) {
//        return militaryManRepository.freeSearch(query, pageable);
//    }
//
//    public List<MilitaryMan> findByGarrison(String query) {
////        switch (query) {
////            case "KYIV":
////                query = "м.Київ";
////                break;
////            case "BORI":
////                query = "Бориспiль";
////                break;
////            case "SEMI":
////                query = "Семиполки";
////                break;
////            case "PERE":
////                query = "Переяславський";
////                break;
////            case "BROV":
////                query = "Бровари";
////                break;
////            case "GOST":
////                query = "Гостомель";
////                break;
////            case "VASY":
////                query = "Василькiв";
////                break;
////        }
//        return militaryManRepository.findAllByGarrison(query);
//    }
//
//    public List<MilitaryMan> getTop() {
//        return militaryManRepository.findFirst20ByOrderByAccountingDate();
//    }
//
//    public List<MilitaryMan> findAllByQuotaType(String quotaType) {
//        return militaryManRepository.findAllByQuotaType(quotaType);
//    }
//
//    public MilitaryMan save(MilitaryMan militaryMan) {
//        return militaryManRepository.save(militaryMan);
//    }
//
//    public MilitaryMan saveAndFlush(MilitaryMan militaryMan) {
//        return militaryManRepository.saveAndFlush(militaryMan);
//    }
//
//    public void saveAll(Iterable<MilitaryMan> customers) {
//        militaryManRepository.saveAll(customers);
//    }
//
//    public void flush() {
//        militaryManRepository.flush();
//    }
//
//    public void delete(MilitaryMan militaryMan) {
//        militaryManRepository.delete(militaryMan);
//    }
//
//    public void deleteById(Long id) {
//        militaryManRepository.deleteById(id);
//    }
//
//    public void deleteAll() {
//        militaryManRepository.deleteAll();
//    }
//
//    public long count() {
//        return militaryManRepository.count();
//    }
//
//    public long countAllAfterDate(LocalDateTime afterDate) {
//        return militaryManRepository.countByUpdateDateAfter(afterDate);
//    }
//
//    public Page<MilitaryMan> getAllToday(Pageable pageable) {
//        return militaryManRepository.findAllByUpdateDateAfter(LocalDate.now().atStartOfDay(), pageable);
//    }
//
//
//    public boolean existsById(Long id) {
//        return militaryManRepository.existsById(id);
//    }


}