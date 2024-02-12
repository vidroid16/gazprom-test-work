package ru.gazprom.testwork.dto.user;

import lombok.Data;

@Data
public class LoginInfoDto {
    private String uuid;
    private String username;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;
}
