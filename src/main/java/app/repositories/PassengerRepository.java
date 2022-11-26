package app.repositories;

import app.entities.user.Passenger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    Optional<Passenger> findByPassport_serialNumberPassport(String serialNumberPassport);

    List<Passenger> findByLastName(String lastName);

    List<Passenger> findByFirstName(String firstName);

    List<Passenger> findByMiddleName(String middleName);

    Passenger findByEmail(String email);
}
