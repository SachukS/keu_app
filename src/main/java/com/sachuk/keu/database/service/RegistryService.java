package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.RegistryRepository;
import com.sachuk.keu.entities.Registry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistryService {
    private final RegistryRepository registryRepository;

    public RegistryService(RegistryRepository registryRepository) {
        this.registryRepository = registryRepository;
    }

    public List<Registry> findAll() {
        return registryRepository.findAll();
    }

    public Registry findById(Long id) {
        return registryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Registry with id: " + id + " is not found"));
    }


    public Registry save(Registry registry) {
        return registryRepository.save(registry);
    }
    public List<Registry> findByReceivedFlat(String garrison) {
        return registryRepository.findByReceivedFlat(garrison).stream()
                .filter(r -> r.getMilitaryMan().getWork().getGarrison().equals(garrison))
                .collect(Collectors.toList());
    }

    public List<Registry> findByReceivedMoney(String garrison) {
        return registryRepository.findByReceivedMoney(garrison).stream()
                .filter(r -> r.getMilitaryMan().getWork().getGarrison().equals(garrison))
                .collect(Collectors.toList());
    }


    public void delete(Registry registry) {
        registryRepository.delete(registry);
    }

    public boolean existsById(Long id) {
        return registryRepository.existsById(id);
    }

    public void deleteById(Long id) {
        registryRepository.deleteById(id);
    }

    public Registry saveAndFlush(Registry registry) {
        return registryRepository.saveAndFlush(registry);
    }

    public void flush() {
        registryRepository.flush();
    }

    public void saveAll(Iterable< Registry > registries) {
        registryRepository.saveAll(registries);
    }

    public long count() {
        return registryRepository.count();
    }

    public void deleteAll() {
        registryRepository.deleteAll();
    }
}
