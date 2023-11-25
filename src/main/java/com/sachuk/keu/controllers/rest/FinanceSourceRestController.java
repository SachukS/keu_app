package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.controllers.rest.dto.FinanceSourceRequestDTO;
import com.sachuk.keu.controllers.rest.dto.FinanceSourceResponseDTO;
import com.sachuk.keu.database.service.FinanceSourceService;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/financeSources")
public class FinanceSourceRestController {
    private final FinanceSourceService financeSourceService;

    public FinanceSourceRestController(FinanceSourceService financeSourceService) {
        this.financeSourceService = financeSourceService;
    }


    @GetMapping("/")
    public List<FinanceSourceResponseDTO> getAllFinanceSources(){
        return financeSourceService.getAllFinanceSources();
    }

    @GetMapping("/{id}")
    public FinanceSourceResponseDTO getFinanceSource(@PathVariable Long id) {
        return new FinanceSourceResponseDTO(financeSourceService.getFinanceSourceById(id));
    }

    @PostMapping("/")
    public Long createFinanceSource(@RequestBody FinanceSourceRequestDTO financeSourceRequestDTO) {
        return financeSourceService.createFinanceSource(financeSourceRequestDTO);
    }

    @PutMapping("/{id}")
    public FinanceSourceResponseDTO updateFinanceSource(@PathVariable Long id,
                                                        @RequestBody FinanceSourceRequestDTO financeSourceRequestDTO){
        return financeSourceService.updateFinanceSource(id, financeSourceRequestDTO);
    }

    @DeleteMapping("/{id}")
    public FinanceSourceResponseDTO deleteFinanceSource(@PathVariable Long id){
        return financeSourceService.deleteFinanceSource(id);
    }
}
