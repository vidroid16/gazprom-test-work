package ru.gazprom.testwork.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gazprom.testwork.entities.TimeZone;
import ru.gazprom.testwork.entities.User;

import java.util.Optional;

@Repository
public interface TimeZoneRepository extends CrudRepository<TimeZone, Long> {
    Optional<TimeZone> getByOffsetAndDescription(String offset, String description);
}