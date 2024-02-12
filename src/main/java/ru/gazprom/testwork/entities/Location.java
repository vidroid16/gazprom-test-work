package ru.gazprom.testwork.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetName;
    private Integer streetNumber;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private Double latitude;
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "timezone_id", nullable = false)
    private TimeZone timeZone;
}
