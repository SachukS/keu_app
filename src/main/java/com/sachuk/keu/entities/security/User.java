package com.sachuk.keu.entities.security;

import com.sachuk.keu.utils.DateUtil;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Entity(name = "User")
@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"lastName", "firstName", "thirdName"}),
        @UniqueConstraint(columnNames = {"login"})})
@NamedQueries({@NamedQuery(name = User.findByEmail, query = "from User i where i.login=:name")})
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1401569701204278633L;
    public static final String findByEmail = "Users.findByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String lastName;
    private String firstName;
    private String thirdName;

    private String login;
    private String password;
    private String phoneNumber;
    @CreationTimestamp
    private LocalDateTime createDateTime;
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @Enumerated(EnumType.ORDINAL)
    private Roles role = Roles.ROLE_OPERATOR;

    @Enumerated(EnumType.ORDINAL)
    private Permission permission = Permission.NONE;

    @Enumerated(EnumType.ORDINAL)
    private Verification verification = Verification.UNVERIFIED;


    public Date getCreateDateTime() {
        if (createDateTime == null)
            return null;
        return Date.from(createDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date getUpdateDateTime() {
        if (updateDateTime == null)
            return null;
        return Date.from(updateDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getFormatCreateDateTime() {
        if (getCreateDateTime() != null) {
            return DateUtil.formatDateToString(getCreateDateTime(), "HH:mm dd.MM.yyyy");
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", permission=" + permission +
                ", verification=" + verification +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                '}';
    }
}