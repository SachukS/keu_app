package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.CustomerRepository;
import com.sachuk.keu.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;



//    public Customer setAllFieldsEnrollee(Customer customer) {
//        customer.setName(customer.getName().trim());
//        customer.setSurname(customer.getSurname().trim());
//        customer.setThirdName(customer.getThirdName().trim());
//
//        customer.setRank(customer.getRank());
//        customer.setRankType(customer.getRankType());
//
//
//        if(customer.getBirthDay()==null){
//            try {
//                enrollee.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse("1957-01-01"));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        enrollee.getCertificate().setEnrollee(enrollee);
//        enrollee.getSpecialties().forEach(specialty -> specialty.setEnrollee(enrollee));
//        enrollee.getZnoCertificates().forEach(znoCertificate -> {
//            znoCertificate.setEnrollee(enrollee);
//        });
//        System.out.println();
//        enrollee.setSpecialties(enrollee.getSpecialties().stream().filter(a->a.getSpecialty()!=null).collect(Collectors.toList()));
//        System.out.println();
//        enrollee.getZnoCertificates().forEach(znoCertificate -> {
//            znoCertificate.getZnoEvaluations().forEach(znoEvaluation -> {
//                znoEvaluation.setZnoCertificate(znoCertificate);
//                znoEvaluation.getZnoDiscipline().setZnoEvaluations(new HashSet<>(znoCertificate.getZnoEvaluations()));
//            });
//        });
//        enrollee.getPrivileges().forEach(privilege -> privilege.setEnrollee(enrollee));
//
//        return enrollee;
//    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
//    public List<Customer> findAllWithCriteria(Criteria criteria){
//        Specification<Customer> checkForwardAndStudyForm = criteria.getForwards().isEmpty() && (criteria.getStudyForm() != null) ?
//                checkStudyForm(criteria) :
//                criteria.getStudyForm() == null && !criteria.getForwards().isEmpty() ?
//                        checkForward(criteria) :
//                        criteria.getStudyForm() != null && !criteria.getForwards().isEmpty() ?
//                                checkStudyForm(criteria).or(checkForward(criteria))
//                                : null;
//
//        Specification<Enrollee> checkSocialStatusAndLyceum = criteria.getIsLyceum() == null && !criteria.getSocialStatus().isEmpty() ?
//                checkSocialStatus(criteria) :
//                criteria.getIsLyceum() != null && criteria.getSocialStatus().isEmpty() ?
//                        checkLiceum(criteria) :
//                        criteria.getIsLyceum() != null && !criteria.getSocialStatus().isEmpty() ?
//                                checkLiceum(criteria).or(checkSocialStatus(criteria))
//                                : null;
//        Specification<Enrollee> specification =
//                Specification.where(checkZNOCertificate(criteria))
//                        .and(checkForwardAndStudyForm)
//                        .and(checkSocialStatusAndLyceum)
//                        .and(checkGender(criteria))
//                        .and(checkMedicalInspection(criteria))
//                        .and(checkPhysicalTraining(criteria))
//                        .and(checkProfessionalSelection(criteria))
//                        .and(checkPrivileges(criteria))
//                        .and(checkPriorities(criteria));
//
//        return customerRepository.findAll(specification);
//    }

    public Customer findById(Long id) {
        return customerRepository.getOne(id);
    }
    public List<Customer> findByIdSet(List<Long> enrolleeId) {
        return enrolleeId.stream().map(this::findById).collect(Collectors.toList());
    }
//    public Customer findEnrolleeByPersonalFile(String personalFile) { return customerRepository.findEnrolleeByPersonalFile(personalFile); }
//    public Customer findEnrolleeByCertificate(Certificate certificate) { return customerRepository.findEnrolleeByCertificateId(certificate.getId()); }
//    public List<Customer> findBySpecialty(Specialty specialty) {
//        return customerRepository.findEnrolleesBySpecialties(specialty);
//    }
//    public List<Customer> findBetweenDates(LocalDateTime start, LocalDateTime end) {
//        return customerRepository.findEnrolleesByCreateDateTimeAfterAndCreateDateTimeBefore(start,end);
//    }
    public List<Customer> freeFind(String query) {
        return customerRepository.freeSearch(query);
    }
    public List<Customer> getTop() {
        return customerRepository.findFirst20ByOrderByAccountingDate();
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
//    public long countBySocialStatusToday(SocialStatus socialStatus, LocalDateTime date) {
//        return customerRepository.countBySocialStatusAndCreateDateTimeAfter(socialStatus, date);
//    }
//    public long countAllAfterDate(LocalDateTime afterDate) { return customerRepository.countByCreateDateTimeAfter(afterDate); }
//    public long countByGender(Gender gender) {
//        return customerRepository.countByGender(gender);
//    }
//    public long countByChecked(Checked checked) {
//        return customerRepository.countByChecked(checked);
//    }
//    public long countBetween(LocalDateTime start, LocalDateTime end) { return customerRepository.countByCreateDateTimeAfterAndCreateDateTimeBefore(start,end); }
//    public long countBetweenByStatus(LocalDateTime start, LocalDateTime end, SocialStatus socialStatus) {
//        return customerRepository.countByCreateDateTimeAfterAndCreateDateTimeBeforeAndSocialStatus(start,end, socialStatus);
//    }

    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

//    @Deprecated
//    public String getDynamicQuery(Filter filter){
//        StringBuilder buildQuery = new StringBuilder(
//                "SELECT e.id FROM Enrollee as e ");
//        boolean startPoint = true;
//
//        if(!filter.getPrivileges().isEmpty())
//            buildQuery.append(" INNER JOIN Privilege as p ON e.id=p.enrollee.id ");
//        if(!filter.getPriorities().isEmpty())
//            buildQuery.append(" INNER JOIN EnrolleeSpecialty as s ON e.id=s.enrollee.id ");
//
//        buildQuery.append("WHERE ");
//
//
//        buildQuery.append(filter.isMiti() | filter.isVkss() | filter.isExtramural() ? "(" : "");
//        if(filter.isMiti()) {
//            buildQuery.append("e.forward=0 and e.studyForm=0");
//            startPoint = false;
//        }
//        if(filter.isExtramural()){
//            if(filter.isMiti())
//                buildQuery.append(") or (");
//            else if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.forward=0 and e.studyForm=1");
//            startPoint = false;
//        }
//        if(filter.isVkss()){
//            if(filter.isMiti() | filter.isExtramural())
//                buildQuery.append(") or (");
//            else if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.forward=1");
//            startPoint = false;
//        }
//        buildQuery.append(filter.isMiti() | filter.isVkss() | filter.isExtramural() ? ") " : "");
//
//        if(filter.isCm()){
//            if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.socialStatus=0 AND e.licey=0");
//            startPoint = false;
//        }
//        if(filter.isVsk()){
//            if(filter.isCm() | filter.isLyceum())
//                buildQuery.append(" OR ");
//            else if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.socialStatus=1");
//            startPoint = false;
//        }
//        if(filter.isLyceum()){
//            if(filter.isCm() | filter.isVsk())
//                buildQuery.append(" OR ");
//            else if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.licey=1");
//            startPoint = false;
//        }
//        buildQuery.append(filter.isCm() | filter.isVsk() | filter.isLyceum() ? ") " : "");
//
//        if(filter.isMale()){
//            if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.gender=0");
//            startPoint = false;
//        }
//        if(filter.isFemale()){
//            if(filter.isMale())
//                buildQuery.append(" OR ");
//            else if(!startPoint)
//                buildQuery.append("AND (");
//            buildQuery.append("e.gender=1");
//            startPoint = false;
//        }
//        buildQuery.append(filter.isMale() | filter.isFemale() ? ") " : "");
//
//        if(filter.isMedicine()) {
//            if (!startPoint)
//                buildQuery.append("AND ");
//
//            buildQuery.append("(");
//            if (filter.isSuccess())
//                buildQuery.append("e.medicalInspection=1");
//            if (filter.isFail()) {
//                if (filter.isSuccess() | filter.isWait())
//                    buildQuery.append(" OR ");
//                buildQuery.append("e.medicalInspection=2");
//            }
//            if (filter.isWait()) {
//                if (filter.isSuccess() | filter.isFail())
//                    buildQuery.append(" OR ");
//                buildQuery.append("e.medicalInspection=0");
//            }
//
//            buildQuery.append(") ");
//            startPoint = false;
//
//        }
//        if(filter.isPsych()) {
//            if (!startPoint)
//                buildQuery.append("AND ");
//
//            buildQuery.append("(");
//            if (filter.isSuccess())
//                buildQuery.append("e.professionalSelection=1");
//            if (filter.isFail()) {
//                if (filter.isSuccess() | filter.isWait())
//                    buildQuery.append(" OR ");
//                buildQuery.append("e.professionalSelection=2");
//            }
//            if (filter.isWait()) {
//                if (filter.isSuccess() | filter.isFail())
//                    buildQuery.append(" OR ");
//                buildQuery.append("e.professionalSelection=0");
//            }
//
//            buildQuery.append(") ");
//            startPoint = false;
//        }
//        if(filter.isPhysical()) {
//            if (!startPoint)
//                buildQuery.append("AND ");
//
//            buildQuery.append("(");
//            if (filter.isSuccess())
//                buildQuery.append("e.physicalTraining=1");
//            if (filter.isFail()) {
//                if (filter.isSuccess() | filter.isWait())
//                    buildQuery.append(" OR ");
//                buildQuery.append("e.physicalTraining=2");
//            }
//            if (filter.isWait()) {
//                if (filter.isSuccess() | filter.isFail())
//                    buildQuery.append(" OR ");
//                buildQuery.append("e.physicalTraining=0");
//            }
//
//            buildQuery.append(") ");
//            startPoint = false;
//        }
//
//        if(!startPoint) {
//            buildQuery.append("AND ");
//            startPoint = false;
//        }
//
//        buildQuery.append("(");
//        if(filter.getZnoCertificate() == 1)
//            buildQuery.append("e.znoCertificateAbsence=1");
//        else if(filter.getZnoCertificate() == 2)
//            buildQuery.append("e.znoCertificateAbsence=0");
//        else
//            buildQuery.append("e.znoCertificateAbsence=0 OR e.znoCertificateAbsence=1");
//
//        buildQuery.append(") ");
//
//
//
//        if(!filter.getPrivileges().isEmpty()){
//            if(!startPoint)
//                buildQuery.append("AND ");
//            buildQuery.append("p.privilegeDocument.id IN (");
//            for(PrivilegeDocument privilegeDocument : filter.getPrivileges()){
//                buildQuery.append(privilegeDocument.getId()).append(", ");
//            }
//            buildQuery.replace(buildQuery.length()-2, buildQuery.length(), ") ");
//            startPoint = false;
//        }
//        if(!filter.getPriorities().isEmpty()){
//            if(!startPoint)
//                buildQuery.append("AND ");
//            buildQuery.append("s.specialty.id IN (");
//            for(Specialty priorities : filter.getPriorities()){
//                buildQuery.append(priorities.getId()).append(", ");
//            }
//            buildQuery.replace(buildQuery.length()-2, buildQuery.length(), ") ");
//        }
//
//
//        return buildQuery.toString().trim();
//    }



}