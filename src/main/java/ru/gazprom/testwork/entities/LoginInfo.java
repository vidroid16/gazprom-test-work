package ru.gazprom.testwork.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "login_info")
public class LoginInfo {
    @Id
    private String uuid;
    private String username;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;

}
