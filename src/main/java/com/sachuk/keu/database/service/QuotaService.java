package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.QuotaRepository;
import com.sachuk.keu.entities.Quota;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuotaService {

    private final QuotaRepository quotaRepository;

    public List<Quota> findAll() {
        return quotaRepository.findAll();
    }

    public Quota findById(Long id) {
        return quotaRepository.getOne(id);
    }

    public Quota save(Quota quota) {
        return quotaRepository.save(quota);
    }

    public void delete(Quota quota) {
        quotaRepository.delete(quota);
    }

    public boolean existsById(Long id) {
        return quotaRepository.existsById(id);
    }

    public void deleteById(Long id) {
        quotaRepository.deleteById(id);
    }

    public Quota saveAndFlush(Quota quota) {
        return quotaRepository.saveAndFlush(quota);
    }

    public void flush() {
        quotaRepository.flush();
    }

    public void saveAll(Iterable<Quota> quota) {
        quotaRepository.saveAll(quota);
    }

    public long count() {
        return quotaRepository.count();
    }

    public void deleteAll() {
        quotaRepository.deleteAll();
    }

}