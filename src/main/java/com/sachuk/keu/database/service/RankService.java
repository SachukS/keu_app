package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.RankRepository;
import com.sachuk.keu.entities.Rank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RankService {

    private final RankRepository rankRepository;

    public List<Rank> findAll() {
        return rankRepository.findAll();
    }

    public Rank findById(Long id) {
        return rankRepository.getOne(id);
    }

    public Rank save(Rank rank) {
        return rankRepository.save(rank);
    }

    public void delete(Rank rank) {
        rankRepository.delete(rank);
    }

    public boolean existsById(Long id) {
        return rankRepository.existsById(id);
    }

    public void deleteById(Long id) {
        rankRepository.deleteById(id);
    }

    public Rank saveAndFlush(Rank rank) {
        return rankRepository.saveAndFlush(rank);
    }

    public void flush() {
        rankRepository.flush();
    }

    public void saveAll(Iterable<Rank> rank) {
        rankRepository.saveAll(rank);
    }

    public long count() {
        return rankRepository.count();
    }

    public void deleteAll() {
        rankRepository.deleteAll();
    }

}
