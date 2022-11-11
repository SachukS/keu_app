package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.CustomerRepository;
import com.sachuk.keu.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer findById(Long id) {
        return customerRepository.getOne(id);
    }

    public List<Customer> findByIdSet(List<Long> customerID) {
        return customerID.stream().map(this::findById).collect(Collectors.toList());
    }

    public List<Customer> freeFind(String query) {
        return customerRepository.freeSearch(query);
    }

    public List<Customer> findByGarrison(String query) {
        switch (query) {
            case "KYIV":
                query = "м.Київ";
                break;
            case "BORI":
                query = "Бориспiль";
                break;
            case "SEMI":
                query = "Семиполки";
                break;
            case "PERE":
                query = "Переяславський";
                break;
            case "BROV":
                query = "Бровари";
                break;
            case "GOST":
                query = "Гостомель";
                break;
            case "VASY":
                query = "Василькiв";
                break;
        }
        return customerRepository.findAllByGarrison(query);
    }

    public List<Customer> getTop() {
        return customerRepository.findFirst20ByOrderByAccountingDate();
    }

    public List<Customer> findAllByQuotaType(String quotaType) {
        return customerRepository.findAllByQuotaType(quotaType);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer saveAndFlush(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    public void saveAll(Iterable<Customer> customers) {
        customerRepository.saveAll(customers);
    }

    public void flush() {
        customerRepository.flush();
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }

    public long count() {
        return customerRepository.count();
    }

    public long countAllAfterDate(LocalDateTime afterDate) {
        return customerRepository.countByUpdateDateAfter(afterDate);
    }

    public List<Customer> getAllToday() {
        return customerRepository.findAllByUpdateDateAfter(LocalDate.now().atStartOfDay());
    }


    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }


}