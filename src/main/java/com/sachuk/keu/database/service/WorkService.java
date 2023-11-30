package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.WorkRepository;
import com.sachuk.keu.entities.Work;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkService {

    private final WorkRepository workRepository;


    public List<Work> findAll() {
        return workRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    public Work findById(Long id) {
        return workRepository.getOne(id);
    }

    public Work save(Work work) {
        return workRepository.save(work);
    }

    public void delete(Work work) {
        workRepository.delete(work);
    }

    public boolean existsById(Long id) {
        return workRepository.existsById(id);
    }

    public void deleteById(Long id) {
        workRepository.deleteById(id);
    }

    public Work saveAndFlush(Work work) {
        return workRepository.saveAndFlush(work);
    }

    public void flush() {
        workRepository.flush();
    }

    public void saveAll(Iterable<Work> works) {
        workRepository.saveAll(works);
    }

    public long count() {
        return workRepository.count();
    }

    public void deleteAll() {
        workRepository.deleteAll();
    }

}
