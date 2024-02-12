package ru.gazprom.testwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameDto {
    private String title;
    private String first;
    private String last;
}
