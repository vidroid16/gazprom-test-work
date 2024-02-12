package ru.gazprom.testwork.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeZonePK implements Serializable {
    @Column(name = "offset")
    private String offset;
    @Column(name = "description")
    private String description;
}
