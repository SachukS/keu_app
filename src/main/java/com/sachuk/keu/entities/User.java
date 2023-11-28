package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.SexEnum;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "thirdname", nullable = false, length = 100)
    private String thirdname;

    @Column(name = "create_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "update_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "ipn", nullable = true, length = 100)
    private String ipn;

    @Column(name = "password", nullable = true, length = 100)
    private String password;

    @OneToOne
    @JoinColumn(name = "military_man_id")
    private MilitaryMan militaryMan;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private SexEnum sex;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garrison_id", nullable = false)
    private Garrison garrison;

    @Column(name = "accounting_place", length = 100)
    private String accountingPlace;

    public User(String surname, String name, LocalDateTime createDate, Set<Role> roles, String thirdname,
                LocalDateTime update_date_time, String ipn, String password, MilitaryMan militaryMan,
                LocalDate birth_date, SexEnum sex) {
        this.surname = surname;
        this.name = name;
        this.createDate = createDate;
        this.roles = roles;
        this.thirdname = thirdname;
        this.updateDate = updateDate;
        this.ipn = ipn;
        this.password = password;
        this.militaryMan = militaryMan;
        this.birthDate = birth_date;
        this.sex = sex;
    }
}
