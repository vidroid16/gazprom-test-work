package ru.gazprom.testwork.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "identificators")
public class Identificator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String value;
}
