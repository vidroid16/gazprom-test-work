package ru.gazprom.testwork.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.gazprom.testwork.dto.user.*;
import ru.gazprom.testwork.entities.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    private final static DateTimeFormatter formatter  =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    public UserMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(User.class, UserDto.class).
                addMapping(User::getIdentificator, UserDto::setId);
        modelMapper.createTypeMap(UserDto.class, User.class).
                addMappings(m->m.skip(User::setId))
                .addMapping(UserDto::getId,User::setIdentificator);

        modelMapper.createTypeMap(Location.class, LocationDto.class);
        modelMapper.createTypeMap(Identificator.class, IdentificatorDto.class);
        modelMapper.createTypeMap(LoginInfo.class, LoginInfoDto.class);
        modelMapper.createTypeMap(Name.class, NameDto.class);
        modelMapper.createTypeMap(Picture.class, PictureDto.class);
        modelMapper.createTypeMap(TimeZone.class, TimeZoneDto.class);
    }

    public UserDto entityToDto(User entity){
        UserDto dto = modelMapper.map(entity, UserDto.class);
        CoordinatesDto coordinatesDto = new CoordinatesDto(entity.getLocation().getLatitude(), entity.getLocation().getLongitude());
        StreetDto streetDto = new StreetDto(entity.getLocation().getStreetNumber(),entity.getLocation().getStreetName());
        TimeZoneDto timeZoneDto = new TimeZoneDto(entity.getLocation().getTimeZone().getOffset(),entity.getLocation().getTimeZone().getDescription());

        DobDto dobDto = new DobDto();
        RegistrationInfoDto registrationInfoDto = new RegistrationInfoDto();
        var regAge = ChronoUnit.YEARS.between(ZonedDateTime.now(entity.getRegistration().getZone()),entity.getRegistration());
        var age = ChronoUnit.YEARS.between(ZonedDateTime.now(entity.getBirthday().getZone()),entity.getBirthday());
        dobDto.setDate(entity.getBirthday().format(formatter));
        dobDto.setAge((int) age);
        registrationInfoDto.setDate(entity.getRegistration().format(formatter));
        registrationInfoDto.setAge((int) regAge);

        dto.getLocation().setCoordinates(coordinatesDto);
        dto.getLocation().setStreet(streetDto);
        dto.getLocation().setTimezone(timeZoneDto);
        dto.setDob(dobDto);
        dto.setRegistered(registrationInfoDto);

        return dto;
    }
    public User dtoToEntity(UserDto dto){
        User entity = modelMapper.map(dto, User.class);
        entity.getLocation().setStreetName(dto.getLocation().getStreet().getName());
        entity.getLocation().setStreetNumber(dto.getLocation().getStreet().getNumber());
        entity.getLocation().setLatitude(dto.getLocation().getCoordinates().getLatitude());
        entity.getLocation().setLongitude(dto.getLocation().getCoordinates().getLongitude());

        ZonedDateTime regDate = ZonedDateTime.parse(dto.getRegistered().getDate());
        ZonedDateTime birthDate = ZonedDateTime.parse(dto.getDob().getDate());
        entity.setRegistration(regDate);
        entity.setBirthday(birthDate);

        TimeZone timeZone = modelMapper.map(dto.getLocation().getTimezone(),TimeZone.class);
        entity.getLocation().setTimeZone(timeZone);
//        RegistrationInfoDto registrationInfoDto = new RegistrationInfoDto();
//        ZonedDateTime regDate = entity.getRegistration();
//        Long years = ChronoUnit.YEARS.between(ZonedDateTime.now(regDate.getZone()),regDate);
        return entity;
    }
}
