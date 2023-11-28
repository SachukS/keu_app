package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.Provided;
import com.sachuk.keu.entities.enums.QuotaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "military_man")
public class MilitaryMan implements Serializable { // TODO: 25.11.2023 Refactor length fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "thirdName", nullable = false, length = 100)
    private String thirdName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "accounting_date", nullable = false, length = 6)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate accountingDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Quota.class)
    @JoinColumn(name = "quota_id", nullable = false)
    private Quota quota = new Quota("Без пільг", "Без пільг", QuotaType.NONE);

    @Column(name = "quota_date", nullable = true)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate quotaDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Rank.class)
    @JoinColumn(name = "rank_id", nullable = false)
    private Rank rank;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Work.class)
    @JoinColumn(name = "work_id", nullable = false)
    private Work work;

    @Lob
    @Column(name = "info", nullable = true)
    @Type(type = "org.hibernate.type.TextType")
    private String info;

    @Column(name = "general_queue", nullable = false)
    private int generalQueue = 0;

    @Column(name = "quota_queue", nullable = false)
    private int quotaQueue = 0;

    @Column(name = "registrated", nullable = false)
    @ColumnDefault("false")
    private boolean registrated;

    @Column(name = "rozshirennya", nullable = false)
    @ColumnDefault("false")
    private boolean rozshirennya;

    @Column(name = "want_compensation", nullable = false)
    @ColumnDefault("false")
    private boolean wantCompensation;

    @Column(name = "expected_compensation_value", nullable = false)
    private double expectedCompensationValue = 0.0;

    @Column(name = "death_date")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate deathDate;

    @Column(name = "ipn", nullable = false)
    private String ipn;

    @Enumerated(EnumType.STRING)
    @Column(name = "provided", nullable = false)
    private Provided provided = Provided.NO;

    @Column(name = "update_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime createDate;

    @Column(name = "room_count", nullable = false)
    private int roomCount;

    @Column(name = "family_war_2022", nullable = false)
    @ColumnDefault("false")
    private boolean familyWar2022;

    @Column(name = "service_from", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate serviceFrom;

    @Column(name = "service_until")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate serviceUntil;

    @Column(name = "apartment_file_date")
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate apartmentFileDate;

    @Column(name = "apartment_file_number")
    private String apartmentFileNumber;

    @OneToMany
    @JoinColumn(name = "military_man_id")
    private List<FamilyMember> family = new ArrayList<>();

    @Column(name = "preview_id", nullable = false, length = 20)
    @ColumnDefault("-1")
    private long preview_id;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MilitaryMan)) return false;
        final MilitaryMan other = (MilitaryMan) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$surname = this.getSurname();
        final Object other$surname = other.getSurname();
        if (this$surname == null ? other$surname != null : !this$surname.equals(other$surname)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$thirdName = this.getThirdName();
        final Object other$thirdName = other.getThirdName();
        if (this$thirdName == null ? other$thirdName != null : !this$thirdName.equals(other$thirdName)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$accountingDate = this.getAccountingDate();
        final Object other$accountingDate = other.getAccountingDate();
        if (this$accountingDate == null ? other$accountingDate != null : !this$accountingDate.equals(other$accountingDate))
            return false;
        final Object this$quota = this.getQuota();
        final Object other$quota = other.getQuota();
        if (this$quota == null ? other$quota != null : !this$quota.equals(other$quota)) return false;
        final Object this$quotaDate = this.getQuotaDate();
        final Object other$quotaDate = other.getQuotaDate();
        if (this$quotaDate == null ? other$quotaDate != null : !this$quotaDate.equals(other$quotaDate)) return false;
        final Object this$rank = this.getRank();
        final Object other$rank = other.getRank();
        if (this$rank == null ? other$rank != null : !this$rank.equals(other$rank)) return false;
        final Object this$work = this.getWork();
        final Object other$work = other.getWork();
        if (this$work == null ? other$work != null : !this$work.equals(other$work)) return false;
        final Object this$info = this.getInfo();
        final Object other$info = other.getInfo();
        if (this$info == null ? other$info != null : !this$info.equals(other$info)) return false;
        if (this.getGeneralQueue() != other.getGeneralQueue()) return false;
        if (this.getQuotaQueue() != other.getQuotaQueue()) return false;
        if (this.isRegistrated() != other.isRegistrated()) return false;
        if (this.isRozshirennya() != other.isRozshirennya()) return false;
        if (this.isWantCompensation() != other.isWantCompensation()) return false;
        if (Double.compare(this.getExpectedCompensationValue(), other.getExpectedCompensationValue()) != 0)
            return false;
        final Object this$deathDate = this.getDeathDate();
        final Object other$deathDate = other.getDeathDate();
        if (this$deathDate == null ? other$deathDate != null : !this$deathDate.equals(other$deathDate)) return false;
        final Object this$ipn = this.getIpn();
        final Object other$ipn = other.getIpn();
        if (this$ipn == null ? other$ipn != null : !this$ipn.equals(other$ipn)) return false;
        final Object this$provided = this.getProvided();
        final Object other$provided = other.getProvided();
        if (this$provided == null ? other$provided != null : !this$provided.equals(other$provided)) return false;
        final Object this$updateDate = this.getCreateDate();
        final Object other$updateDate = other.getCreateDate();
        if (this$updateDate == null ? other$updateDate != null : !this$updateDate.equals(other$updateDate))
            return false;
        if (this.getRoomCount() != other.getRoomCount()) return false;
        if (this.isFamilyWar2022() != other.isFamilyWar2022()) return false;
        final Object this$serviceFrom = this.getServiceFrom();
        final Object other$serviceFrom = other.getServiceFrom();
        if (this$serviceFrom == null ? other$serviceFrom != null : !this$serviceFrom.equals(other$serviceFrom))
            return false;
        final Object this$serviceUntil = this.getServiceUntil();
        final Object other$serviceUntil = other.getServiceUntil();
        if (this$serviceUntil == null ? other$serviceUntil != null : !this$serviceUntil.equals(other$serviceUntil))
            return false;
        final Object this$apartmentFileDate = this.getApartmentFileDate();
        final Object other$apartmentFileDate = other.getApartmentFileDate();
        if (this$apartmentFileDate == null ? other$apartmentFileDate != null : !this$apartmentFileDate.equals(other$apartmentFileDate))
            return false;
        final Object this$apartmentFileNumber = this.getApartmentFileNumber();
        final Object other$apartmentFileNumber = other.getApartmentFileNumber();
        if (this$apartmentFileNumber == null ? other$apartmentFileNumber != null : !this$apartmentFileNumber.equals(other$apartmentFileNumber))
            return false;
        final Object this$family = this.getFamily();
        final Object other$family = other.getFamily();
        if (this$family == null ? other$family != null : !this$family.equals(other$family)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MilitaryMan;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $surname = this.getSurname();
        result = result * PRIME + ($surname == null ? 43 : $surname.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $thirdName = this.getThirdName();
        result = result * PRIME + ($thirdName == null ? 43 : $thirdName.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $accountingDate = this.getAccountingDate();
        result = result * PRIME + ($accountingDate == null ? 43 : $accountingDate.hashCode());
        final Object $quota = this.getQuota();
        result = result * PRIME + ($quota == null ? 43 : $quota.hashCode());
        final Object $quotaDate = this.getQuotaDate();
        result = result * PRIME + ($quotaDate == null ? 43 : $quotaDate.hashCode());
        final Object $rank = this.getRank();
        result = result * PRIME + ($rank == null ? 43 : $rank.hashCode());
        final Object $work = this.getWork();
        result = result * PRIME + ($work == null ? 43 : $work.hashCode());
        final Object $info = this.getInfo();
        result = result * PRIME + ($info == null ? 43 : $info.hashCode());
        result = result * PRIME + this.getGeneralQueue();
        result = result * PRIME + this.getQuotaQueue();
        result = result * PRIME + (this.isRegistrated() ? 79 : 97);
        result = result * PRIME + (this.isRozshirennya() ? 79 : 97);
        result = result * PRIME + (this.isWantCompensation() ? 79 : 97);
        final long $expectedCompensationValue = Double.doubleToLongBits(this.getExpectedCompensationValue());
        result = result * PRIME + (int) ($expectedCompensationValue >>> 32 ^ $expectedCompensationValue);
        final Object $deathDate = this.getDeathDate();
        result = result * PRIME + ($deathDate == null ? 43 : $deathDate.hashCode());
        final Object $ipn = this.getIpn();
        result = result * PRIME + ($ipn == null ? 43 : $ipn.hashCode());
        final Object $provided = this.getProvided();
        result = result * PRIME + ($provided == null ? 43 : $provided.hashCode());
        final Object $updateDate = this.getCreateDate();
        result = result * PRIME + ($updateDate == null ? 43 : $updateDate.hashCode());
        result = result * PRIME + this.getRoomCount();
        result = result * PRIME + (this.isFamilyWar2022() ? 79 : 97);
        final Object $serviceFrom = this.getServiceFrom();
        result = result * PRIME + ($serviceFrom == null ? 43 : $serviceFrom.hashCode());
        final Object $serviceUntil = this.getServiceUntil();
        result = result * PRIME + ($serviceUntil == null ? 43 : $serviceUntil.hashCode());
        final Object $apartmentFileDate = this.getApartmentFileDate();
        result = result * PRIME + ($apartmentFileDate == null ? 43 : $apartmentFileDate.hashCode());
        final Object $apartmentFileNumber = this.getApartmentFileNumber();
        result = result * PRIME + ($apartmentFileNumber == null ? 43 : $apartmentFileNumber.hashCode());
        final Object $family = this.getFamily();
        result = result * PRIME + ($family == null ? 43 : $family.hashCode());
        return result;
    }

//    public int getExpInt() {
//        return getWorkExperience();
//    }
//
//    public String getFormatAccounting() {
//        if (this.getAccountingDate() != null)
//            return DateUtil.formatDateToString(this.getAccountingDate(), "dd.MM.yyyy");
//        return null;
//    }
//
//    public Date getUpdateDateTime() {
//        if (updateDate == null)
//            return null;
//        return Date.from(updateDate.atZone(ZoneId.systemDefault()).toInstant());
//    }

//    public String getFormatQuota() {
//        if (this.getQuotaDate() != null)
//            return DateUtil.formatDateToString(this.getQuotaDate(), "dd.MM.yyyy");
//        return null;
//    }
}
