package ru.gazprom.testwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneDto {
    private String offset;
    private String description;
}
