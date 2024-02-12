package ru.gazprom.testwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String gender;
    private NameDto name;
    private LocationDto location;
    private String email;
    private LoginInfoDto login;
    private DobDto dob;
    private RegistrationInfoDto registered;
    private String phone;
    private String cell;
    private IdentificatorDto id;
    private PictureDto picture;
    private String nat;

}
