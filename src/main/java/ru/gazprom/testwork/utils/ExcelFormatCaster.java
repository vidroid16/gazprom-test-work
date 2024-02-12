package ru.gazprom.testwork.utils;

import org.springframework.stereotype.Component;
import ru.gazprom.testwork.dto.user.UserDto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class ExcelFormatCaster implements FormatCaster<UserDto>{
    @Override
    public ByteArrayOutputStream cast(List<UserDto> users) {
        return null;
    }
}
