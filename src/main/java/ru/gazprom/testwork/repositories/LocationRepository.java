package ru.gazprom.testwork.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gazprom.testwork.entities.Location;
import ru.gazprom.testwork.entities.User;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}