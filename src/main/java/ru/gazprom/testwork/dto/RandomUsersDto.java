package ru.gazprom.testwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gazprom.testwork.entities.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class RandomUsersDto implements Serializable {
    @JsonProperty("results")
    private ArrayList<User> results;
    @JsonProperty("info")
    private RandomUsersReqInfoDto info;
}
