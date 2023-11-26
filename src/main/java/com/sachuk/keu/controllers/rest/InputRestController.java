package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.database.service.RankService;
import com.sachuk.keu.database.service.WorkService;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Rank;
import com.sachuk.keu.entities.Work;
import com.sachuk.keu.entities.enums.Provided;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

// for admin //
@RestController
@Transactional
@AllArgsConstructor
@RequestMapping("/api/v1")
public class InputRestController {
//    public MilitaryManService militaryManService;
//    public RankService rankService;
//    public WorkService workService;
//
//    private Collection<Rank> ranks = rankService.findAll().stream()
//            .sorted(Comparator.comparing(Rank::getNameRank)).collect(Collectors.toList());
//    private Collection<Work> works = workService.findAll().stream()
//            .sorted(Comparator.comparing(Work::getWorkPlace)).collect(Collectors.toList());
//
//    @GetMapping("/edit/{id}")
//    public MilitaryMan edit(@PathVariable("id") String id) {
//        MilitaryMan militaryMan = militaryManService.findById(Long.parseLong(id));
//        if (Objects.equals(militaryMan.getFlatFileNumber(), "0"))
//            militaryMan.setFlatFileNumber(null);
//        if (Objects.equals(militaryMan.getFamilyWar2022(), null))
//            militaryMan.setFamilyWar2022(FamilyWar2022.NO);
//        if (Objects.equals(militaryMan.getProvided(), null))
//            militaryMan.setProvided(Provided.NO);
//        return militaryMan;
//    }
//
//    @PostMapping("/input")
//    public MilitaryMan saveMilitaryMan(@RequestBody MilitaryMan militaryMan) {
//        if (militaryMan.getRank().getId() == 0L)
//            militaryMan.setRank(ranks.stream().filter(r -> Objects.equals(r.getNameRank(), militaryMan.getRank().getNameRank())).findFirst().get());
//        if (militaryMan.getWork().getId() == 0L)
//            militaryMan.setWork(works.stream().filter(w -> Objects.equals(w.getWorkPlace(), militaryMan.getWork().getWorkPlace())).findFirst().get());
//        if (militaryMan.getRankType() == null)
//            militaryMan.setRankType(militaryMan.getRank().getRankType().getName());
//        if (militaryMan.getQuotaType() == null)
//            militaryMan.setQuotaType(militaryMan.getQuota().getQuotaType().getName());
//        if (militaryMan.getAddress() == "")
//            militaryMan.setAddress(null);
//        if (militaryMan.getFamily() == "")
//            militaryMan.setFamily(null);
//        if (militaryMan.getInfo() == "")
//            militaryMan.setInfo(null);
//        if (militaryMan.getFlatFileNumber() == "")
//            militaryMan.setFlatFileNumber(null);
//        militaryMan.setUpdateDate(LocalDateTime.now());
//        return militaryManService.save(militaryMan);
//    }
//
    @GetMapping("/test")
    public String edit() {
        return "militaryMan";
    }

}
