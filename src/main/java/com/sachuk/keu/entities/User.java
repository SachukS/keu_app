package com.sachuk.keu.entities;

import com.sachuk.keu.entities.enums.SexEnum;
import com.sachuk.keu.entities.enums.RoleEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @Column(name = "create_date_time", length = 6, nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "thirdname", nullable = false, length = 100)
    private String thirdname;

    @Column(name = "update_date_time", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date update_date_time;

    @Column(name = "ipn", nullable = false, length = 100)
    private String ipn;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @OneToOne
    @JoinColumn(name = "military_man_id", nullable = false)
    private MilitaryMan militaryMan;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth_date;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "sex", nullable = false)
    private SexEnum sex;
}
