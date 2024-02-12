package ru.gazprom.testwork.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String large;
    private String medium;
    private String thumbnail;
}
