package ru.gazprom.testwork.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "loc_timezones")
public class TimeZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grinwich_offset")
    private String offset;
    private String description;
}
