package com.sachuk.keu.database.service;

import com.sachuk.keu.database.repositories.MilitaryManRepository;
import com.sachuk.keu.entities.FamilyMember;
import com.sachuk.keu.entities.MilitaryMan;
import com.sachuk.keu.entities.Quota;
import com.sachuk.keu.entities.enums.QuotaType;
import com.sachuk.keu.entities.enums.SexEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MilitaryManService {

    private final MilitaryManRepository militaryManRepository;

    private FamilyMemberService familyMemberService;

    private RankService rankService;

    public List<MilitaryMan> findAll() {
        return militaryManRepository.findAll();
    }

    public Page<MilitaryMan> findAll(Pageable pageable) {
        return militaryManRepository.findAll(pageable);
    }

    public Optional<MilitaryMan> findById(Long id) {
        return militaryManRepository.findById(id);
    }

//    public List<MilitaryMan> findByIdSet(List<Long> customerID) {
//        return customerID.stream().map(this::findById).collect(Collectors.toList());
//    }

    public Page<MilitaryMan> freeFind(String query, Pageable pageable) {
        return militaryManRepository.freeSearch(query, pageable);
    }

    public List<MilitaryMan> findByGarrison(String query) {
        return militaryManRepository.findAllByGarrison(query);
    }

    public List<MilitaryMan> findQueueTypeByGarrison(String garrison, String type) {
        return militaryManRepository.findQueueTypeByGarrison(garrison, type);
    }

    public List<MilitaryMan> getTop() {
        return militaryManRepository.findFirst20ByOrderByAccountingDate();
    }

    public List<MilitaryMan> findAllByQuotaType(String quotaType) {
        return militaryManRepository.findAllByQuotaType(quotaType);
    }

    public MilitaryMan save(MilitaryMan militaryMan) {
        calculateRoomCount(militaryMan);
        if (militaryMan.isWantCompensation())
            calculateCompensation(militaryMan);
        return militaryManRepository.save(militaryMan);
    }

    public MilitaryMan saveAndFlush(MilitaryMan militaryMan) {
        calculateRoomCount(militaryMan);
        if (militaryMan.isWantCompensation())
            calculateCompensation(militaryMan);
        return militaryManRepository.saveAndFlush(militaryMan);
    }

    public void saveAll(Iterable<MilitaryMan> militaryMen) {
        militaryMen.forEach(this::calculateRoomCount);
        militaryMen.forEach(this::calculateCompensation);
        militaryManRepository.saveAll(militaryMen);
    }

    public void flush() {
        militaryManRepository.flush();
    }

    public void delete(MilitaryMan militaryMan) {
        militaryManRepository.delete(militaryMan);
    }

    public void deleteById(Long id) {
        militaryManRepository.deleteById(id);
    }

    public void deleteAll() {
        militaryManRepository.deleteAll();
    }

    public long count() {
        return militaryManRepository.count();
    }

    public long countAllAfterDate(LocalDateTime afterDate) {
        return militaryManRepository.countByUpdateDateAfter(afterDate);
    }

    public Page<MilitaryMan> getAllToday(Pageable pageable) {
        return militaryManRepository.findAllByUpdateDateAfter(LocalDate.now().atStartOfDay(), pageable);
    }


    public boolean existsById(Long id) {
        return militaryManRepository.existsById(id);
    }

    public void calculateRoomCount(MilitaryMan militaryMan) {
        int militaryManRoomCount = militaryMan.getRoomCount();
        List<FamilyMember> familyMembers = familyMemberService.findAll().stream()
                .filter((x) -> x.getId().equals(militaryMan.getId())).collect(Collectors.toList());
        boolean isHaveYoungBoy = familyMembers.stream().anyMatch(x -> x.getSex().equals(SexEnum.MALE) &&
                x.getBirthDate().isBefore(x.getBirthDate().withYear(LocalDate.now().getYear())));
        boolean isHaveYoungGirl = familyMembers.stream().anyMatch(x -> x.getSex().equals(SexEnum.FEMALE) &&
                x.getBirthDate().isBefore(x.getBirthDate().withYear(LocalDate.now().getYear())));
        if (isHaveYoungBoy && isHaveYoungGirl) {
            militaryManRoomCount++;
        }

        boolean isColonel = rankService.findAll().stream().anyMatch(x -> x.getShortName().startsWith("п-к"));
        boolean isGeneral = rankService.findAll().stream().anyMatch(x -> x.getShortName().startsWith("г-л"));
        boolean isGeneralOfArmy = rankService.findAll().stream().anyMatch(x -> x.getShortName().startsWith("ГЕНЕРАЛ"));
        if (isColonel || isGeneral || isGeneralOfArmy) {
            militaryManRoomCount++;
        }
        militaryMan.setRoomCount(militaryManRoomCount);
    }

    public void calculateCompensation(MilitaryMan militaryMan) {
        if (militaryMan.getRoomCount() == 1) {
            militaryMan.setExpectedCompensationValue(militaryMan.getWork().getGarrison().getPricePerMeter() *
                    militaryMan.getWork().getGarrison().getPricePerMeter());
        }
        long familyCount = familyMemberService.findAll().stream()
                .filter((x) -> x.getId().equals(militaryMan.getId())).count() + 1; // count of family members in personal data + mp.
        Quota militaryManQuota = militaryMan.getQuota();
        int dPl = 0;
        if (militaryManQuota.getType().equals(QuotaType.OUTOFQUEUE) && !militaryManQuota.getName().equals("суддя")) {
            dPl = 10;
        }
        double KyivCityCoefficient = 1.75;
        double b0 = militaryMan.getWork().getGarrison().getPricePerMeter() * KyivCityCoefficient;// static cost of 1 square of flat in hryvna * koef of city
        militaryMan.setExpectedCompensationValue((13.65 * familyCount + 17 + dPl) * b0);
    }

}