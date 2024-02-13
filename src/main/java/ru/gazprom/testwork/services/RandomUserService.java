package ru.gazprom.testwork.services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gazprom.testwork.GazpromApplication;
import ru.gazprom.testwork.dto.user.UserDto;
import ru.gazprom.testwork.entities.TimeZone;
import ru.gazprom.testwork.entities.User;
import ru.gazprom.testwork.mappers.UserMapper;
import ru.gazprom.testwork.repositories.TimeZoneRepository;
import ru.gazprom.testwork.repositories.UserRepository;
import ru.gazprom.testwork.utils.CsvFormatCaster;
import ru.gazprom.testwork.utils.FormatCaster;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter
public class RandomUserService {

    private final HashMap<String, FormatCaster> casters;
    private final UserMapper userMapper;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final TimeZoneRepository timeZoneRepository;

    public RandomUserService(@Qualifier("casterByFormat") HashMap<String, FormatCaster> casters,
                             UserMapper userMapper, UserRepository userRepository,
                             TimeZoneRepository timeZoneRepository) {
        this.casters = casters;
        this.userMapper = userMapper;
        this.restTemplate = new RestTemplate();
        this.userRepository = userRepository;
        this.timeZoneRepository = timeZoneRepository;
    }
    public void uploadNRandomUsers(int n) throws JsonProcessingException{
        String url = "https://api.randomuser.me?page=1&results="+n;
        var jsonNode = restTemplate.getForEntity(url, JsonNode.class).getBody().get("results");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        List<UserDto> userDtos = objectMapper.readValue(jsonNode.toString(),new TypeReference<List<UserDto>>(){});
        List<User> users = userDtos.stream().map(userMapper::dtoToEntity).toList();
        users.forEach(u->{
            TimeZone zone = timeZoneRepository.
                    getByOffsetAndDescription(u.getLocation().getTimeZone().getOffset(),
                            u.getLocation().getTimeZone().getDescription()).orElse(null);
            if (zone==null){
                zone = timeZoneRepository.save(u.getLocation().getTimeZone());
            }
            u.getLocation().getTimeZone().setId(zone.getId());
            userRepository.save(u);
        });
    }

    public ByteArrayOutputStream saveCsvToFile(int n, String path){
        var page = userRepository.findAll(PageRequest.of(0,n));
        var userDtos = page.get().map(userMapper::entityToDto).toList();
        GazpromApplication.log.info(page.get().collect(Collectors.toList()).get(0).getEmail());
        try (ByteArrayOutputStream os = casters.get("csv").cast(userDtos);
             FileOutputStream out = new FileOutputStream(path)){
            FileWriter fileWriter = new FileWriter("/user/local/Documents/"+path);
            fileWriter.append(os.toString());
            fileWriter.flush();
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            GazpromApplication.log.error("IOExeption ((");
            return null;
        }
    }
    public Resource getFileResource(int n){
        var page = userRepository.findAll(PageRequest.of(0,n));
        var userDtos = page.get().map(userMapper::entityToDto).toList();
        GazpromApplication.log.info(page.get().collect(Collectors.toList()).get(0).getEmail());
        try (ByteArrayOutputStream os = casters.get("csv").cast(userDtos)){
            ByteArrayResource resource = new ByteArrayResource(os.toByteArray());
            return resource;
        } catch (IOException e) {
            GazpromApplication.log.error("{}",e.getMessage());
            GazpromApplication.log.error("IOExeption: {}", e.getMessage());
            return null;
        }
    }
}
