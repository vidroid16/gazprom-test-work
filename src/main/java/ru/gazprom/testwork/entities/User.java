package ru.gazprom.testwork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gazprom.testwork.dto.user.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name_id", nullable = false)
    private Name name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_info_id", nullable = false)
    private LoginInfo login;
    private ZonedDateTime birthday;
    private ZonedDateTime registration;
    private String phone;
    private String cell;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identificator_id", nullable = false)
    private Identificator identificator;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id", nullable = false)
    private Picture picture;
    private String nat;

}
