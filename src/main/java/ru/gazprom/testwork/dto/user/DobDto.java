package ru.gazprom.testwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DobDto {
    private String date;
    private Integer age;
}
