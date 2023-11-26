package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.SearchQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@Transactional
@AllArgsConstructor
@RequestMapping("/api/v1/find")
public class FindRestController {
    private MilitaryManService militaryManService;

    @RequestMapping("/")
    public Page<MilitaryMan> findMilitaryMan(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "50") int size,
                                             @RequestParam(required = false) SearchQuery searchQuery) {
        Pageable pageable;
        if (size == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }
        return militaryManService.freeFind(searchQuery.getQuery(), pageable);
    }

    @RequestMapping("/findToday")
    public Page<MilitaryMan> findToday(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "50") int size) {
        Pageable pageable;
        if (size == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }
        return militaryManService.getAllToday(pageable);
    }
}
