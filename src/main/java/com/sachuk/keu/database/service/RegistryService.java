package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.RegistryRepository;
import com.sachuk.keu.entities.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistryService {
    private final RegistryRepository registryRepository;

    public RegistryService(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public List<Entry> findAll() {
        return registryRepository.findAll();
    }

    public Entry findById(Long id) {
        return registryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Registry with id: " + id + " is not found"));
    }

    public Entry save(Entry entry) {
        return registryRepository.save(entry);
    }

    public void delete(Entry entry) {
        registryRepository.delete(entry);
    }

    public boolean existsById(Long id) {
        return registryRepository.existsById(id);
    }

    public void deleteById(Long id) {
        registryRepository.deleteById(id);
    }

    public Entry saveAndFlush(Entry entry) {
        return registryRepository.saveAndFlush(entry);
    }

    public void flush() {
        registryRepository.flush();
    }

    public void saveAll(Iterable<Entry> registries) {
        registryRepository.saveAll(registries);
    }

    public long count() {
        return registryRepository.count();
    }

    public void deleteAll() {
        registryRepository.deleteAll();
    }
}
