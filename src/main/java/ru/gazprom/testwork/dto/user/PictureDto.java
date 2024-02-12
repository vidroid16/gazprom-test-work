package ru.gazprom.testwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureDto {
    private String large;
    private String medium;
    private String thumbnail;
}
