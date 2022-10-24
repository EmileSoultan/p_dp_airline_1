package app.repositories;

import app.entities.Passenger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PassengerRepository extends CrudRepository<Passenger, Long> {
    Optional<Passenger> findByPassportId(Long passportId);

    List<Passenger> findByLastName(String lastName);

    List<Passenger> findByFirstName(String firstName);

    List<Passenger> findByMiddleName(String middleName);
}
