package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.GarrisonRepository;
import com.sachuk.keu.entities.Garrison;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GarrisonService {
    private final GarrisonRepository garrisonRepository;

    private final MilitaryManService militaryManService;

    public List<Garrison> findAll() {
        return garrisonRepository.findAll();
    }

    public Garrison findById(Long id) {
        return garrisonRepository.getOne(id);
    }

    public Garrison findByRegion(String region){
        return findByRegion(region);
    }

    public Garrison save(Garrison garrison) {
//        militaryManService.findAll().stream()
//                .filter(x -> x.getWork().getGarrison().equals(garrison))
//                .forEach(militaryManService::calculateCompensation);
        return garrisonRepository.save(garrison);
    }

    public void delete(Garrison garrison) {
        garrisonRepository.delete(garrison);
    }

    public boolean existsById(Long id) {
        return garrisonRepository.existsById(id);
    }

    public void deleteById(Long id) {
        garrisonRepository.deleteById(id);
    }

    public Garrison saveAndFlush(Garrison garrison) {
//        militaryManService.findAll().stream()
//                .filter(x -> x.getWork().getGarrison().equals(garrison))
//                .forEach(militaryManService::calculateCompensation);
        return garrisonRepository.saveAndFlush(garrison);
    }

    public void flush() {
        garrisonRepository.flush();
    }

    public void saveAll(Iterable<Garrison> garrisons) {
//        garrisons.forEach(garrison -> militaryManService.findAll().stream()
//                .filter(militaryMan -> militaryMan.getWork().getGarrison().equals(garrison))
//                .forEach(militaryManService::calculateCompensation));
        garrisonRepository.saveAll(garrisons);
    }

    public long count() {
        return garrisonRepository.count();
    }

    public void deleteAll() {
        garrisonRepository.deleteAll();
    }


}
