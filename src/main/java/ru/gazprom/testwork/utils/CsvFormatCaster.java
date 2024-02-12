package ru.gazprom.testwork.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import ru.gazprom.testwork.GazpromApplication;
import ru.gazprom.testwork.dto.user.UserDto;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CsvFormatCaster implements FormatCaster<UserDto>{
    private static final Logger log = GazpromApplication.log;
    public static final String[] HEADERS = {"gender", "name.title", "name.first", "name.last",
            "location.street.number", "location.street.name", "location.city", "location.state",
            "location.country", "location.postcode", "location.coordinates.latitude",
            "location.coordinates.longitude", "location.timezone.offset", "location.timezone.description",
            "email", "login.uuid", "login.username", "login.password", "login.salt", "login.md5", "login.sha1",
            "login.sha256", "dob.date", "dob.age", "registered.date", "registered.age", "phone", "cell",
            "id.name", "id.value", "picture.large", "picture.medium", "picture.thumbnail", "nat"};
    public static final CSVFormat FORMAT = CSVFormat.EXCEL.builder()
            .setHeader(HEADERS)
            .setSkipHeaderRecord(true)
            .setDelimiter(';')
            .build();
    @Override
    public ByteArrayOutputStream cast(List<UserDto> users) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer sw = new OutputStreamWriter(outputStream);

        HashMap<String,String> record = new HashMap<>();
        try (final CSVPrinter printer = new CSVPrinter(sw, FORMAT)) {
            users.forEach(u->{
                HashMap<String, Object> map = userToCsvMap(u);
                List<Object> rec = new ArrayList<>();
                for (String header : HEADERS) {
                    Object col = map.getOrDefault(header,null);
                    rec.add(col);
                }
                try {
                    printer.printRecord(rec);
                } catch (IOException e) {
                    log.error("Cannot cast User with identificator: {} to csv", u.getId().getValue());
                }
            });
//            InputStream is = new ByteArrayInputStream(sw.toString().getBytes());
//            OutputStream os = new ByteArrayOutputStream();
//            IOUtils.copy(is,os);
            return outputStream;
        }
    }
    private HashMap<String,Object> userToCsvMap(UserDto u){
        HashMap<String,Object> map = new HashMap<>();
        map.put("gender",u.getGender());
        map.put("name.title",u.getName().getTitle());
        map.put("name.first",u.getName().getFirst());
        map.put("name.last",u.getName().getLast());
        map.put("location.street.number",u.getLocation().getStreet().getNumber());
        map.put("location.street.name",u.getLocation().getStreet().getName());
        map.put("location.city",u.getLocation().getCity());
        map.put("location.state",u.getLocation().getState());
        map.put("location.country",u.getLocation().getCountry());
        map.put("location.postcode",u.getLocation().getPostcode());
        map.put("location.coordinates.latitude",u.getLocation().getCoordinates().getLatitude());
        map.put("location.coordinates.longitude",u.getLocation().getCoordinates().getLongitude());
        map.put("location.timezone.offset",u.getLocation().getTimezone().getOffset());
        map.put("location.timezone.description",u.getLocation().getTimezone().getDescription());
        map.put("email",u.getEmail());
        map.put("login.uuid",u.getLogin().getUuid());
        map.put("login.username",u.getLogin().getUsername());
        map.put("login.password",u.getLogin().getPassword());
        map.put("login.salt",u.getLogin().getSalt());
        map.put("login.md5",u.getLogin().getMd5());
        map.put("login.sha1",u.getLogin().getSha1());
        map.put("login.sha256",u.getLogin().getSha256());
        map.put("dob.date",u.getDob().getDate());
        map.put("dob.age",u.getDob().getAge());
        map.put("registered.date",u.getRegistered().getDate());
        map.put("registered.age",u.getRegistered().getAge());
        map.put("phone",u.getPhone());
        map.put("cell",u.getCell());
        map.put("id.name",u.getId().getName());
        map.put("id.value",u.getId().getValue());
        map.put("picture.large",u.getPicture().getLarge());
        map.put("picture.medium",u.getPicture().getMedium());
        map.put("picture.thumbnail",u.getPicture().getThumbnail());
        map.put("nat",u.getNat());

        return map;
    }
}
