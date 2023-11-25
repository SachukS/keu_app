package com.sachuk.keu.entities;

import com.sachuk.keu.entities.security.Roles;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "create_date_time", length = 6, nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDateTime;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Roles role;

    @Column(name = "thirdname", nullable = false, length = 100)
    private String thirdname;

    @Column(name = "update_date_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date update_date_time;

    @Column(name = "ipn", nullable = false, length = 100)
    private String ipn;


    @OneToOne
    @JoinColumn(name = "military_man_id", nullable = false)
    private MilitaryMan militaryMan;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth_date;

    @Column(name = "sex", nullable = false, length = 100)
    private String sex;
}
