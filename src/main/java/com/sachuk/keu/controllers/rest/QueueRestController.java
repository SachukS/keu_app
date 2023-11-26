package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.entities.Entry;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.services.queue.QueueService;
import com.sachuk.keu.services.queue.QueueXlsCreateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Transactional
@AllArgsConstructor
@RequestMapping("/api/v1/queue")
public class QueueRestController {
    public MilitaryManService militaryManService;
    private QueueXlsCreateService queueXlsCreateService;
    private static List<MilitaryMan> staticMilitaryMEN = new ArrayList<>();
    public static List<String> garrisons = Arrays.asList("м.Київ", "Бориспiль", "Семиполки", "Переяславський", "Бровари", "Гостомель", "Василькiв");

    @GetMapping("/{garrison}/{queueType}")
    public Page<MilitaryMan> getQueue(@PathVariable String garrison,
                                      @PathVariable String queueType,
                                      @RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam(required = false, defaultValue = "50") int size) {
        staticMilitaryMEN = QueueService.getQueue(garrison, queueType);
        Pageable pageable;
        if (size == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), staticMilitaryMEN.size());

        List<MilitaryMan> pageContent = staticMilitaryMEN.subList(start, end);
        return new PageImpl<>(pageContent, pageable, staticMilitaryMEN.size());
    }

    @GetMapping("/{garrison}/received/{receivedType}")
    public Page<Entry> getReceivedQueue(@PathVariable String garrison,
                                        @PathVariable String receivedType,
                                        @RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "50") int size) {
        ///TODO implement case for received type
        List<Entry> queue = QueueService.getReceivedQueue(garrison, receivedType);
        Pageable pageable;
        if (size == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(page, size);
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), queue.size());

        List<Entry> pageContent = queue.subList(start, end);
        return new PageImpl<>(pageContent, pageable, queue.size());
    }


    //admin
//    @PostMapping("/calculate")
//    public void calculateQueue() {
//        List<MilitaryMan> militaryMEN;
//        List<MilitaryMan> customersPersho;
//        List<MilitaryMan> customersPoza;
//
//        MilitaryMan militaryMan = new MilitaryMan();
//        for (String gar : garrisons) {
//            militaryMEN = militaryManService.findByGarrison(gar);
//            //customers = customers.stream().filter(c -> c.getRegistrated().equals(Registrated.YES)).sorted(Comparator.comparing(Customer::getAccountingDate)).collect(Collectors.toList());
//            militaryMEN = militaryMEN.stream().filter(c -> c.getRegistrated().equals(Registrated.YES)).sorted(Comparator.comparing(MilitaryMan::getAccountingDate).reversed()
//                            .thenComparing(MilitaryMan::getQuotaType).reversed()
//                            .thenComparing(c -> c.getQuotaDate() != null ? c.getQuotaDate() : c.getAccountingDate()))
//                    .collect(Collectors.toList());
//            for (int i = 0; i < militaryMEN.size(); i++) {
//                militaryMEN.get(i).setZagalna(String.valueOf(i + 1));
//            }
//            militaryManService.saveAll(militaryMEN);
//            customersPersho = new ArrayList<>(militaryMEN);
//
//            customersPoza = militaryMEN.stream()
//                    .filter(c -> c.getQuotaType().equals("позачерговий"))
//                    .sorted(Comparator.comparing(MilitaryMan::getQuotaDate)
//                            .thenComparing(MilitaryMan::getAccountingDate))
//                    .collect(Collectors.toList());
//            for (int i = 0; i < customersPoza.size(); i++) {
//                customersPoza.get(i).setPilgova(String.valueOf(i + 1));
//            }
//            militaryManService.saveAll(customersPoza);
//
//            customersPersho = customersPersho.stream()
//                    .filter(c -> c.getQuotaType().equals("першочерговий"))
//                    .sorted(Comparator.comparing(MilitaryMan::getQuotaDate)
//                            .thenComparing(MilitaryMan::getAccountingDate))
//                    .collect(Collectors.toList());
//            for (int i = 0; i < customersPersho.size(); i++) {
//                customersPersho.get(i).setPilgova(String.valueOf(i + 1));
//            }
//            militaryManService.saveAll(customersPersho);
//        }
//    }
}
