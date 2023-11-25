package com.sachuk.keu.database.service;

import com.sachuk.keu.controllers.rest.dto.FinanceSourceRequestDTO;
import com.sachuk.keu.controllers.rest.dto.FinanceSourceResponseDTO;
import com.sachuk.keu.database.repositories.FinanceSourceRepository;
import com.sachuk.keu.entities.FinanceSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceSourceService {
    private FinanceSourceRepository financeSourceRepository;

    public FinanceSourceService(FinanceSourceRepository financeSourceRepository) {
        this.financeSourceRepository = financeSourceRepository;
    }

    public List<FinanceSourceResponseDTO> getAllFinanceSources(){
        return financeSourceRepository.findAll().stream().map(FinanceSourceResponseDTO::new).collect(Collectors.toList());
    }
    public FinanceSource getFinanceSourceById(Long id) {
        return financeSourceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Finance source with id: " + id + " is not found"));
    }

    public Long createFinanceSource(FinanceSourceRequestDTO financeSourceRequestDTO) {
        FinanceSource financeSource = new FinanceSource(financeSourceRequestDTO.getName(),
                financeSourceRequestDTO.getSum(), financeSourceRequestDTO.getCurrencyType());
        return financeSourceRepository.save(financeSource).getId();
    }

    public FinanceSourceResponseDTO updateFinanceSource(Long id, FinanceSourceRequestDTO financeSourceRequestDTO) {
        FinanceSource financeSource = new FinanceSource(financeSourceRequestDTO.getName(),
                financeSourceRequestDTO.getSum(), financeSourceRequestDTO.getCurrencyType());
        financeSource.setId(id);
        return new FinanceSourceResponseDTO(financeSourceRepository.save(financeSource));
    }

    public FinanceSourceResponseDTO deleteFinanceSource(Long id){
        FinanceSource financeSourceById = getFinanceSourceById(id);
        financeSourceRepository.deleteById(id);
        return new FinanceSourceResponseDTO(financeSourceById);
    }

}
