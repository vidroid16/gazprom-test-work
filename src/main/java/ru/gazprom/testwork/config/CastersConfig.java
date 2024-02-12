package ru.gazprom.testwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gazprom.testwork.utils.CsvFormatCaster;
import ru.gazprom.testwork.utils.ExcelFormatCaster;
import ru.gazprom.testwork.utils.FormatCaster;

import java.util.HashMap;

@Configuration
public class CastersConfig {
    @Bean
    public HashMap<String, FormatCaster> casterByFormat(){
        HashMap<String,FormatCaster> casters = new HashMap<>();
        casters.put("csv", new CsvFormatCaster());
        casters.put("excel", new ExcelFormatCaster());
        return casters;
    }
}
