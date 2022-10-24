package app.repositories;

import app.entities.Passport;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PassportRepository extends CrudRepository<Passport, Long> {
    Optional<Passport> findBySerialNumberPassport(String serialNumberPassport);
}
